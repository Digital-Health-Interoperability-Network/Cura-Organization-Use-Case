/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Namek Solutions Limited,
 * Abuja, FCT.
 * Nigeria
 */

package com.nameksolutions.regchain.curaorganization.home.services.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nameksolutions.regchain.curaorganization.base.BaseFragment
import com.nameksolutions.regchain.curaorganization.databinding.FragmentServicesBinding
import com.nameksolutions.regchain.curaorganization.home.services.ServicesApi
import com.nameksolutions.regchain.curaorganization.home.services.ServicesRepo
import com.nameksolutions.regchain.curaorganization.home.services.ServicesViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class ServicesFragment : BaseFragment<ServicesViewModel, FragmentServicesBinding, ServicesRepo>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun getViewModel() = ServicesViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentServicesBinding.inflate(inflater, container, false)

    override fun getFragmentRepo(): ServicesRepo {
        val token = runBlocking { userprefs.authToken.first() }
        val api = remoteDataSource.buildApi(ServicesApi::class.java, token)
        return ServicesRepo(api, userprefs)
    }

}