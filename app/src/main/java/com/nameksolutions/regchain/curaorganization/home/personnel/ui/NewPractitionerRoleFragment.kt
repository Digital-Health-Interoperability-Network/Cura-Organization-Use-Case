/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Namek Solutions Limited,
 * Abuja, FCT.
 * Nigeria
 */

package com.nameksolutions.regchain.curaorganization.home.personnel.ui

import android.app.Dialog
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.MultiAutoCompleteTextView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nameksolutions.regchain.curaorganization.R
import com.nameksolutions.regchain.curaorganization.base.BaseFragment
import com.nameksolutions.regchain.curaorganization.databinding.FragmentNewPractitionerRoleBinding
import com.nameksolutions.regchain.curaorganization.home.personnel.PersonnelApi
import com.nameksolutions.regchain.curaorganization.home.personnel.PersonnelRepo
import com.nameksolutions.regchain.curaorganization.home.personnel.PersonnelViewModel
import com.nameksolutions.regchain.curaorganization.network.Resource
import com.nameksolutions.regchain.curaorganization.requests.AvailableTimeRequest
import com.nameksolutions.regchain.curaorganization.requests.CreatePractitionerRequest
import com.nameksolutions.regchain.curaorganization.requests.NameRequest
import com.nameksolutions.regchain.curaorganization.requests.PractitionerRoleRequest
import com.nameksolutions.regchain.curaorganization.utils.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.*


