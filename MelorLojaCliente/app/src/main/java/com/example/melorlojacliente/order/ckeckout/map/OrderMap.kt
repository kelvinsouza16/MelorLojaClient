package com.example.melorlojacliente.order.ckeckout.map

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
class OrderMap : OnMapReadyCallback, SupportMapFragment() {

    private var map: (googleMap: GoogleMap) -> Unit = {}

    fun onMapReady(map: (googleMap: GoogleMap) -> Unit) {
        this.map = map
    }

    override fun onMapReady(p0: GoogleMap) {
        map.invoke(p0)
    }

}