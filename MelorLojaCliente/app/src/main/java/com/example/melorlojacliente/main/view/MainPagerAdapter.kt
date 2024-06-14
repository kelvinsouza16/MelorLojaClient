package com.example.melorlojacliente.main.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.melorlojacliente.home.view.FragmentHome
import com.example.melorlojacliente.order.view.FragmentOrder
import com.example.melorlojacliente.profile.view.FragmentProfile

class MainPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> FragmentHome()
            1 -> FragmentOrder()
            else -> FragmentProfile()
        }
}