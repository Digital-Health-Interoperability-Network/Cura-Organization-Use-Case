package com.nameksolutions.regchain.curaorganization.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.NavHostFragment
import com.nameksolutions.regchain.curaorganization.R
import com.nameksolutions.regchain.curaorganization.databinding.ActivityHomeBinding
import com.nameksolutions.regchain.curaorganization.databinding.ActivityRegBinding
import com.nameksolutions.regchain.curaorganization.home.personnel.ui.PersonnelFragment
import com.nameksolutions.regchain.curaorganization.home.profile.ProfileFragment
import com.nameksolutions.regchain.curaorganization.home.services.ServicesFragment
import com.nameksolutions.regchain.curaorganization.home.tokens.TokenFragment
import com.nameksolutions.regchain.curaorganization.utils.Common
import com.nameksolutions.regchain.curaorganization.utils.UserPreferences
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_main.*

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private var personnelFragment = PersonnelFragment()
    private var servicesFragment = ServicesFragment()
    private var tokenFragment = TokenFragment()
    private var profileFragment = ProfileFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityHomeBinding.inflate(layoutInflater)


        val navHostFragment = activity_home_fragment_container_view as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_auth)
        val navController = navHostFragment.navController


//        val destination = if (binding.actionBarHomeLand?.isVisible == true) R.id.personnelFragment else R.id.homeFragment
//        val destination =  R.id.personnelFragment
//        val userPrefs = UserPreferences(this)
//        userPrefs.isFirstTime.asLiveData().observe(this, Observer {
//             if (it == true) graph.setStartDestination(R.id.personalEmailInput) else graph.setStartDestination(R.id.signIn)
//            val destination = if (it != true) R.id.signIn else R.id.personalEmailInput
//            Log.d(Common.TAG, "onCreate Is First Time: $it")
//            graph.setStartDestination(destination)
//            navController.graph = graph
//            val options = NavOptions.Builder()
//                .setPopUpTo(R.id.personalEmailInput, true)
//                .build()
//
//            findNavController().
//            navigate(nextDestination, null, options)
//
//            findNavController().navigate(nextDestination, null, options)
//            navController.setGraph(graph, intent.extras)

//        })


        with(binding){
            if (actionBarHomeLand?.isVisible == true){

                buttonPersonnel?.setOnClickListener {
                    openMainFragment(personnelFragment)
                }
                buttonServices?.setOnClickListener {
                    openMainFragment(servicesFragment)
                }
                buttonProfile?.setOnClickListener {
                    openMainFragment(profileFragment)
                }
                buttonToken?.setOnClickListener {
                    openMainFragment(tokenFragment)
                }
            }
        }

        setContentView(binding.root)


    }

    private fun openMainFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.activity_home_fragment_container_view, fragment)
        transaction.commit()
    }
}