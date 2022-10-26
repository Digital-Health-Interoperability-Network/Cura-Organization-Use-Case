package com.nameksolutions.regchain.curaorganization.home.personnel.ui

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
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
import com.nameksolutions.regchain.curaorganization.responses.*
import com.nameksolutions.regchain.curaorganization.utils.Common.TAG
import com.nameksolutions.regchain.curaorganization.utils.handleApiError
import com.nameksolutions.regchain.curaorganization.utils.showProgressDialog
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class PractitionerItem : BaseFragment<PersonnelViewModel, FragmentPractitionerItemBinding, PersonnelRepo>() {

    private val args by navArgs<PractitionerItemArgs>()
    private val availableTimeAdapter = PractitionerAvailableTImeAdapter()
    private var practitionerRoles = listOf<PractitionerRoleX>()
    private var telecoms = listOf<Telecom>()
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

        viewModel.onePractitionerInfo.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgress()
                    Log.d(TAG, "fetchAllPractitionersStatsPre: ${response.value.toString()}")
                    val practitionerNames = mutableListOf<String>(response.value.practitioner.name.given[0].substring(0, 1), response.value.practitioner.name.family.substring(0, 1))
                    binding.practitionerIconText.text = "${practitionerNames[0]}${practitionerNames[1]}"
                    binding.txtPractitionerName.text = "${response.value.practitioner.name.given[0]} ${response.value.practitioner.name.family}"
                    practitionerRoles = response.value.practitioner.practitionerRoles
                    for (role in practitionerRoles){
                        binding.txtPractitionerRoles.append(role.code.toString())
                    }
                    val practitionerSurName = response.value.practitioner.name.family
                    val practitionerFirstName = response.value.practitioner.name.given[0]

                    telecoms = response.value.practitioner.telecom
                    binding.singlePractitionerGender.text = response.value.practitioner.gender
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
                    val listOfAvailableTimes = mutableListOf<AvailableTimeX>()
                    for (role in practitionerRoles){
                        val availableTimes = role.availableTime
                        for (availableTime in availableTimes){
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

                    binding.practitionerEditBtn.isVisible = true
                    binding.practitionerEditBtn.setOnClickListener {
                        val editRoleDirection = PractitionerItemDirections.actionPractitionerItemToNewPractitionerRoleFragment(practitionerId, practitionerSurName, practitionerFirstName)
                        findNavController().navigate(editRoleDirection)
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