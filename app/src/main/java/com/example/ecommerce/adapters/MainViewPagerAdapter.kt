package com.example.ecommerce.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ecommerce.presentation.CartShopFragment
import com.example.ecommerce.presentation.ListProductShopFragment

class MainViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        if (position == 1) {
            return CartShopFragment.newInstance()
        }
        return ListProductShopFragment.newInstance()
    }

}