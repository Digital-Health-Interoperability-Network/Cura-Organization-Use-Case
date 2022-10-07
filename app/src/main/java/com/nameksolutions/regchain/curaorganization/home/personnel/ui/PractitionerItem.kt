package com.nameksolutions.regchain.curaorganization.home.personnel.ui

import android.app.Dialog
import android.os.Bundle
import android.util.Log
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
import com.nameksolutions.regchain.curaorganization.databinding.FragmentPractitionerItemBinding
import com.nameksolutions.regchain.curaorganization.home.personnel.PersonnelApi
import com.nameksolutions.regchain.curaorganization.home.personnel.PersonnelRepo
import com.nameksolutions.regchain.curaorganization.home.personnel.PersonnelViewModel
import com.nameksolutions.regchain.curaorganization.home.personnel.adapters.PractitionerAvailableTImeAdapter
import com.nameksolutions.regchain.curaorganization.network.Resource
import com.nameksolutions.regchain.curaorganization.responses.AvailableTime
import com.nameksolutions.regchain.curaorganization.responses.PersonnelResponse
import com.nameksolutions.regchain.curaorganization.responses.PractitionerRole
import com.nameksolutions.regchain.curaorganization.responses.TelecomResponse
import com.nameksolutions.regchain.curaorganization.utils.Common.TAG
import com.nameksolutions.regchain.curaorganization.utils.handleApiError
import com.nameksolutions.regchain.curaorganization.utils.showProgressDialog
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class PractitionerItem : BaseFragment<PersonnelViewModel, FragmentPractitionerItemBinding, PersonnelRepo>() {

    val args by navArgs<PractitionerItemArgs>()
    val availableTimeAdapter = PractitionerAvailableTImeAdapter()
    private var practitionerRoles = listOf<PractitionerRole>()
    private var telecoms = listOf<TelecomResponse>()
    private var progressDialog: Dialog? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val practitionerId = args.currentPractitionerId

        fetchPractitionerDetail(practitionerId)

        with(binding){

            mockBackBtn.setOnClickListener {
                findNavController().navigate(R.id.action_practitionerItem_to_personnelFragment)
            }
            
        }

    }

    private fun fetchPractitionerDetail(practitionerId: String) {
        viewModel.getOnePractitioner(practitionerId)
        viewModel.practitionerDetailsFetch.observe(viewLifecycleOwner, Observer { response ->
            Log.d(TAG, "fetchAllPractitionersStatsPre: ${response.toString()}")
            when (response) {
                is Resource.Success -> {
                    hideProgress()

                    val practitionerNames = mutableListOf<String>(response.value.name!!.given[0].substring(0, 1), response.value.name.family.substring(0, 1))
                    binding.practitionerIconText.text = "${practitionerNames[0]}${practitionerNames[1]}"
                    binding.txtPractitionerName.text = "${response.value.name.given[0]} ${response.value.name.family}"
                    practitionerRoles = response.value.practitionerRoles
                    for (role in practitionerRoles){
                        binding.txtPractitionerRoles.append(role.code.toString())
                    }

                    telecoms = response.value.telecom
                    binding.singlePractitionerGender.text = response.value.gender
                    for (telecom in telecoms){
                        if (telecom.system == "email"){
                            binding.singlePractitionerEmail.text = telecom.value
                        }
                    }
                    for (telecom in telecoms){
                        if (telecom.system == "phone" || telecom.system == "mobile"){
                            binding.singlePractitionerPhoneNumber.text = telecom.value
                        }
                    }
                    val listOfAvailableTimes = mutableListOf<AvailableTime>()
                    for (role in practitionerRoles){
                        val availableTimes = role.availableTime
                        for (availableTime in availableTimes!!){
                            if (availableTime.availableStartTime.isNotEmpty() || availableTime.availableEndTime.isNotEmpty()){
                                listOfAvailableTimes.add(availableTime)
                            }

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
                is Resource.Failure -> {
                    hideProgress()
                    Log.d(TAG, "fetchAllPractitionersStats: $response")

                    handleApiError(response) { fetchPractitionerDetail(practitionerId) }
                }
                is Resource.Loading -> {
                    showProgress()
                }
            }
        })
    }



    private fun showProgress() {
        hideProgress()
        progressDialog = requireActivity().showProgressDialog()
    }

    private fun hideProgress() {
        progressDialog?.let { if (it.isShowing) it.cancel() }
    }



    override fun getViewModel() = PersonnelViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPractitionerItemBinding.inflate(inflater, container, false)

    override fun getFragmentRepo(): PersonnelRepo {
        val token = runBlocking { userprefs.authToken.first() }
        val api = remoteDataSource.buildApi(PersonnelApi::class.java, token)
        return PersonnelRepo(api, userprefs)
    }

}