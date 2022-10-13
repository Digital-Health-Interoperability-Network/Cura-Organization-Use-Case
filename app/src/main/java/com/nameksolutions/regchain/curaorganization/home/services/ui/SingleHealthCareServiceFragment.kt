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
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.nameksolutions.regchain.curaorganization.R
import com.nameksolutions.regchain.curaorganization.base.BaseFragment
import com.nameksolutions.regchain.curaorganization.databinding.FragmentSingleHealthCareServiceBinding
import com.nameksolutions.regchain.curaorganization.home.services.ServicesApi
import com.nameksolutions.regchain.curaorganization.home.services.ServicesRepo
import com.nameksolutions.regchain.curaorganization.home.services.ServicesViewModel
import com.nameksolutions.regchain.curaorganization.home.services.adapters.ServiceAvailableTimeAdapter
import com.nameksolutions.regchain.curaorganization.network.Resource
import com.nameksolutions.regchain.curaorganization.responses.services.AvailableTime
import com.nameksolutions.regchain.curaorganization.responses.services.HealthcareService
import com.nameksolutions.regchain.curaorganization.responses.services.Telecom
import com.nameksolutions.regchain.curaorganization.utils.handleApiError
import com.nameksolutions.regchain.curaorganization.utils.showProgressDialog
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class SingleHealthCareServiceFragment : BaseFragment<ServicesViewModel, FragmentSingleHealthCareServiceBinding, ServicesRepo>() {


    private val args by navArgs<SingleHealthCareServiceFragmentArgs>()
    private val availableTimeAdapter = ServiceAvailableTimeAdapter()
    private var telecoms = listOf<Telecom>()
    private var progressDialog: Dialog? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val healthCareServiceId = args.healthcareServiceId

        fetchHealthCareServiceDetail(healthCareServiceId)


        binding.servicesMockBackBtn.setOnClickListener {
            findNavController().navigate(R.id.action_singleHealthCareServiceFragment_to_servicesHomeFragment)
        }

    }

    private fun fetchHealthCareServiceDetail(healthCareServiceId: String) {

        viewModel.fetchOneHealthcareService(healthCareServiceId)
        viewModel.fetchOneHealthCareService.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success ->{
                    hideProgress()
                    subscribeToUI(response.value.healthcareservice)
                }
                is Resource.Failure ->{
                    hideProgress()
                    handleApiError(response) {fetchHealthCareServiceDetail(healthCareServiceId)}
                }
                is Resource.Loading ->{
                    showProgress()
                }
            }
        })

    }

    private fun subscribeToUI(value: HealthcareService) {
        with(binding){
            val healthCareServiceNames = mutableListOf<String>(value.name.substring(0, 1))
            healthCareServiceIconText.text = healthCareServiceNames[0]
            txtHealthCareServiceName.text = value.name
            for (category in value.category){
                txtHealthCareServiceCategories.append(category)
            }
            if (value.appointmentRequired)
            healthCareServiceAppointmentRequired.text = "Yes"
            else
                healthCareServiceAppointmentRequired.text = "No"

            telecoms = value.telecom
            for (telecom in telecoms){
                if (telecom.system == "email"){
                    healthCareServiceEmail.text = telecom.value
                }
            }
            for (telecom in telecoms){
                if (telecom.system == "phone" || telecom.system == "mobile"){
                    healthCareServicePhoneNumber.text = telecom.value
                }
            }
            for (communication in value.communitcation){
                healthCareServiceCommunication.append(communication)
            }

            for (char in value.characteristics){
                val chars = mutableListOf<String>()
                for (charCoding in char.coding){
                    chars.add(charCoding.display)
                }
                for (c in chars){
                    healthCareServiceCharacteristics.append(c)
                }
            }

            for (eligibility in value.eligibility){
                val eligibilityList = mutableListOf<String>()
                for (eligibilityCoding in eligibility.coding){
                    eligibilityList.add(eligibilityCoding.display)
                }
                for (e in eligibilityList){
                    healthCareServiceEligibility.append(e)
                }
            }

            healthCareServiceProvidedBy.text = value.providedBy


            val listOfAvailableTimes = mutableListOf<AvailableTime>()
            for (time in value.availableTime){
                    if (time.availableStartTime.isNotEmpty() || time.availableEndTime.isNotEmpty()){
                        listOfAvailableTimes.add(time)
                    }
            }


            val practitionerAvailableTimeLayoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.rvSinglePractitionerAvailableTimes.apply {
                adapter = availableTimeAdapter
                layoutManager = practitionerAvailableTimeLayoutManager
                addItemDecoration(
                    DividerItemDecoration(
                        requireContext(), practitionerAvailableTimeLayoutManager.orientation
                    )
                )
            }

            availableTimeAdapter.submitList(listOfAvailableTimes)

        }
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