class NewPractitionerRoleFragment :
    BaseFragment<PersonnelViewModel, FragmentNewPractitionerRoleBinding, PersonnelRepo>() {

    val args by navArgs<NewPractitionerRoleFragmentArgs>()

    private lateinit var newPractitionerRole: String
    private lateinit var newPractitionerRoleSpecialty: String

    private lateinit var fetchedPractitionerRoles: List<String>

    lateinit var practitionerId: String

    private val practitionerRole = PractitionerRoleRequest()
    private val availTime = AvailableTimeRequest()
    private var availableTime: MutableList<AvailableTimeRequest> = mutableListOf()

    lateinit var timePicker: TimePickerHelper
    lateinit var openHour: String
    private var availabilityMon: AvailableTimeRequest = AvailableTimeRequest()
    private var availabilityTue: AvailableTimeRequest = AvailableTimeRequest()
    private var availabilityWed: AvailableTimeRequest = AvailableTimeRequest()
    private var availabilityThurs: AvailableTimeRequest = AvailableTimeRequest()
    private var availabilityFri: AvailableTimeRequest = AvailableTimeRequest()
    private var availabilitySat: AvailableTimeRequest = AvailableTimeRequest()
    private var availabilitySun: AvailableTimeRequest = AvailableTimeRequest()

    private var progressDialog: Dialog? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getPractitionerRoleList()


    }

    private fun beginEntry() {
        with(binding) {
            val practitionerName = "${args.practitionerFirstName} ${args.practitionerSurName}"
            practitionerId = args.practitionerId

            timePicker = TimePickerHelper(requireContext(), true, isSpinnerType = true)


            textviewNewPractitionerRolePractitionerName.text = practitionerName

            val practitionerRolesSpecialtyArray = resources.getStringArray(R.array.id_communication)
            val practitionerRolesSpecialtyArrayAdapter =
                ArrayAdapter(
                    requireContext(),
                    R.layout.drop_down_item,
                    practitionerRolesSpecialtyArray
                )
            binding.regPractitionerRoleSpecialty.setAdapter(practitionerRolesSpecialtyArrayAdapter)

            val practitionerRolesArray = fetchedPractitionerRoles
            val practitionerRolesArrayAdapter =
                ArrayAdapter(
                    requireContext(),
                    R.layout.drop_down_item,
                    practitionerRolesArray
                )

            binding.regPractitionerRole.setAdapter(practitionerRolesArrayAdapter)



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
                            Common.TAG, "onActivityCreated: ${
                                monOpenTimeView.text.toString()
                            }"
                        )
                        Log.d(
                            Common.TAG,
                            "onActivityCreated: ${showTimePickerDialog(monOpenTimeView)}"
                        )
                    }

                    Log.d(Common.TAG, "onActivityCreatedPost: ${regPractitionerMondayOpen.text}")


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

            btnPractitionerRoleRegister.setOnClickListener {


                if (checkBoxPractitionerMonday.isChecked) {
                    availabilityMon = availTime.copy(
                        availableStartTime = regPractitionerMondayOpen.text.toString().trim(),
                        availableEndTime = regPractitionerMondayClose.text.toString().trim(),
                        daysOfWeek = listOf("Mon")
                    )
                    availableTime.add(availabilityMon)
                }
                if (checkBoxPractitionerTuesday.isChecked) {
                    availabilityTue = availTime.copy(
                        availableStartTime = practitionerTuesdayOpen.text.toString().trim(),
                        availableEndTime = practitionerTuesdayClose.text.toString().trim(),
                        daysOfWeek = listOf("Tue")
                    )
                    availableTime.add(availabilityTue)
                }
                if (checkBoxPractitionerWednesday.isChecked) {
                    availabilityWed = availTime.copy(
                        availableStartTime = practitionerWednesdayOpen.text.toString().trim(),
                        availableEndTime = practitionerWednesdayClose.text.toString().trim(),
                        daysOfWeek = listOf("Wed")
                    )
                    availableTime.add(availabilityWed)
                }
                if (checkBoxPractitionerThursday.isChecked) {
                    availabilityThurs = availTime.copy(
                        availableStartTime = practitionerThursdayOpen.text.toString().trim(),
                        availableEndTime = practitionerThursdayClose.text.toString().trim(),
                        daysOfWeek = listOf("Thurs")
                    )
                    availableTime.add(availabilityThurs)
                }
                if (checkBoxPractitionerFriday.isChecked) {
                    availabilityFri = availTime.copy(
                        availableStartTime = practitionerFridayOpen.text.toString().trim(),
                        availableEndTime = practitionerFridayClose.text.toString().trim(),
                        daysOfWeek = listOf("Fri"),
                        allDay = true
                    )
                    availableTime.add(availabilityFri)
                }
                if (checkBoxPractitionerSaturday.isChecked) {
                    availabilitySat = availTime.copy(
                        availableStartTime = practitionerSaturdayOpen.text.toString().trim(),
                        availableEndTime = practitionerSaturdayClose.text.toString().trim(),
                        daysOfWeek = listOf("Sat")
                    )
                    availableTime.add(availabilitySat)
                }
                if (checkBoxPractitionerSunday.isChecked) {
                    availabilitySun = availTime.copy(
                        availableStartTime = practitionerSundayOpen.text.toString().trim(),
                        availableEndTime = practitionerSundayClose.text.toString().trim(),
                        daysOfWeek = listOf("Sun")
                    )
                    availableTime.add(availabilitySun)
                }


                newPractitionerRole = regPractitionerRole.text.toString().trim()
                newPractitionerRoleSpecialty = regPractitionerRoleSpecialty.text.toString().trim()

                performValidation()


            }

        }
    }

    private fun performValidation() {
        with(binding) {
            //if new practitioner role field is empty
            if (newPractitionerRole.isEmpty()) {
                textInputLayoutPractitionerRole.error = "Practitioner Role Required"
                regPractitionerRole.requestFocus()
                return
            } //if new practitioner role field is empty
            if (newPractitionerRoleSpecialty.isEmpty()) {
                textInputLayoutPractitionerRoleSpeciality.error = "Practitioner Required Required"
                regPractitionerRoleSpecialty.requestFocus()
                return
            }
            if (availableTime.isEmpty()) {
                requireView().snackbar("At least one day required")
            } else {

                val newPractitionerRoleSpecialtyArray =
                    newPractitionerRoleSpecialty.split("\\s*,\\s*")
                val newPractitionerRoleArray = newPractitionerRole.split("\\s*,\\s*")


                val practitionerRole = practitionerRole.copy(
                    availableTime = availableTime,
                    code = newPractitionerRoleArray,
                    specialty = newPractitionerRoleSpecialtyArray
                )


                createNewPractitionerRole(practitionerRole)

            }
        }
    }

    private fun createNewPractitionerRole(practitionerRole: PractitionerRoleRequest) {

        viewModel.createPractitionerRole(practitionerId, practitionerRole)
        viewModel.practitionerRoleCreationResponse.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgress()
                    requireView().snackbar("Practitioner Details Entered")
                    val navToRoleCreated =
                        NewPractitionerRoleFragmentDirections.actionNewPractitionerRoleFragmentToPersonnelFragment()
                    findNavController().navigate(navToRoleCreated)
                }
                is Resource.Failure -> {
                    hideProgress()
                    Log.d(Common.TAG, "createNewPractitioner: $response")
                    handleApiError(response) { createNewPractitionerRole(practitionerRole) }
                }
                is Resource.Loading -> {
                    showProgress()
                }
            }

        })
    }

    private fun getPractitionerRoleList() {
        viewModel.getPractitionerRolesList()

        viewModel.practitionerRoleListResponse.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgress()
                    requireView().snackbar("Roles Fetched")
                    fetchedPractitionerRoles =
                        response.value.listOfPractitionerRoles
                    Log.d(
                        Common.TAG,
                        "getPractitionerRoleList: ${response.value.listOfPractitionerRoles}"
                    )

                    beginEntry()

                }
                is Resource.Failure -> {
                    hideProgress()
                    handleApiError(response) { getPractitionerRoleList() }
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
    ) = FragmentNewPractitionerRoleBinding.inflate(inflater, container, false)

    override fun getFragmentRepo(): PersonnelRepo {
        val token = runBlocking { userprefs.authToken.first() }
        val api = remoteDataSource.buildApi(PersonnelApi::class.java, token)
        return PersonnelRepo(api, userprefs)
    }


}