package com.example.melorlojacliente.order.ckeckout.map

import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.location.Geocoder
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.example.melorlojacliente.R
import com.example.melorlojacliente.commom.base.BaseFragment
import com.example.melorlojacliente.commom.extensions.hasPermission
import com.example.melorlojacliente.commom.extensions.isLocationEnabled
import com.example.melorlojacliente.commom.extensions.toast
import com.example.melorlojacliente.commom.utils.Basket
import com.example.melorlojacliente.databinding.FragmentOrderMapBinding
import com.example.melorlojacliente.order.presentation.MapPresenter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.ldralighieri.corbind.view.clicks

class FragmentOrderMap : BaseFragment<FragmentOrderMapBinding, Map.Presenter>(
    R.layout.fragment_order_map,
    FragmentOrderMapBinding::bind
), Map.View {

    private var job: Job? = null
    private lateinit var googleMap: GoogleMap
    private lateinit var center: LatLng

    override lateinit var presenter: Map.Presenter

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) permissionApprovedSnackBar() else permissionDeniedSnackBar()
        }

    @OptIn(FlowPreview::class)
    override fun setupViews() {
        val map = childFragmentManager.findFragmentById(R.id.map_container) as OrderMap
        map.getMapAsync(map)
        map.onMapReady {
            googleMap = it
            googleMap.uiSettings.apply {
                isCompassEnabled = false
                isRotateGesturesEnabled = false
                isMyLocationButtonEnabled = false
            }
            googleMap.addMarker(MarkerOptions().position(ITAPERUNA).title("Itaperuna"))
            if (!this::center.isInitialized)
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ITAPERUNA, 5f))

            googleMap.setOnCameraIdleListener {
                job?.cancel()
                job = viewLifecycleOwner.lifecycleScope.launch {
                    delay(2000L)
                    presenter.getAddressByLocation(googleMap.cameraPosition.target)
                }
            }

            center = googleMap.cameraPosition.target

        }

        binding?.let {fragmentMapBinding ->
            with(fragmentMapBinding) {

                mapBtnMyLocation.clicks().debounce(100L).onEach {
                    if (isLocationEnabled()) locationRequest()
                    else showAlert()
                }.launchIn(lifecycleScope)

                presenter.address.onEach {
                    mapTxtAddressName.text = it
                }.launchIn(viewLifecycleOwner.lifecycleScope)

                presenter.currentLocationFlow.observe(viewLifecycleOwner) {
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(it, 10f))
                    presenter.getAddressByLocation(it)
                }

                mapBtnConfirm.setOnClickListener {
                    Basket.locationLiveData.value =
                        Pair(mapTxtAddressName.text.toString(), googleMap.cameraPosition.target)
                    toast(googleMap.cameraPosition.target.toString())
                    //findNavController().navigateUp()
                }
            }
        }
    }

    override fun setupPresenter() {
        val geocoder = Geocoder(requireContext())
        presenter = MapPresenter(this, geocoder)
    }

    companion object {
        private val ITAPERUNA = LatLng(-21.2065, -41.8896)
    }

    private fun permissionApprovedSnackBar() {
        Snackbar.make(
            binding?.root!!, R.string.permission_approved_explanation,
            BaseTransientBottomBar.LENGTH_LONG
        ).show()
    }

    private fun permissionDeniedSnackBar() {
        Snackbar.make(
            binding?.root!!,
            R.string.fine_permission_denied_explanation,
            BaseTransientBottomBar.LENGTH_LONG
        )
            .setAction(R.string.settings) { launchSettings() }
            .setActionTextColor(Color.WHITE)
            .show()
    }

    private fun launchSettings() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts(
            "package",
            "com.example.melorlojacliente", null
        )
        intent.data = uri
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun locationRequest() {
        if (hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) presenter.requestCurrentLocation()
        else requestPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun showAlert() {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle(getString(R.string.enable_location))
            .setMessage(getString(R.string.enable_location_message))
            .setPositiveButton(getString(R.string.location_settings)) { _, _ ->
                val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(myIntent)
            }
            .setNegativeButton(getString(R.string.cancel)) { _, _ -> }
        dialog.show()
    }

}