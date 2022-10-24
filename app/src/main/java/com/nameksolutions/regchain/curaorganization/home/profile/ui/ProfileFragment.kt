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
import android.text.Editable
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.nameksolutions.regchain.curaorganization.R
import com.nameksolutions.regchain.curaorganization.base.BaseFragment
import com.nameksolutions.regchain.curaorganization.databinding.FragmentPersonnelBinding
import com.nameksolutions.regchain.curaorganization.databinding.FragmentProfileBinding
import com.nameksolutions.regchain.curaorganization.home.personnel.PersonnelApi
import com.nameksolutions.regchain.curaorganization.home.personnel.PersonnelRepo
import com.nameksolutions.regchain.curaorganization.home.personnel.adapters.*
import com.nameksolutions.regchain.curaorganization.home.profile.ProfileApi
import com.nameksolutions.regchain.curaorganization.home.profile.ProfileRepo
import com.nameksolutions.regchain.curaorganization.home.profile.ProfileViewModel
import com.nameksolutions.regchain.curaorganization.home.profile.adapters.AddressAdapter
import com.nameksolutions.regchain.curaorganization.home.profile.adapters.AvailableTimeAdapter
import com.nameksolutions.regchain.curaorganization.home.profile.adapters.IdentifiersAdapter
import com.nameksolutions.regchain.curaorganization.home.profile.adapters.TelecomAdapter
import com.nameksolutions.regchain.curaorganization.network.Resource
import com.nameksolutions.regchain.curaorganization.requests.CreateOrganizationRequest
import com.nameksolutions.regchain.curaorganization.requests.TelecomRequest
import com.nameksolutions.regchain.curaorganization.responses.profile.Organization
import com.nameksolutions.regchain.curaorganization.utils.handleApiError
import com.nameksolutions.regchain.curaorganization.utils.showProgressDialog
import com.nameksolutions.regchain.curaorganization.utils.snackbar
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlin.properties.Delegates

class ProfileFragment : BaseFragment<ProfileViewModel, FragmentProfileBinding, ProfileRepo>() {

    private var progressDialog: Dialog? = null

    private val telecomsAdapter = TelecomAdapter()
    private val addressesAdapter = AddressAdapter()
    private val identifiersAdapter = IdentifiersAdapter()
    private val availableTimeAdapter = AvailableTimeAdapter()

    lateinit var organizationDetail: Organization

    private val createOrganizationRequest = CreateOrganizationRequest()
    private val updateTelecomRequest = TelecomRequest()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchOrganizationInfo()

