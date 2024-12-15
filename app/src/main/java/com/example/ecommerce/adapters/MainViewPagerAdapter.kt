package com.example.ecommerce.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ecommerce.presentation.CartShopFragment
import com.example.ecommerce.presentation.ListProductShopFragment
import com.example.ecommerce.presentation.admin.AdminFragment
import com.example.ecommerce.ui.login.LoginFragment

class MainViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val isUserLoggedIn: Boolean
) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        if (position == 1) {
            return CartShopFragment.newInstance()
        } else if (position == 2) {
            if (isUserLoggedIn) {
                return AdminFragment.newInstance()
            }
            return LoginFragment.newInstance()
        }
        return ListProductShopFragment.newInstance()
    }

}