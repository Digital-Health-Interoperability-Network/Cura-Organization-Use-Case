package com.nameksolutions.regchain.curaorganization.auth.ui.registration

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.nameksolutions.regchain.curaorganization.R
import com.nameksolutions.regchain.curaorganization.auth.AuthApi
import com.nameksolutions.regchain.curaorganization.auth.AuthRepo
import com.nameksolutions.regchain.curaorganization.auth.AuthViewModel
import com.nameksolutions.regchain.curaorganization.base.BaseFragment
import com.nameksolutions.regchain.curaorganization.databinding.FragmentRegRegistryIdentifersBinding
import com.nameksolutions.regchain.curaorganization.network.Resource
import com.nameksolutions.regchain.curaorganization.requests._RegIdentifiers
import com.nameksolutions.regchain.curaorganization.utils.Common.regStepCount
import com.nameksolutions.regchain.curaorganization.utils.getDate
import com.nameksolutions.regchain.curaorganization.utils.handleApiError
import com.nameksolutions.regchain.curaorganization.utils.showProgressDialog
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*


/*
* This fragment class handles the input and registration of the organization registry identifiers details
*
* The registry identifiers details refer to the organisation information issued and recognised by the
* local governmental body of the organisation
*
* It collects the following organisation information and adds to the table in the database
* The details collected in this class UI include:
* - Organisation State Unique ID
* - Organisation Local Registration Number
* - Organisation Ownership Type
* - Organisation Ownership
* - Organisation Facility Level
* - Organisation Facility Level Option
* - Organisation Start Date
*
* */

