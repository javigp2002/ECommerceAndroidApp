package com.example.ecommerce.presentation

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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
import kotlinx.coroutines.launch

class MainActivity : FragmentActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var userRepository: UserRepository
    private lateinit var adapter: MainViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)

        userRepository = AppContainerImpl.userRepository

        adapter = MainViewPagerAdapter(this)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.icon = when (position) {
                0 -> ContextCompat.getDrawable(this, home_24px)
                1 -> ContextCompat.getDrawable(this, shopping_cart_24px)
                else -> if (adapter.isUserLoggedIn)
                    ContextCompat.getDrawable(this, verified_user_24px)
                else
                    ContextCompat.getDrawable(this, admin_panel_settings_24px)
            }
        }.attach()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userRepository.isUserLoggedIn.collect { isLoggedIn ->
                    adapter.isUserLoggedIn = isLoggedIn

                    adapter.notifyItemChanged(2)

                }
            }
        }
    }
}
