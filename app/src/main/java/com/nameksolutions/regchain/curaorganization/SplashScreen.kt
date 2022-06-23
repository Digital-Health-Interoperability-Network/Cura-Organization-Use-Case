package com.nameksolutions.regchain.curaorganization

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.nameksolutions.regchain.curaorganization.databinding.ActivityRegBinding
import com.nameksolutions.regchain.curaorganization.databinding.ActivitySplashScreenBinding
import com.nameksolutions.regchain.curaorganization.utils.UserPreferences
import com.nameksolutions.regchain.curaorganization.utils.startNewActivity

class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)

            binding.splashLayout.alpha = 0f
            binding.splashLayout.animate().setDuration(2000).alpha(1f).withEndAction{
            val authActivity = MainActivity::class.java
                startNewActivity(authActivity)
            }


        setContentView(binding.root)
    }
}