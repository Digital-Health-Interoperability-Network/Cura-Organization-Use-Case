package com.nameksolutions.regchain.curaorganization.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.nameksolutions.regchain.curaorganization.R
import com.nameksolutions.regchain.curaorganization.databinding.ActivityHomeBinding
import com.nameksolutions.regchain.curaorganization.home.personnel.ui.PersonnelFragment
import com.nameksolutions.regchain.curaorganization.home.profile.ui.ProfileFragment
import com.nameksolutions.regchain.curaorganization.home.services.ui.ServicesFragment
import com.nameksolutions.regchain.curaorganization.home.tokens.TokenFragment
import kotlinx.android.synthetic.main.activity_home.*

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



        setContentView(binding.root)


    }

    private fun openMainFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.activity_home_fragment_container_view, fragment)
        transaction.commit()
    }

    override fun onBackPressed() {

    }
}