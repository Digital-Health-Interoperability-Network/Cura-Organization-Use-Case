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
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.nameksolutions.regchain.curaorganization.R
import com.nameksolutions.regchain.curaorganization.base.BaseFragment
import com.nameksolutions.regchain.curaorganization.databinding.FragmentHealthCareServicesBinding
import com.nameksolutions.regchain.curaorganization.home.services.ServicesApi
import com.nameksolutions.regchain.curaorganization.home.services.ServicesRepo
import com.nameksolutions.regchain.curaorganization.home.services.ServicesViewModel
import com.nameksolutions.regchain.curaorganization.home.services.adapters.HealthCareServiceCategoryAdapter
import com.nameksolutions.regchain.curaorganization.home.services.adapters.HealthCareServicesAdapter
import com.nameksolutions.regchain.curaorganization.network.Resource
import com.nameksolutions.regchain.curaorganization.responses.services.HealthcareService
import com.nameksolutions.regchain.curaorganization.utils.Common.TAG
import com.nameksolutions.regchain.curaorganization.utils.handleApiError
import com.nameksolutions.regchain.curaorganization.utils.showProgressDialog
import com.nameksolutions.regchain.curaorganization.utils.snackbar
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class HealthCareServicesFragment :
    BaseFragment<ServicesViewModel, FragmentHealthCareServicesBinding, ServicesRepo>() {

    private var progressDialog: Dialog? = null
    private val healthCareServicesAdapter: HealthCareServicesAdapter = HealthCareServicesAdapter()
    private val healthCareServiceCategoryAdapter: HealthCareServiceCategoryAdapter = HealthCareServiceCategoryAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchHealthCareServices()

        binding.fabAddHealthCareService.setOnClickListener {
            findNavController().navigate(R.id.action_servicesHomeFragment_to_newService)
        }

        with(binding) {
            val healthCareServiceLayoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            rvHealthCareServices.apply {
                adapter = healthCareServicesAdapter
                layoutManager = healthCareServiceLayoutManager
                addItemDecoration(
                    DividerItemDecoration(
                        requireContext(), healthCareServiceLayoutManager.orientation
                    )
                )
            }
        }
    }

    private fun fetchHealthCareServices() {
        viewModel.fetchHealthCareServices()
        viewModel.fetchHealthCareServices.observe(viewLifecycleOwner, Observer { response ->

            when (response) {
                is Resource.Success -> {
                    hideProgress()
                    //categorize the data into categories
                    if (response.value.length == 0) {
                        requireView().snackbar("No Healthcare Services Found!")
                    } else {

                        val categories = mutableListOf<String>()
                        val healthCareServices = response.value.healthcareServices
                        for (healthCareService in healthCareServices) {
                            val category = healthCareService.category
                            for (cat in category) {
                                if (cat !in categories)
                                    categories.add(cat)
                            }
                        }

                        val services = mutableListOf<HealthcareService>()
                        Log.d(TAG, "fetchHealthCareServices: $categories")
                        for (category in categories) {
                            for (healthCareService in healthCareServices) {
                                if (healthCareService.category.contains(category)) {
                                    services.add(healthCareService)
                                }

                            }
                        }
                        subscribeToUI(services)

                        Log.d(TAG, "fetchHealthCareServices: $services")
                    }

                }
                is Resource.Failure -> {
                    hideProgress()
                    handleApiError(response) { fetchHealthCareServices() }
                }
                is Resource.Loading -> {
                    showProgress()
                }
            }
        })
    }

    private fun subscribeToUI(services: MutableList<HealthcareService>) {
        healthCareServicesAdapter.submitList(services)
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
    ) = FragmentHealthCareServicesBinding.inflate(inflater, container, false)

    override fun getFragmentRepo(): ServicesRepo {
        val token = runBlocking { userprefs.authToken.first() }
        val api = remoteDataSource.buildApi(ServicesApi::class.java, token)
        return ServicesRepo(api, userprefs)
    }

}