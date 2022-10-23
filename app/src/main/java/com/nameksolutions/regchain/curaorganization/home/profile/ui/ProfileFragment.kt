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
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
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
import com.nameksolutions.regchain.curaorganization.utils.showProgressDialog
import com.nameksolutions.regchain.curaorganization.utils.snackbar
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class ProfileFragment : BaseFragment<ProfileViewModel, FragmentProfileBinding, ProfileRepo>() {

    private var progressDialog: Dialog? = null

    private val telecomsAdapter = TelecomAdapter()
    private val addressesAdapter = AddressAdapter()
    private val identifiersAdapter = IdentifiersAdapter()
    private val availableTimeAdapter = AvailableTimeAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchOrganizationInfo()

        with(binding) {

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