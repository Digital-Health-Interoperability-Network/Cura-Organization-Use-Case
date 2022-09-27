/*
 * Copyright (c) 2022.
 * Richard Uzor
 */

package com.nameksolutions.regchain.curaorganization.home.personnel.ui

import android.app.Dialog
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.hbb20.CountryCodePicker
import com.nameksolutions.regchain.curaorganization.R
import com.nameksolutions.regchain.curaorganization.base.BaseFragment
import com.nameksolutions.regchain.curaorganization.databinding.FragmentNewPersonnelBinding
import com.nameksolutions.regchain.curaorganization.home.personnel.PersonnelApi
import com.nameksolutions.regchain.curaorganization.home.personnel.PersonnelRepo
import com.nameksolutions.regchain.curaorganization.home.personnel.PersonnelViewModel
import com.nameksolutions.regchain.curaorganization.network.Resource
import com.nameksolutions.regchain.curaorganization.requests.*
import com.nameksolutions.regchain.curaorganization.utils.*
import com.nameksolutions.regchain.curaorganization.utils.Common.TAG
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.*
//
class NewPersonnelFragment :
    BaseFragment<PersonnelViewModel, FragmentNewPersonnelBinding, PersonnelRepo>(),
    CountryCodePicker.OnCountryChangeListener {


    private lateinit var newPractitionerRole: String
    private lateinit var newPractitionerSurName: String
    private lateinit var newPractitionerOtherNames: String
    private lateinit var newPractitionerNamePrefix: String
    private lateinit var newPractitionerGender: String
    private lateinit var newPractitionerPhoneNumber: String
    private lateinit var newPractitionerEmail: String
    private lateinit var newPractitionerCountryCodePicker: CountryCodePicker
    private lateinit var newPractitionerIdentifierValue: String
    private lateinit var newPractitionerIdentifierType: String

    lateinit var practitionerPhoneNumberCode: String

    private val telco = Telecom()
    private val idfier = Identifier()
//    private val practRole = PractitionerRole()
    private val availTime = AvailableTime()

    private var identifiers: MutableList<Identifier> = mutableListOf()
    private var telecom: MutableList<Telecom> = mutableListOf()
//    private var practitionerRoles: MutableList<PractitionerRole> = mutableListOf()
    private var availableTime: MutableList<AvailableTime> = mutableListOf()
    private var practitionerNamePrefix = mutableListOf<String>()
    private var practitionerOtherNames = mutableListOf<String>()

    lateinit var timePicker: TimePickerHelper
    lateinit var openHour: String
    private var availabilityMon: AvailableTime = AvailableTime()
    private var availabilityTue: AvailableTime = AvailableTime()
    private var availabilityWed: AvailableTime = AvailableTime()
    private var availabilityThurs: AvailableTime = AvailableTime()
    private var availabilityFri: AvailableTime = AvailableTime()
    private var availabilitySat: AvailableTime = AvailableTime()
    private var availabilitySun: AvailableTime = AvailableTime()
    var hoursDaysOfOperation: HashMap<String, String> = hashMapOf()


    private var telecomRank = 0
    private var progressDialog: Dialog? = null


    val roleDelimiter = ","

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //fetch practitioner roles from back end and add to list in common

        getPractitionerRoleList()

        timePicker = TimePickerHelper(requireContext(), true, false)




        with(binding) {


            val practitionerTitleArray = resources.getStringArray(R.array.name_prefixes)
            val practitionerTitleArrayAdapter =
                ArrayAdapter(requireContext(), R.layout.drop_down_item, practitionerTitleArray)
            regPractitionerNamePrefix.setAdapter(practitionerTitleArrayAdapter)

            val practitionerGenderArray = resources.getStringArray(R.array.gender)
            val practitionerGenderArrayAdapter =
                ArrayAdapter(requireContext(), R.layout.drop_down_item, practitionerGenderArray)
            regPractitionerGender.setAdapter(practitionerGenderArrayAdapter)

            val practitionerIdentifierArray = resources.getStringArray(R.array.id_types)
            val practitionerIdentifierArrayAdapter =
                ArrayAdapter(requireContext(), R.layout.drop_down_item, practitionerIdentifierArray)
            regPractitionerIdType.setAdapter(practitionerIdentifierArrayAdapter)

            practitionersAvailableTimesButton.setOnClickListener {
                // If the CardView is already expanded, set its visibility
                //  to gone and change the expand less icon to expand more.
                if (practitionersAvailableTimesHideAbleView.visibility == View.VISIBLE) {
                    TransitionManager.beginDelayedTransition(
                        baseCardViewPractitionerAvailableTimes,
                        AutoTransition()
                    )

                    practitionersAvailableTimesHideAbleView.visibility = View.GONE
                    practitionersAvailableTimesButton.setImageResource(R.drawable.angle_down)
                } else {
                    // If the CardView is not expanded, set its visibility
                    // to visible and change the expand more icon to expand less.
                    TransitionManager.beginDelayedTransition(
                        baseCardViewPractitionerAvailableTimes,
                        AutoTransition()
                    )
                    practitionersAvailableTimesHideAbleView.visibility = View.VISIBLE
                    practitionersAvailableTimesButton.setImageResource(R.drawable.angle_up)


                }


            }

            layoutPractitionerMonday.enable(false)
            layoutPractitionerTuesday.enable(false)
            layoutPractitionerWednesday.enable(false)
            layoutPractitionerThursday.enable(false)
            layoutPractitionerFriday.enable(false)
            layoutPractitionerSaturday.enable(false)
            layoutPractitionerSunday.enable(false)

            checkBoxPractitionerMonday.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    checkBoxPractitionerMonday.isChecked = true
                    layoutPractitionerMonday.enable(true)
                    //fetch Monday times in a function and return the values
                    regPractitionerMondayOpen.setOnClickListener {
                        val monOpenTimeView = it as TextView
                        showTimePickerDialog(monOpenTimeView)

                        Log.d(
                            TAG, "onActivityCreated: ${
                                monOpenTimeView.text.toString()
                            }"
                        )
                        Log.d(TAG, "onActivityCreated: ${showTimePickerDialog(monOpenTimeView)}")
                    }

                    Log.d(TAG, "onActivityCreatedPost: ${regPractitionerMondayOpen.text}")


                    regPractitionerMondayClose.setOnClickListener {

                        val closeMonTV = it as TextView
                        showTimePickerDialog(closeMonTV)

                    }

                } else {
                    checkBoxPractitionerMonday.isChecked = false
                    layoutPractitionerMonday.enable(false)

                }

            }
            checkBoxPractitionerTuesday.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    checkBoxPractitionerTuesday.isChecked = true
                    layoutPractitionerTuesday.enable(true)
                    //fetch Tuesday times in a function and return the values

                    practitionerTuesdayOpen.setOnClickListener {

                        val tueOpenTV = it as TextView
                        showTimePickerDialog(tueOpenTV)

                    }
                    practitionerTuesdayClose.setOnClickListener {
                        val tueCloseTV = it as TextView
                        showTimePickerDialog(tueCloseTV)

                    }


                } else {
                    checkBoxPractitionerTuesday.isChecked = false
                    layoutPractitionerTuesday.enable(false)

                }

            }
            checkBoxPractitionerWednesday.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) { //if it is not checked
                    checkBoxPractitionerWednesday.isChecked = true
                    layoutPractitionerWednesday.enable(true)
                    //fetch Wednesday times in a function and return the values
                    practitionerWednesdayOpen.setOnClickListener {
                        val wedOpenTV = it as TextView
                        showTimePickerDialog(wedOpenTV)
                    }
                    practitionerWednesdayClose.setOnClickListener {
                        val wedCloseTV = it as TextView
                        showTimePickerDialog(wedCloseTV)
                    }
                } else {
                    checkBoxPractitionerWednesday.isChecked = false
                    layoutPractitionerWednesday.enable(false)
                }


                //availableTime.add(availabilityWed)
            }
            checkBoxPractitionerThursday.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) { //if it is not checked
                    checkBoxPractitionerThursday.isChecked = true
                    layoutPractitionerThursday.enable(true)
                    //fetch Thursday times in a function and return the values
                    practitionerThursdayOpen.setOnClickListener {
                        val thursOpenTV = it as TextView
                        showTimePickerDialog(thursOpenTV)
                    }
                    practitionerThursdayClose.setOnClickListener {
                        val thursCloseTV = it as TextView
                        showTimePickerDialog(thursCloseTV)
                    }
                } else {
                    checkBoxPractitionerThursday.isChecked = false
                    layoutPractitionerThursday.enable(false)
                }


            }
            checkBoxPractitionerFriday.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) { //if it is not checked
                    checkBoxPractitionerFriday.isChecked = true
                    layoutPractitionerFriday.enable(true)
                    //fetch Friday times in a function and return the values
                    practitionerFridayOpen.setOnClickListener {
                        val friOpenTV = it as TextView
                        showTimePickerDialog(friOpenTV)
                    }
                    practitionerFridayClose.setOnClickListener {
                        val friCloseTV = it as TextView
                        showTimePickerDialog(friCloseTV)
                    }
                } else {
                    checkBoxPractitionerFriday.isChecked = false
                    layoutPractitionerFriday.enable(false)
                }

            }
            checkBoxPractitionerSaturday.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) { //if it is not checked
                    checkBoxPractitionerSaturday.isChecked = true
                    layoutPractitionerSaturday.enable(true)
                    //fetch Saturday times in a function and return the values
                    practitionerSaturdayOpen.setOnClickListener {
                        val satOpenTV = it as TextView
                        showTimePickerDialog(satOpenTV)
                    }
                    practitionerSaturdayClose.setOnClickListener {
                        val satCloseTV = it as TextView
                        showTimePickerDialog(satCloseTV)
                    }
                } else {
                    checkBoxPractitionerSaturday.isChecked = false
                    layoutPractitionerSaturday.enable(false)
                }

            }
            checkBoxPractitionerSunday.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) { //if it is not checked
                    checkBoxPractitionerSunday.isChecked = true
                    layoutPractitionerSunday.enable(true)
                    //fetch Sunday times in a function and return the values
                    practitionerSundayOpen.setOnClickListener {
                        val sunOpenTV = it as TextView
                        showTimePickerDialog(sunOpenTV)
                    }
                    practitionerSundayClose.setOnClickListener {
                        val sunCloseTV = it as TextView
                        showTimePickerDialog(sunCloseTV)
                    }
                } else {
                    checkBoxPractitionerSunday.isChecked = false
                    layoutPractitionerSunday.enable(false)
                }

            }


            btnPractitionerCancel.setOnClickListener {
                findNavController().navigate(R.id.action_newPersonnelFragment_to_personnelFragment)
            }

            btnPractitionerRegister.setOnClickListener {
                newPractitionerCountryCodePicker.registerCarrierNumberEditText(
                    practitionerPhoneNumber
                )

                if (checkBoxPractitionerMonday.isChecked){
                    availabilityMon = availTime.copy(
                        availableStartTime = regPractitionerMondayOpen.text.toString().trim(),
                        availableEndTime = regPractitionerMondayClose.text.toString().trim(),
                        daysOfWeek = listOf("Mon")
                    )
                    availableTime.add(availabilityMon)
                }
                if (checkBoxPractitionerTuesday.isChecked){
                    availabilityTue = availTime.copy(
                        availableStartTime = practitionerTuesdayOpen.text.toString().trim(),
                        availableEndTime = practitionerTuesdayClose.text.toString().trim(),
                        daysOfWeek = listOf("Tue")
                    )
                    availableTime.add(availabilityTue)
                }
                if (checkBoxPractitionerWednesday.isChecked){
                    availabilityWed = availTime.copy(
                        availableStartTime = practitionerWednesdayOpen.text.toString().trim(),
                        availableEndTime = practitionerWednesdayClose.text.toString().trim(),
                        daysOfWeek = listOf("Wed")
                    )
                    availableTime.add(availabilityWed)
                }
                if (checkBoxPractitionerThursday.isChecked){
                    availabilityThurs = availTime.copy(
                        availableStartTime = practitionerThursdayOpen.text.toString().trim(),
                        availableEndTime = practitionerThursdayClose.text.toString().trim(),
                        daysOfWeek = listOf("Thurs")
                    )
                    availableTime.add(availabilityThurs)
                }
                if (checkBoxPractitionerFriday.isChecked){
                    availabilityFri = availTime.copy(
                        availableStartTime = practitionerFridayOpen.text.toString().trim(),
                        availableEndTime = practitionerFridayClose.text.toString().trim(),
                        daysOfWeek = listOf("Fri"),
                        allDay = true
                    )
                    availableTime.add(availabilityFri)
                }
                if (checkBoxPractitionerSaturday.isChecked){
                    availabilitySat = availTime.copy(
                        availableStartTime = practitionerSaturdayOpen.text.toString().trim(),
                        availableEndTime = practitionerSaturdayClose.text.toString().trim(),
                        daysOfWeek = listOf("Sat")
                    )
                    availableTime.add(availabilitySat)
                }
                if (checkBoxPractitionerSunday.isChecked){
                    availabilitySun = availTime.copy(
                        availableStartTime = practitionerSundayOpen.text.toString().trim(),
                        availableEndTime = practitionerSundayClose.text.toString().trim(),
                        daysOfWeek = listOf("Sun")
                    )
                    availableTime.add(availabilitySun)
                }

                newPractitionerNamePrefix = regPractitionerNamePrefix.text.toString().trim()
                newPractitionerSurName = regPractitionerSurname.text.toString().trim()
                newPractitionerOtherNames = regPractitionerOtherNames.text.toString().trim()
                newPractitionerGender = regPractitionerGender.text.toString().trim()
                newPractitionerIdentifierType = regPractitionerIdType.text.toString().trim()
                newPractitionerIdentifierValue =
                    regPractitionerIdentifierValue.text.toString().trim()
                newPractitionerEmail = regPractitionerEmail.text.toString().trim()
                newPractitionerCountryCodePicker.setOnCountryChangeListener(this@NewPersonnelFragment)
                newPractitionerPhoneNumber = newPractitionerCountryCodePicker.fullNumberWithPlus
                newPractitionerRole = regPractitionerRole.text.toString().trim()

//                newPractitionerRole =
//                    Pattern.compile(roleDelimiter).split(regPractitionerRole.text.toString().trim())

//                availableTime = mutableListOf(
//                    availabilityMon,
//                    availabilityTue,
//                    availabilityWed,
//                    availabilityThurs,
//                    availabilityFri,
//                    availabilitySat,
//                    availabilitySun
//                )
                Log.d(TAG, "onActivityCreated: $availableTime")
                if (availableTime.isEmpty()) {
                    requireView().snackbar("At least one day required")
                } else {
//
//                    val practitionerRole = practRole.copy(
//                        availableTime = availableTime,
//                        code = newPractitionerRole,
//                        specialty = null
//                    )

                    val practitionerRoleRequest = PractitionerRole(
                        practitionerRole = listOf(practitionerRole)
                    )


//                    Log.d(TAG, "onActivityCreated: $practitionerRole")
//                    performValidation(practitionerRole)

                }

            }

        }



    }


    private fun performValidation(practitionerRoleRequest: PractitionerRole) {
        with(binding) {
            //if new practitioner name prefix field is empty
            if (newPractitionerNamePrefix.isEmpty()) {
                textInputLayoutNamePrefix.error = "Practitioner Title Required"
                regPractitionerNamePrefix.requestFocus()
                return
            }
            //if new practitioner role field is empty
            if (newPractitionerRole.isEmpty()) {
                textInputLayoutPractitionerRole.error = "Practitioner Role Required"
                regPractitionerRole.requestFocus()
                return
            }
            //if new practitioner surname field is empty
            if (newPractitionerSurName.isEmpty()) {
                textInputLayoutRegPractitionerSurname.error = "Practitioner Surname Required"
                regPractitionerSurname.requestFocus()
                return
            }
            //if new practitioner other name field is empty
            if (newPractitionerOtherNames.isEmpty()) {
                textInputLayoutRegPractitionerOtherNames.error = "Practitioner Other Name Required"
                regPractitionerOtherNames.requestFocus()
                return
            }
            //if new practitioner gender field is empty
            if (newPractitionerGender.isEmpty()) {
                textInputLayoutPractitionerGender.error = "Practitioner Gender Required"
                regPractitionerGender.requestFocus()
                return
            }
            //if new practitioner ID Type field is empty
            if (newPractitionerIdentifierType.isEmpty()) {
                textInputLayoutPractitionerIdType.error = "Practitioner ID Type Required"
                regPractitionerIdType.requestFocus()
                return
            }
            //if new practitioner ID Number field is empty
            if (newPractitionerIdentifierValue.isEmpty()) {
                textInputLayoutRegPractitionerIdentifier.error = "Practitioner ID Number Required"
                regPractitionerIdentifierValue.requestFocus()
                return
            }
            //if new practitioner email field is empty
            if (newPractitionerEmail.isEmpty()) {
                textInputLayoutRegPractitionerEmail.error = "Practitioner Email Required"
                regPractitionerEmail.requestFocus()
                return
            }
            //checks if the entered organization email is valid
            if (!Patterns.EMAIL_ADDRESS.matcher(newPractitionerEmail).matches()) {
                binding.textInputLayoutRegPractitionerEmail.error = "Input Valid Email"
                regPractitionerEmail.requestFocus()
            }
            //if new practitioner name prefix field is empty
            if (newPractitionerPhoneNumber.isEmpty()) {
                textInputLayoutNewPractitionerPhoneNumber.error =
                    "Practitioner Phone Number Required"
                return
            } else {
                //all requirements are satisfied
                val email = telco.copy(
                    system = "email",
                    rank = telecomRank++,
                    value = newPractitionerEmail,
                    use = "official"
                )

                val phone = telco.copy(
                    system = "phone",
                    rank = telecomRank++,
                    value = newPractitionerPhoneNumber,
                    use = "official"
                )

                val identity = idfier.copy(
                    use = "official",
                    system = newPractitionerIdentifierType,
                    value = newPractitionerIdentifierValue
                )



                telecom.add(email)
                telecom.add(phone)
                identifiers.add(identity)
                practitionerNamePrefix.add(newPractitionerNamePrefix)
                practitionerOtherNames.add(newPractitionerOtherNames)

                val name = Name(
                    family = newPractitionerSurName,
                    given = practitionerOtherNames,
                    prefix = practitionerNamePrefix,
                    suffix = null,
                    use = "Official"
                )


                val newPractitioner = PractitionerRequest(
                    active = true,
                    address = null,
                    birthDate = null,
                    communication = null,
                    gender = newPractitionerGender,
                    identifier = identifiers,
                    name = name,
                    telecom = telecom,
                    qualification = null,
                    practitionerRole = mutableListOf(practitionerRoleRequest)
                )

                createNewPractitioner(newPractitioner)

                Log.d(TAG, "performValidation: $newPractitioner")

                // TODO: send practitioner role
//                sendPractitionerRole(practitionerRoleRequest)

            }
        }
    }

    private fun getPractitionerRoleList() {
        viewModel.getPractitionerRolesList()
        viewModel.practitionerRoleListResponse.observe(viewLifecycleOwner, Observer { it ->
            when (it) {
                is Resource.Success -> {
                    hideProgress()
                    requireView().snackbar("Roles Fetched")
                    val fetchedPractitionerRoles =
                        it.value.practitionerRolesList.listOfPractitionerRoles
                    Log.d(
                        TAG,
                        "getPractitionerRoleList: ${it.value.practitionerRolesList.listOfPractitionerRoles}"
                    )
                    val practitionerRolesArrayAdapter =
                        ArrayAdapter(
                            requireContext(),
                            R.layout.drop_down_item,
                            fetchedPractitionerRoles
                        )
                    binding.regPractitionerRole.setAdapter(practitionerRolesArrayAdapter)

                }
                is Resource.Failure -> {
                    hideProgress()
                    handleApiError(it) { getPractitionerRoleList() }
                }
                is Resource.Loading -> {
                    showProgress()
                }
            }
        })


    }

    private fun createNewPractitioner(newPractitioner: PractitionerRequest) {

        viewModel.createPractitioner(newPractitioner)
        viewModel.practitionerCreationResponse.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    hideProgress()
                    requireView().snackbar("Practitioner Details Entered")
                    //navigate to ??
                    findNavController().navigate(R.id.action_newPersonnelFragment_to_personnelFragment)
                }
                is Resource.Failure -> {
                    hideProgress()
                    Log.d(TAG, "createNewPractitioner: $it")
                    handleApiError(it) { createNewPractitioner(newPractitioner) }
                }
                is Resource.Loading -> {
                    showProgress()
                }
            }

        })

    }

    private fun showTimePickerDialog(timeView: TextView) {
        val cal = Calendar.getInstance()
        val h = cal.get(Calendar.HOUR_OF_DAY)
        val m = cal.get(Calendar.MINUTE)


        var hourTime = ""
        var minTime = ""
        var am_pm = ""


        val testVar = timePicker.showDialog(h, m, object : TimePickerHelper.Callback {
            override fun onTimeSelected(hourOfDay: Int, minute: Int) {
                var hourOfDay = hourOfDay
                //AM_PM decider logic
                when {
                    hourOfDay == 0 -> {
                        hourOfDay += 12
                        am_pm = "AM"
                    }
                    hourOfDay == 12 -> am_pm = "PM"
                    hourOfDay > 12 -> {
                        hourOfDay -= 12
                        am_pm = "PM"
                    }
                    else -> am_pm = "AM"
                }
                val hourStr = if (hourOfDay < 10)
                    "0${hourOfDay}" else "${hourOfDay}"
                val minuteStr = if (minute < 10)
                    "0${minute}" else "${minute}"

                hourTime = hourStr
                minTime = minuteStr

                timeView.text = "${hourStr}:${minuteStr}$am_pm"

                openHour = "${hourStr}:${minuteStr}"

            }

        })
        return testVar
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
    ) = FragmentNewPersonnelBinding.inflate(inflater, container, false)

    override fun getFragmentRepo(): PersonnelRepo {
        val token = runBlocking { userprefs.authToken.first() }
        val api = remoteDataSource.buildApi(PersonnelApi::class.java, token)
        return PersonnelRepo(api, userprefs)
    }
//
//
    override fun onCountrySelected() {
        practitionerPhoneNumberCode = binding.newPractitionerCountryCodePicker.selectedCountryCode
    }

}