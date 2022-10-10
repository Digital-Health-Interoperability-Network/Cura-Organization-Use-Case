/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Namek Solutions Limited,
 * Abuja, FCT.
 * Nigeria
 */

package com.nameksolutions.regchain.curaorganization.home.services.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.nameksolutions.regchain.curaorganization.home.services.ServicesFragment
import com.nameksolutions.regchain.curaorganization.home.services.ui.HealthCareServicesFragment

/**
 * Created by Richard Uzor  on 10/10/2022
 */
/**
 * Created by Richard Uzor  on 10/10/2022
 */
class ServicesHomePagerAdapter(
    var context: FragmentActivity?,
    fm: FragmentManager,
    var totalTabs: Int
) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return totalTabs
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                ServicesFragment()
            }
            1 -> {
                HealthCareServicesFragment()
            }
            else -> getItem(position)
        }
    }


}