class RegRegistryIdentifiers :
    BaseFragment<AuthViewModel, FragmentRegRegistryIdentifersBinding, AuthRepo>() {

    private var progressDialog: Dialog? = null
    private lateinit var nextBtn: Button
    var cal = Calendar.getInstance()


    private lateinit var organizationStateUniqueId: String
    private lateinit var organizationRegistrationNo: String
    private lateinit var organizationOwnerShipType: String
    private lateinit var organizationOwnership: String
    private lateinit var organizationStartDate: String
    private lateinit var organizationFacilityLevel: String
    private lateinit var organizationFacilityLevelOption: String

    val gson = Gson()

    private val TAG = "EQUA"


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        regStepCount = +1
//        (requireActivity() as RegActivity).currentRegStep = 2

        with(binding) {
            val ownershipTypeArray = resources.getStringArray(R.array.ownership_type)
            val ownershipTypeArrayAdapter =
                ArrayAdapter(requireContext(), R.layout.drop_down_item, ownershipTypeArray)
            regOwnershipType.setAdapter(ownershipTypeArrayAdapter)


            val ownershipArray = resources.getStringArray(R.array.ownership)
            val ownershipArrayAdapter =
                ArrayAdapter(requireContext(), R.layout.drop_down_item, ownershipArray)
            regOwnership.setAdapter(ownershipArrayAdapter)


            val facilityLevelArray = resources.getStringArray(R.array.facility_level)
            val facilityLevelArrayAdapter =
                ArrayAdapter(requireContext(), R.layout.drop_down_item, facilityLevelArray)
            regFacilityLevel.setAdapter(facilityLevelArrayAdapter)


            val facilityLevelOptionArray = resources.getStringArray(R.array.facility_level_option)
            val facilityLevelOptionArrayAdapter =
                ArrayAdapter(requireContext(), R.layout.drop_down_item, facilityLevelOptionArray)
            regFacilityLevelOption.setAdapter(facilityLevelOptionArrayAdapter)


            // create an OnDateSetListener for Organization Start Date
            val startDateSetListener = object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(
                    view: DatePicker, year: Int, monthOfYear: Int,
                    dayOfMonth: Int
                ) {
                    cal.set(Calendar.YEAR, year)
                    cal.set(Calendar.MONTH, monthOfYear)
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    updateStartDateInView()
                }
            }

            regOrganizationStartDate!!.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View?) {
                    DatePickerDialog(
                        requireContext(),
                        startDateSetListener, // set DatePickerDialog to point to today's date when it loads up
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }

            })

            nextBtn = (requireActivity() as RegActivity).btnNext

            nextBtn.setOnClickListener {

                organizationStateUniqueId = regIdStateId.text.toString().trim()
                organizationRegistrationNo = regIdRegNo.text.toString().trim()
                organizationOwnerShipType = regOwnershipType.text.toString()
                organizationOwnership = regOwnership.text.toString()
                organizationFacilityLevel = regFacilityLevel.text.toString()
                organizationFacilityLevelOption = regFacilityLevelOption.text.toString()
                organizationStartDate = regOrganizationStartDate.text.toString()

                performValidation()

            }

        }


    }

    //this function performs validation on the entered data
    private fun performValidation() {
        with(binding) {
            //if organization state unique id field is empty
            if (organizationStateUniqueId.isEmpty()) {
                textInputLayoutRegIdStateId.error = "Organization State Unique ID Required"
                regIdStateId.requestFocus()
                return
            }
            //if organization registration number field is empty
            if (organizationRegistrationNo.isEmpty()) {
                textInputLayoutRegIdRegNo.error = "Organization Registration Number Required"
                regIdRegNo.requestFocus()
                return
            }
            //if organization ownership type field is empty
            if (organizationOwnerShipType.isEmpty()) {
                txtInputLayoutOwnershipType.error =
                    "Select Organization Ownership Type"
                return
            }
            //if organization ownership field is empty
            if (organizationOwnership.isEmpty()) {
                txtInputLayoutOwnership.error = "Select Organization Ownership"
                return
            }

            //if organization phone number field is empty
            if (organizationStartDate.isEmpty()) {
                textInputLayoutRegOrganizationStartDate!!.error = "Organization Start Date Required"
                regOrganizationStartDate!!.requestFocus()
                return
            }
            //if organization national identifier type is not selected
            if (organizationFacilityLevel.isEmpty()) {
                txtInputLayoutFacilityLevel.error = "Select Organization Facility Level"
                return
            }
            //if organization national identifier field is empty
            if (organizationFacilityLevelOption.isEmpty()) {
                txtInputLayoutFacilityLevelOption.error =
                    "Select Organization Facility Level Option"
                return
            } else {
                //if the validation is passed...

                val nowTime = System.currentTimeMillis().toLong()
                val timeEdited = getDate(nowTime, "dd/MM/yyyy")
                val regIDs = _RegIdentifiers(
                    EndDate = timeEdited,
                    facilityLevel = organizationFacilityLevel,
                    ownership = organizationOwnership,
                    ownershipType = organizationOwnerShipType,
                    registrationNO = organizationRegistrationNo,
                    startDate = organizationStartDate,
                    stateUID = organizationStateUniqueId
                )

                val regIDsGson = gson.toJson(regIDs).toString()

                val newOrganizationRegIDs: CreateOrganizationRequest = CreateOrganizationRequest(
                    _registryIdentifier = regIDs
                )

                addRegIDs(newOrganizationRegIDs)
            }
        }
    }

    //this function registers the organisation local registry identifiers details to the database
    private fun addRegIDs(newOrganizationRegIDs: CreateOrganizationRequest) {

        viewModel.addOrganizationDetails(newOrganizationRegIDs)
        viewModel.organizationDetailsUpdate.observe(
            viewLifecycleOwner, Observer {
                when(it){
                    is Resource.Success -> {
                        hideProgress()
                        Log.d(TAG, "addRegIDs: Registered Reg IDs")
                        //navigate to days and hours of operation
                        regStepCount++
                        Log.d(TAG, "registerOrganizationRegStepCount: $regStepCount")
                        findNavController().navigate(R.id.action_regRegistryIdentifiers_to_regDaysOfOperation)
                    }
                    is Resource.Failure -> {
                        hideProgress()
                        handleApiError(it) { addRegIDs(newOrganizationRegIDs) }
                    }
                    is Resource.Loading -> {
                        showProgress()
                    }
                }
            }
        )
    }


    private fun updateStartDateInView() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.regOrganizationStartDate!!.setText(sdf.format(cal.getTime()).toString())
    }


    private fun showProgress() {
        hideProgress()
        progressDialog = requireActivity().showProgressDialog()
    }

    private fun hideProgress() {
        progressDialog?.let { if (it.isShowing) it.cancel() }
    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentRegRegistryIdentifersBinding.inflate(inflater, container, false)

    override fun getFragmentRepo(): AuthRepo {
        val token = runBlocking { userprefs.authToken.first() }
        val api = remoteDataSource.buildApi(AuthApi::class.java, token)
        return AuthRepo(api, userprefs)
    }

}