        with(binding) {
            setNameEditMode(false)

            profileBackBtn.setOnClickListener {
                val homeNav = ProfileFragmentDirections.actionProfileFragmentToHomeFragment()
                findNavController().navigate(homeNav)
            }

            profileAddTelecomButton.setOnClickListener {
                requireView().snackbar("Coming soon...")
            }

            profileAddAddressButton.setOnClickListener {
                requireView().snackbar("Coming soon...")
            }

            profileAddIdentifiersButton.setOnClickListener {
                requireView().snackbar("Coming soon...")
            }

            profileUpdateDaysOfOperationButton.setOnClickListener {
                requireView().snackbar("Coming soon...")
            }


            profileTelecomButton.setOnClickListener {
                // If the CardView is already expanded, set its visibility
                //  to gone and change the expand less icon to expand more.
                if (profileTelecomHideAbleView.visibility == View.VISIBLE) {
                    TransitionManager.beginDelayedTransition(
                        baseCardViewTelecoms,
                        AutoTransition()
                    )

                    profileTelecomHideAbleView.visibility = View.GONE
                    profileTelecomButton.setImageResource(R.drawable.angle_down)
                } else {
                    // If the CardView is not expanded, set its visibility
                    // to visible and change the expand more icon to expand less.
                    TransitionManager.beginDelayedTransition(
                        profileTelecomHideAbleView,
                        AutoTransition()
                    )
                    profileTelecomHideAbleView.visibility = View.VISIBLE
                    profileTelecomButton.setImageResource(R.drawable.angle_up)

                    if (profileAddressesHideAbleView.visibility == View.VISIBLE) {
                        TransitionManager.beginDelayedTransition(
                            baseCardViewAddress,
                            AutoTransition()
                        )
                        profileAddressesHideAbleView.visibility = View.GONE
                        profileAddressesButton.setImageResource(R.drawable.angle_down)
                    }
                    if (profileIdentifiersHideAbleView.visibility == View.VISIBLE) {
                        TransitionManager.beginDelayedTransition(
                            baseCardViewIdentifiers,
                            AutoTransition()
                        )
                        profileIdentifiersHideAbleView.visibility = View.GONE
                        profileIdentifiersButton.setImageResource(R.drawable.angle_down)
                    }
                    if (daysOfOperationHideAbleView.visibility == View.VISIBLE) {
                        TransitionManager.beginDelayedTransition(
                            baseCardViewDaysOfOperation,
                            AutoTransition()
                        )
                        daysOfOperationHideAbleView.visibility = View.GONE
                        profileDaysOfOperationButton.setImageResource(R.drawable.angle_down)
                    }
                }
            }
            profileAddressesButton.setOnClickListener {
                // If the CardView is already expanded, set its visibility
                //  to gone and change the expand less icon to expand more.
                if (profileAddressesHideAbleView.visibility == View.VISIBLE) {
                    TransitionManager.beginDelayedTransition(
                        baseCardViewAddress,
                        AutoTransition()
                    )

                    profileAddressesHideAbleView.visibility = View.GONE
                    profileAddressesButton.setImageResource(R.drawable.angle_down)
                } else {
                    // If the CardView is not expanded, set its visibility
                    // to visible and change the expand more icon to expand less.
                    TransitionManager.beginDelayedTransition(
                        baseCardViewAddress,
                        AutoTransition()
                    )
                    profileAddressesHideAbleView.visibility = View.VISIBLE
                    profileAddressesButton.setImageResource(R.drawable.angle_up)

                    if (profileTelecomHideAbleView.visibility == View.VISIBLE) {
                        TransitionManager.beginDelayedTransition(
                            baseCardViewTelecoms,
                            AutoTransition()
                        )
                        profileTelecomHideAbleView.visibility = View.GONE
                        profileTelecomButton.setImageResource(R.drawable.angle_down)
                    }
                    if (profileIdentifiersHideAbleView.visibility == View.VISIBLE) {
                        TransitionManager.beginDelayedTransition(
                            baseCardViewIdentifiers,
                            AutoTransition()
                        )
                        profileIdentifiersHideAbleView.visibility = View.GONE
                        profileIdentifiersButton.setImageResource(R.drawable.angle_down)
                    }
                    if (daysOfOperationHideAbleView.visibility == View.VISIBLE) {
                        TransitionManager.beginDelayedTransition(
                            baseCardViewDaysOfOperation,
                            AutoTransition()
                        )
                        daysOfOperationHideAbleView.visibility = View.GONE
                        profileDaysOfOperationButton.setImageResource(R.drawable.angle_down)
                    }
                }

            }
            profileIdentifiersButton.setOnClickListener {
                // If the CardView is already expanded, set its visibility
                //  to gone and change the expand less icon to expand more.
                if (profileIdentifiersHideAbleView.visibility == View.VISIBLE) {
                    TransitionManager.beginDelayedTransition(
                        baseCardViewIdentifiers,
                        AutoTransition()
                    )

                    profileIdentifiersHideAbleView.visibility = View.GONE
                    profileIdentifiersButton.setImageResource(R.drawable.angle_down)
                } else {
                    // If the CardView is not expanded, set its visibility
                    // to visible and change the expand more icon to expand less.
                    TransitionManager.beginDelayedTransition(
                        baseCardViewIdentifiers,
                        AutoTransition()
                    )
                    profileIdentifiersHideAbleView.visibility = View.VISIBLE
                    profileIdentifiersButton.setImageResource(R.drawable.angle_up)

                    if (profileAddressesHideAbleView.visibility == View.VISIBLE) {
                        TransitionManager.beginDelayedTransition(
                            baseCardViewAddress,
                            AutoTransition()
                        )
                        profileAddressesHideAbleView.visibility = View.GONE
                        profileAddressesButton.setImageResource(R.drawable.angle_down)
                    }
                    if (profileTelecomHideAbleView.visibility == View.VISIBLE) {
                        TransitionManager.beginDelayedTransition(
                            baseCardViewTelecoms,
                            AutoTransition()
                        )
                        profileTelecomHideAbleView.visibility = View.GONE
                        profileTelecomButton.setImageResource(R.drawable.angle_down)
                    }
                    if (daysOfOperationHideAbleView.visibility == View.VISIBLE) {
                        TransitionManager.beginDelayedTransition(
                            baseCardViewDaysOfOperation,
                            AutoTransition()
                        )
                        daysOfOperationHideAbleView.visibility = View.GONE
                        profileDaysOfOperationButton.setImageResource(R.drawable.angle_down)
                    }
                }

            }
            profileDaysOfOperationButton.setOnClickListener {
                // If the CardView is already expanded, set its visibility
                //  to gone and change the expand less icon to expand more.
                if (daysOfOperationHideAbleView.visibility == View.VISIBLE) {
                    TransitionManager.beginDelayedTransition(
                        baseCardViewDaysOfOperation,
                        AutoTransition()
                    )

                    daysOfOperationHideAbleView.visibility = View.GONE
                    profileDaysOfOperationButton.setImageResource(R.drawable.angle_down)
                } else {
                    // If the CardView is not expanded, set its visibility
                    // to visible and change the expand more icon to expand less.
                    TransitionManager.beginDelayedTransition(
                        baseCardViewDaysOfOperation,
                        AutoTransition()
                    )
                    daysOfOperationHideAbleView.visibility = View.VISIBLE
                    profileDaysOfOperationButton.setImageResource(R.drawable.angle_up)

                    if (profileAddressesHideAbleView.visibility == View.VISIBLE) {
                        TransitionManager.beginDelayedTransition(
                            baseCardViewAddress,
                            AutoTransition()
                        )
                        profileAddressesHideAbleView.visibility = View.GONE
                        profileAddressesButton.setImageResource(R.drawable.angle_down)
                    }
                    if (profileIdentifiersHideAbleView.visibility == View.VISIBLE) {
                        TransitionManager.beginDelayedTransition(
                            baseCardViewIdentifiers,
                            AutoTransition()
                        )
                        profileIdentifiersHideAbleView.visibility = View.GONE
                        profileIdentifiersButton.setImageResource(R.drawable.angle_down)
                    }
                    if (profileTelecomHideAbleView.visibility == View.VISIBLE) {
                        TransitionManager.beginDelayedTransition(
                            baseCardViewTelecoms,
                            AutoTransition()
                        )
                        profileTelecomHideAbleView.visibility = View.GONE
                        profileTelecomButton.setImageResource(R.drawable.angle_down)
                    }
                }

            }

            val profileTelecomsLayoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            val profilesAddressesLayoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            val profileIdentifierLayoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            val profileDaysOfOperationLayoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            rvTelecom.apply {
                adapter = telecomsAdapter
                layoutManager = profileTelecomsLayoutManager
                addItemDecoration(
                    DividerItemDecoration(
                        requireContext(), profileTelecomsLayoutManager.orientation
                    )
                )
            }

            rvAddresses.apply {
                adapter = addressesAdapter
                layoutManager = profilesAddressesLayoutManager
                addItemDecoration(
                    DividerItemDecoration(
                        requireContext(), profilesAddressesLayoutManager.orientation
                    )
                )
            }

            rvIdentifiers.apply {
                adapter = identifiersAdapter
                layoutManager = profileIdentifierLayoutManager
                addItemDecoration(
                    DividerItemDecoration(
                        requireContext(), profileIdentifierLayoutManager.orientation
                    )
                )
            }

            rvDaysOfOperation.apply {
                adapter = availableTimeAdapter
                layoutManager = profileDaysOfOperationLayoutManager
                addItemDecoration(
                    DividerItemDecoration(
                        requireContext(), profileDaysOfOperationLayoutManager.orientation
                    )
                )
            }
        }
    }


    private fun fetchOrganizationInfo() {
        viewModel.getOrganizationInfo()
        viewModel.fetchOrganizationInfo.observe(viewLifecycleOwner, Observer { response ->
            when (response){
                is Resource.Success ->{
                    hideProgress()

                    organizationDetail = response.value.organization

                    val name = response.value.organization.name
                    val aliasNames = response.value.organization.alias
                    val telecoms = response.value.organization.telecom
                    val identifiers = response.value.organization.identifier
                    val addresses = response.value.organization.address
                    val daysOfOperation = response.value.organization._registryIdentifier.availableTime


                    binding.profileOrgNameTxt.text = name
                    for (alias in aliasNames){
                        binding.profileOrgAliasNameTxt.setText("$alias,")
                    }

                    telecomsAdapter.submitList(telecoms)
                    identifiersAdapter.submitList(identifiers)
                    addressesAdapter.submitList(addresses)
                    availableTimeAdapter.submitList(daysOfOperation)

                    enableEdits(response.value.organization)
                }
                is Resource.Failure ->{
                    hideProgress()
                    requireView().snackbar(response.errorBody.toString()) { fetchOrganizationInfo() }
                }
                is Resource.Loading ->{
                    showProgress()
                }
            }
        })

    }

    private fun enableEdits(organization: Organization) {

        with(binding){
            profileNameEditBtn.setOnClickListener {
                setNameEditMode(true)

                var newOrgName: String
                var newOrgAliasName: List<String>

                etProfileOrgName.apply {
                    isVisible = true
                    setText(organization.name)
                }

                etProfileOrgAliasName.apply {
                    isVisible = true
                    for (aliasName in organization.alias)
                        append(aliasName)
                }
                profileNameEditOkTxt.apply {
                    isVisible = true
                    setOnClickListener {
                        newOrgName = etProfileOrgName.text.toString().trim()
                        newOrgAliasName = etProfileOrgAliasName.text.toString().trim().split("\\s*,\\s*")

                        if (newOrgName.isEmpty()){
                            newOrgName = organization.name
                        }
                        if (newOrgAliasName.isEmpty()){
                            newOrgAliasName = organization.alias
                        }

                        updateOrganisationName(newOrgName, newOrgAliasName)
                    }
                }





            }

            profileAddTelecomButton.setOnClickListener {

                lateinit var newTelecomValue: String
                lateinit var newTelecomType: String

                val telecomValueArray = arrayListOf<String>("Email")
                val telecomValueArrayAdapter =
                    ArrayAdapter(
                        requireContext(),
                        R.layout.drop_down_item,
                        telecomValueArray
                    )


                val builder = AlertDialog.Builder(requireContext(),R.style.CustomAlertDialog)
                    .create()
                val view = layoutInflater.inflate(R.layout.custom_telecom_update_layout_dialog,null)
                val telecomTextValue = view.findViewById<TextInputEditText>(R.id.profile_telecom_update_value)
                val telecomType = view.findViewById<AutoCompleteTextView>(R.id.update_telecom_type)
//                val telecomEmailInput = view.findViewById<TextInputLayout>(R.id.text_input_layout_update_telecom_value)
//                val telecomPhoneInput = view.findViewById<TextInputLayout>(R.id.telecom_update_phone_layout)
                val  updateButton = view.findViewById<Button>(R.id.profile_telecom_update_okay_button)
                val  cancelButton = view.findViewById<Button>(R.id.profile_telecom_update_cancel_button)

//                telecomType.addTextChangedListener{
//                    if (it.toString() == "Phone"){
//                        telecomEmailInput.isVisible = false
//                        telecomPhoneInput.isVisible = true
//                    }else{
//                        telecomEmailInput.isVisible = true
//                        telecomPhoneInput.isVisible = false
//                    }
//                }

                telecomType.setAdapter(telecomValueArrayAdapter)
                builder.setView(view)

                updateButton.setOnClickListener {
                    val updatedTelecomValue = telecomTextValue.text.toString().trim()
                    val updatedTelecomType = telecomType.text.toString().trim()

                    updateOrganisationTelecom(updatedTelecomValue, updatedTelecomType, builder)
                }
                cancelButton.setOnClickListener {
                    builder.dismiss()
                }
                builder.setCanceledOnTouchOutside(false)
                builder.show()
            }

        }
    }

    private fun updateOrganisationTelecom(
        updatedTelecomValue: String,
        updatedTelecomType: String,
        builder: AlertDialog
    ) {
        val updatedOrganizationTelecom = updateTelecomRequest.copy(
            value = updatedTelecomValue,
            system = updatedTelecomType,
            use = "Alternative",
            rank = organizationDetail.telecom.lastIndex +1,
            active = true
        )

        viewModel.updateOrganizationTelecom(updatedOrganizationTelecom)
        viewModel.updateOrganizationTelecom.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success ->{
                    builder.dismiss()
                    hideProgress()
                    fetchOrganizationInfo()
                }
                is Resource.Failure ->{
                    hideProgress()
                    handleApiError(response) {updateOrganisationTelecom(updatedTelecomValue, updatedTelecomType, builder)}
                }
                is Resource.Loading ->{
                    showProgress()
                }
            }
        })

    }

    private fun updateOrganisationName(newOrgName: String, newOrgAliasName: List<String>) {
        val updatedOrganizationBody = createOrganizationRequest.copy(
            name = newOrgName,
            alias = newOrgAliasName
        )

        viewModel.updateOrganizationDetails(updatedOrganizationBody)
        viewModel.updateOrganizationDetails.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success ->{
                    hideProgress()
                    setNameEditMode(false)

                    val name = response.value.organization.name
                    val aliasNames = response.value.organization.alias

                    binding.profileOrgNameTxt.text = name
                    for (alias in aliasNames){
                        binding.profileOrgAliasNameTxt.setText("$alias,")
                    }

                }
                is Resource.Failure ->{
                    hideProgress()
                    handleApiError(response) { updateOrganisationName(newOrgName, newOrgAliasName) }
                }
                is Resource.Loading ->{
                    showProgress()
                }
            }
        })
    }

    private fun setNameEditMode(isEditing: Boolean){
        with(binding){
            if (isEditing){ //is in editing mode
                profileNameEditOkTxt.isVisible = true
                etProfileOrgName.isVisible = true
                etProfileOrgAliasName.isVisible = true
                profileOrgNameTxt.isVisible = false
                profileOrgAliasNameTxt.isVisible = false
                profileNameEditBtn.isVisible = false
            }else{ //is not in editing mode
                profileNameEditOkTxt.isVisible = false
                etProfileOrgName.isVisible = false
                etProfileOrgAliasName.isVisible = false
                profileOrgNameTxt.isVisible = true
                profileOrgAliasNameTxt.isVisible = true
                profileNameEditBtn.isVisible = true
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

    override fun getViewModel() = ProfileViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentProfileBinding.inflate(inflater, container, false)

    override fun getFragmentRepo(): ProfileRepo {
        val token = runBlocking { userprefs.authToken.first() }
        val api = remoteDataSource.buildApi(ProfileApi::class.java, token)
        return ProfileRepo(api, userprefs)
    }
}