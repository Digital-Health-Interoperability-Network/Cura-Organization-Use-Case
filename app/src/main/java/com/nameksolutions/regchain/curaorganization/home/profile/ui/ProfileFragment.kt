/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Namek Solutions Limited,
 * Abuja, FCT.
 * Nigeria
 */

package com.nameksolutions.regchain.curaorganization.home.profile.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.nameksolutions.regchain.curaorganization.base.BaseFragment
import com.nameksolutions.regchain.curaorganization.databinding.FragmentPersonnelBinding
import com.nameksolutions.regchain.curaorganization.home.personnel.PersonnelApi
import com.nameksolutions.regchain.curaorganization.home.personnel.PersonnelRepo
import com.nameksolutions.regchain.curaorganization.home.profile.ProfileApi
import com.nameksolutions.regchain.curaorganization.home.profile.ProfileRepo
import com.nameksolutions.regchain.curaorganization.home.profile.ProfileViewModel
import com.nameksolutions.regchain.curaorganization.utils.showProgressDialog
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class ProfileFragment : BaseFragment<ProfileViewModel, FragmentPersonnelBinding, ProfileRepo>() {

    private var progressDialog: Dialog? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchOrganizationInfo()

    }

    private fun fetchOrganizationInfo() {
        viewModel.getOrganizationInfo()
        viewModel.fetchOrganizationInfo.observe(viewLifecycleOwner, Observer {

        })

    }


    private fun showProgress() {
        hideProgress()
        progressDialog = requireActivity().showProgressDialog()
    }

    private fun hideProgress() {
        progressDialog?.let { if (it.isShowing) it.cancel() }
    }

    override fun getViewModel() = ProfileViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPersonnelBinding.inflate(inflater, container, false)

    override fun getFragmentRepo(): ProfileRepo{
        val token = runBlocking { userprefs.authToken.first() }
        val api = remoteDataSource.buildApi(ProfileApi::class.java, token)
        return ProfileRepo(api, userprefs)
    }

}