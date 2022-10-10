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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.nameksolutions.regchain.curaorganization.base.BaseFragment
import com.nameksolutions.regchain.curaorganization.databinding.FragmentServicesBinding
import com.nameksolutions.regchain.curaorganization.home.services.ServicesApi
import com.nameksolutions.regchain.curaorganization.home.services.ServicesRepo
import com.nameksolutions.regchain.curaorganization.home.services.ServicesViewModel
import com.nameksolutions.regchain.curaorganization.network.Resource
import com.nameksolutions.regchain.curaorganization.responses.services.FetchServicesInfoResponse
import com.nameksolutions.regchain.curaorganization.utils.handleApiError
import com.nameksolutions.regchain.curaorganization.utils.showProgressDialog
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class ServicesFragment : BaseFragment<ServicesViewModel, FragmentServicesBinding, ServicesRepo>() {

    private var progressDialog: Dialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchServicesInfo()

    }

    private fun fetchServicesInfo() {
        viewModel.fetchServicesInfo()
        viewModel.fetchServicesInfo.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgress()
                    populateUI(response)
                }
                is Resource.Failure -> {
                    hideProgress()
                    handleApiError(response) { fetchServicesInfo() }
                }
                is Resource.Loading -> {
                    showProgress()
                }
            }
        })
    }

    private fun populateUI(response: Resource.Success<FetchServicesInfoResponse>) {
        with(binding) {
            if (response.value._service.outpatientService)
                txtOutpatientService.isVisible = true
            if (response.value._service.inpatientServices)
                txtInpatientService.isVisible = true
            if (response.value._service.onsiteLaboratory)
                txtOnsiteLabService.isVisible = true
            if (response.value._service.motuaryServices)
                txtMortuaryService.isVisible = true
            if (response.value._service.medicalServices)
                txtMedicalService.isVisible = true
            if (response.value._service.ambulanceServices)
                txtAmbulanceService.isVisible = true


            txtTotalNumberOfBeds.apply {
                isVisible = true
                text = response.value._service.totalNumberOfBeds.toString()
            }

            txtNumberOfAvailableBeds.apply {
                isVisible = true
                text = response.value._service.numberOfBedsAvailable.toString()
            }

            val dentalServices = response.value._service.dentalServices
            val ogServices = response.value._service.obstericsAndGynecologyServices
            val pediatricsService = response.value._service.pediatricsServices
            val scServices = response.value._service.specialClinicalServices
            val surgicalServices = response.value._service.surgicalServices

            if (dentalServices.isEmpty()) {
                layoutDentalService.isVisible = false
            } else {
                layoutDentalService.isVisible = true
                for (dentalService in dentalServices) {
                    txtDentalServicesList.append(dentalService)
                }
            }

            if (ogServices.isEmpty()) {
                layoutOgService.isVisible = false
            } else {
                layoutOgService.isVisible = true
                for (ogService in ogServices) {
                    txtOgServicesList.append(ogService)
                }
            }

            if (pediatricsService.isEmpty()) {
                layoutPediatricsService.isVisible = false
            } else {
                layoutPediatricsService.isVisible = true
                for (ogService in ogServices) {
                    txtOgServicesList.append(ogService)
                }
            }

            if (scServices.isEmpty()) {
                layoutScService.isVisible = false
            } else {
                layoutScService.isVisible = true
                for (scService in scServices) {
                    txtScServicesList.append(scService)
                }
            }

            if (surgicalServices.isEmpty()) {
                layoutSurgicalService.isVisible = false
            } else {
                layoutSurgicalService.isVisible = true
                for (surgicalService in surgicalServices) {
                    txtSurgicalServicesList.append(surgicalService)
                }
            }

        }
    }


    private fun showProgress() {
        hideProgress()
        progressDialog = requireActivity().showProgressDialog()
    }

    private fun hideProgress() {
        progressDialog?.let { if (it.isShowing) it.cancel() }
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