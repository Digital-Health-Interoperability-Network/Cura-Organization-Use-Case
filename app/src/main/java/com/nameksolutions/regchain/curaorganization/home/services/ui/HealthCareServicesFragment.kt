/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Namek Solutions Limited,
 * Abuja, FCT.
 * Nigeria
 */

package com.nameksolutions.regchain.curaorganization.home.services.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nameksolutions.regchain.curaorganization.R
import com.nameksolutions.regchain.curaorganization.base.BaseFragment
import com.nameksolutions.regchain.curaorganization.databinding.FragmentHealthCareServicesBinding
import com.nameksolutions.regchain.curaorganization.home.services.ServicesApi
import com.nameksolutions.regchain.curaorganization.home.services.ServicesRepo
import com.nameksolutions.regchain.curaorganization.home.services.ServicesViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class HealthCareServicesFragment : BaseFragment<ServicesViewModel, FragmentHealthCareServicesBinding, ServicesRepo>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabAddHealthCareService.setOnClickListener {
            findNavController().navigate(R.id.action_servicesHomeFragment_to_newService)
        }
    }

    override fun getViewModel() = ServicesViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHealthCareServicesBinding.inflate(inflater, container, false)

    override fun getFragmentRepo(): ServicesRepo {
        val token = runBlocking { userprefs.authToken.first() }
        val api = remoteDataSource.buildApi(ServicesApi::class.java, token)
        return ServicesRepo(api, userprefs)
    }

}