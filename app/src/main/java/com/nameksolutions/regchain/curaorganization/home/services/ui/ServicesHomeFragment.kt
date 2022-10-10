/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Namek Solutions Limited,
 * Abuja, FCT.
 * Nigeria
 */

package com.nameksolutions.regchain.curaorganization.home.services.ui

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import com.nameksolutions.regchain.curaorganization.R
import com.nameksolutions.regchain.curaorganization.base.BaseFragment
import com.nameksolutions.regchain.curaorganization.databinding.FragmentServicesHomeBinding
import com.nameksolutions.regchain.curaorganization.home.personnel.PersonnelApi
import com.nameksolutions.regchain.curaorganization.home.personnel.PersonnelRepo
import com.nameksolutions.regchain.curaorganization.home.services.ServicesApi
import com.nameksolutions.regchain.curaorganization.home.services.ServicesRepo
import com.nameksolutions.regchain.curaorganization.home.services.ServicesViewModel
import com.nameksolutions.regchain.curaorganization.home.services.adapters.ServicesHomePagerAdapter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class ServicesHomeFragment :
    BaseFragment<ServicesViewModel, FragmentServicesHomeBinding, ServicesRepo>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            tabTitle.addTab(tabTitle.newTab().setText("Services Info"))
            tabTitle.addTab(tabTitle.newTab().setText("Healthcare Services"))

            tabTitle.tabGravity = TabLayout.GRAVITY_FILL

            val adapter = childFragmentManager?.let {
                ServicesHomePagerAdapter(
                    activity,
                    it,
                    tabTitle.tabCount
                )
            }
            viewPager.adapter = adapter
            viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabTitle))

            tabTitle.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    viewPager.currentItem = tab.position
                    tabTitle.setSelectedTabIndicatorColor(Color.WHITE)
                    tabTitle.setTabTextColors(resources.getColor(R.color.custom_color), Color.WHITE)
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                    tabTitle.setTabTextColors(Color.BLACK, Color.WHITE)
                }

                override fun onTabReselected(tab: TabLayout.Tab) {}
            })

        }


    }

    override fun getViewModel() = ServicesViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentServicesHomeBinding.inflate(inflater, container, false)

    override fun getFragmentRepo(): ServicesRepo {
        val token = runBlocking { userprefs.authToken.first() }
        val api = remoteDataSource.buildApi(ServicesApi::class.java, token)
        return ServicesRepo(api, userprefs)
    }


}