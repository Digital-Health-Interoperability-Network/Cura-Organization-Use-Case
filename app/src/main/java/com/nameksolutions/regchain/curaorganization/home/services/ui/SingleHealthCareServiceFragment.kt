/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Namek Solutions Limited,
 * Abuja, FCT.
 * Nigeria
 */

package com.nameksolutions.regchain.curaorganization.home.services.ui

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.nameksolutions.regchain.curaorganization.R
import com.nameksolutions.regchain.curaorganization.base.BaseFragment
import com.nameksolutions.regchain.curaorganization.databinding.FragmentSingleHealthCareServiceBinding
import com.nameksolutions.regchain.curaorganization.home.personnel.adapters.PractitionerAvailableTImeAdapter
import com.nameksolutions.regchain.curaorganization.home.personnel.ui.PractitionerItemArgs
import com.nameksolutions.regchain.curaorganization.home.services.ServicesApi
import com.nameksolutions.regchain.curaorganization.home.services.ServicesRepo
import com.nameksolutions.regchain.curaorganization.home.services.ServicesViewModel
import com.nameksolutions.regchain.curaorganization.responses.PractitionerRoleX
import com.nameksolutions.regchain.curaorganization.responses.Telecom
import com.nameksolutions.regchain.curaorganization.utils.showProgressDialog
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class SingleHealthCareServiceFragment : BaseFragment<ServicesViewModel, FragmentSingleHealthCareServiceBinding, ServicesRepo>() {


    private val args by navArgs<PractitionerItemArgs>()
    private val availableTimeAdapter = PractitionerAvailableTImeAdapter()
    private var telecoms = listOf<Telecom>()
    private var progressDialog: Dialog? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun getViewModel() = ServicesViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSingleHealthCareServiceBinding.inflate(inflater, container, false)

    override fun getFragmentRepo(): ServicesRepo {
        val token = runBlocking { userprefs.authToken.first() }
        val api = remoteDataSource.buildApi(ServicesApi::class.java, token)
        return ServicesRepo(api, userprefs)    }


    private fun showProgress() {
        hideProgress()
        progressDialog = requireActivity().showProgressDialog()
    }

    private fun hideProgress() {
        progressDialog?.let { if (it.isShowing) it.cancel() }
    }


}