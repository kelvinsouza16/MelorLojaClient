package com.example.melorlojacliente.commom.extensions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.melorlojacliente.commom.dialogs.ErrorDialog
import com.example.melorlojacliente.commom.dialogs.MessageDialog
import com.example.melorlojacliente.commom.dialogs.SuccessDialog
import com.example.melorlojacliente.login.view.LoginActivity
import com.example.melorlojacliente.main.view.MainActivity
import com.example.melorlojacliente.register.view.RegisterActivity

const val DEBOUNCE_TIME_OUT: Long = 150L

fun Fragment.hideLoginProgress() {
    (requireActivity() as LoginActivity).hideProgress()
}

fun Fragment.showLoginProgress() {
    (requireActivity() as LoginActivity).showProgress()
}

fun Fragment.hideRegisterProgress() {
    (requireActivity() as RegisterActivity).hideProgress()
}

fun Fragment.showRegisterProgress() {
    (requireActivity() as RegisterActivity).showProgress()
}

fun Fragment.hideMainProgress() {
    (requireActivity() as MainActivity).hideProgress()
}

fun Fragment.showMainProgress() {
    (requireActivity() as MainActivity).showProgress()
}

fun Fragment.showErrorDialog(message: String) {
    val dialog = ErrorDialog(requireContext(), message)
    dialog.show()
}

fun Fragment.showMessageDialog(message: String) {
    val dialog = MessageDialog(requireContext(), message)
    dialog.show()
}

fun Fragment.showSuccessDialog(message: String) {
    val dialog = SuccessDialog(requireContext(), message)
    dialog.show()
}

fun Fragment.toast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.hasPermission(permission: String): Boolean {
    return requireActivity().applicationContext.hasPermission(permission)
}

fun Context.hasPermission(permission: String): Boolean {

    if (permission == Manifest.permission.ACCESS_BACKGROUND_LOCATION &&
        android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.Q
    ) return true

    return ActivityCompat.checkSelfPermission(this, permission) ==
            PackageManager.PERMISSION_GRANTED
}

fun Fragment.isLocationEnabled(): Boolean {
    val locationManager =
        requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
        LocationManager.NETWORK_PROVIDER
    )
}