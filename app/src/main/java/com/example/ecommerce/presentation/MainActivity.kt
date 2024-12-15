package com.example.ecommerce.presentation

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.ecommerce.R
import com.example.ecommerce.R.drawable.admin_panel_settings_24px
import com.example.ecommerce.R.drawable.home_24px
import com.example.ecommerce.R.drawable.shopping_cart_24px
import com.example.ecommerce.R.drawable.verified_user_24px
import com.example.ecommerce.adapters.MainViewPagerAdapter
import com.example.ecommerce.dependency.AppContainerImpl
import com.example.ecommerce.domain.repository.UserRepository
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : FragmentActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)

        userRepository = AppContainerImpl().userRepository

        val isUserLoggedIn = userRepository.getUserJwt().isNotEmpty()

        viewPager.adapter = MainViewPagerAdapter(this, isUserLoggedIn)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.icon = when (position) {
                0 -> ContextCompat.getDrawable(this, home_24px)
                1 -> ContextCompat.getDrawable(this, shopping_cart_24px)
                else -> if (isUserLoggedIn)
                    ContextCompat.getDrawable(this, verified_user_24px)
                else
                    ContextCompat.getDrawable(this, admin_panel_settings_24px)
            }
        }.attach()

    }
}
