package com.example.melorlojacliente.order.ckeckout.map

import androidx.lifecycle.LiveData
import com.example.melorlojacliente.commom.base.BasePresenter
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.SharedFlow

interface Map {
    interface Presenter : BasePresenter {
        val address: SharedFlow<String>
        val currentLocationFlow: LiveData<LatLng>
        fun getAddressByLocation(latLng: LatLng)
        fun requestCurrentLocation()
    }

    interface View {

    }
}