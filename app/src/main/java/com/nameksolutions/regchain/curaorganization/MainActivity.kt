package com.nameksolutions.regchain.curaorganization

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.nameksolutions.regchain.curaorganization.utils.UserPreferences
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = activity_main_fragment_container_view as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_auth)
        val navController = navHostFragment.navController


        val userPrefs = UserPreferences(this)
        userPrefs.isFirstTime.asLiveData().observe(this, Observer {
//             if (it == true) graph.setStartDestination(R.id.personalEmailInput) else graph.setStartDestination(R.id.signIn)
            val destination = if (it != true) R.id.personalEmailInput else R.id.signIn
            graph.setStartDestination(destination)
             navController.graph = graph
//            val options = NavOptions.Builder()
//                .setPopUpTo(R.id.personalEmailInput, true)
//                .build()
//
//            findNavController().
//            navigate(nextDestination, null, options)
//
//            findNavController().navigate(nextDestination, null, options)
//            navController.setGraph(graph, intent.extras)

    })
    }
}