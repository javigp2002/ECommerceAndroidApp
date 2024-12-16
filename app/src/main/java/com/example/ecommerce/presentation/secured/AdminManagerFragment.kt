package com.example.ecommerce.presentation.secured

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.ecommerce.R
import com.example.ecommerce.dependency.AppContainerImpl.userRepository
import com.example.ecommerce.presentation.secured.admin.AdminFragment
import com.example.ecommerce.presentation.secured.login.LoginFragment
import kotlinx.coroutines.launch

class AdminManagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.admin_manager_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            userRepository.isUserLoggedIn.collect { isLoggedIn ->

                if (isLoggedIn) {
                    childFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, AdminFragment.newInstance())
                        .commit()
                } else {
                    childFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, LoginFragment.newInstance())
                        .commit()
                }

            }
        }


    }
}
