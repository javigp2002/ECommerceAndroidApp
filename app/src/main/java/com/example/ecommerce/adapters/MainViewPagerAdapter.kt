package com.example.ecommerce.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.example.ecommerce.presentation.AdminManagerFragment
import com.example.ecommerce.presentation.CartShopFragment
import com.example.ecommerce.presentation.ListProductShopFragment

class MainViewPagerAdapter(
    fragmentActivity: FragmentActivity
) :
    FragmentStateAdapter(fragmentActivity) {
    var isUserLoggedIn = false

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        if (position == 1) {
            return CartShopFragment.newInstance()
        } else if (position == 2) {
            return AdminManagerFragment()

        }
        return ListProductShopFragment.newInstance()
    }

    override fun onBindViewHolder(
        holder: FragmentViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
    }

}