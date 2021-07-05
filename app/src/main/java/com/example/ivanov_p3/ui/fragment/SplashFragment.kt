package com.example.ivanov_p3.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.ivanov_p3.R
import com.example.ivanov_p3.common.base.BaseFragment
import com.example.ivanov_p3.databinding.FragmentSplashBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : BaseFragment(R.layout.fragment_splash) {

    private lateinit var binding: FragmentSplashBinding
    private val SPLASH_TIME_OUT = 1500L
    private val activityScope = CoroutineScope(Dispatchers.Main)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(layoutInflater, container, false)


        activityScope.launch {
            delay(SPLASH_TIME_OUT)

//            if(prefs.state)
                findNavController().navigate(R.id.action_splashFragment_to_searchFragment)
//            else {
//                val extras = FragmentNavigatorExtras(
//                    binding.imageViewAppSplash to "imageTransition"
//                )
//                findNavController().navigate(R.id.action_splashFragment_to_authorisationFragment, null, null, extras)
//            }
        }

        return binding.root
    }
}