package com.example.melorlojacliente.order.presentation

import android.annotation.SuppressLint
import android.location.Geocoder
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.melorlojacliente.commom.exceptions.NoInternetConnection
import com.example.melorlojacliente.commom.extensions.hasConnection
import com.example.melorlojacliente.order.ckeckout.map.Map
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MapPresenter(private var view: Map.View?, private val geocoder: Geocoder,
) : Map.Presenter, ViewModel() {
    override val address = MutableSharedFlow<String>()

    override val currentLocationFlow = MutableLiveData<LatLng>()

    override fun getAddressByLocation(latLng: LatLng) {
        viewModelScope.launch(Dispatchers.IO) {
            //loadingFlow.emit(true)
            try {
                if (hasConnection()) {
                    val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
                    address.emit(withContext(Dispatchers.IO) {
                        //loadingFlow.emit(false)
                        try {
                            if (addresses!!.size > 0) addresses[0].getAddressLine(0)
                            else "Não especificado"
                        } catch (e: Exception) {
                            e.localizedMessage
                        }
                    }!!)
                } else throw NoInternetConnection()
            } catch (e: Exception) {
                //loadingFlow.emit(false)
                //errorFlow.emit(e.localizedMessage!!)
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun requestCurrentLocation() {
        /*viewModelScope.launch {
            //loadingFlow.emit(true)
            fusedLocationClient.getCurrentLocation(
                LocationRequest.PRIORITY_HIGH_ACCURACY,
                cancellationTokenSource.token
            )
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val location = task.result
                        currentLocationFlow.postValue(LatLng(location.latitude, location.longitude))
                    } else {
                        //errorFlow.tryEmit(task.exception!!.localizedMessage!!)
                    }
                }
        }*/

    }

    override fun onDestroy() {
        view = null
    }
}