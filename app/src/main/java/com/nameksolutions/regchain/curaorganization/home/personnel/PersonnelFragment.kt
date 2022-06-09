package com.nameksolutions.regchain.curaorganization.home.personnel

import android.app.Dialog
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
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
import com.nameksolutions.regchain.curaorganization.home.personnel.adapters.*
import com.nameksolutions.regchain.curaorganization.network.Resource
import com.nameksolutions.regchain.curaorganization.responses.DataXX
import com.nameksolutions.regchain.curaorganization.responses.NewPractitioner
import com.nameksolutions.regchain.curaorganization.utils.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class PersonnelFragment :
    BaseFragment<PersonnelViewModel, FragmentPersonnelBinding, PersonnelRepo>() {

    val TAG = "EQUA"
    private var progressDialog: Dialog? = null
    private var practitionerRoles = arrayListOf(
        "Doctor",
        "Nurse",
        "Pharmacist",
        "Lab Scientist",
        "-Doctor,-Nurse,-Pharmacist,-Lab Scientist"
    )

    private val doctorsAdapter = DoctorsAdapter()
    private val nursesAdapter = NursesAdapter()
    private val pharmacistsAdapter = PharmacistsAdapter()
    private val labScientistsAdapter = LabScientistsAdapter()
    private val otherPractitionersAdapter = OtherPractitionersAdapter()
    private val personnelStatsAdapter = PersonnelStatsAdapter()
// var practitionerRoles = arrayListOf("Doctor", "Nurse",
//        "Pharmacist",
//        "Pharmacy Technician",
//        "Dentist",
//        "Dental Technician",
//        "Midwife",
//        "Lab Technician",
//        "Lab Scientist",
//        "health Record And HIM Officer",
//        "Community Health Worker",
//        "Community Health Extension Worker",
//        "Junior Community Health Extension Worker",
//        "Environmental Health Officers",
//        "Health Attendant Or Assistant")

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        getPractitionerRoleList()
        fetchAllPractitionersStats()
//        fetchPractitioners("Doctor")
        for (role in Common.practitionerRolesList) {
            fetchPractitioners(role)
        }
        
        fetchAllPractitoner()

        binding.fabAddPractitioner.setOnClickListener {
            findNavController().navigate(R.id.action_personnelFragment_to_newPersonnelFragment)
        }


        with(binding) {
            doctorsButton.setOnClickListener {
                // If the CardView is already expanded, set its visibility
                //  to gone and change the expand less icon to expand more.
                if (doctorsHideAbleView.visibility == View.VISIBLE) {
                    TransitionManager.beginDelayedTransition(
                        baseCardViewDoctors,
                        AutoTransition()
                    )

                    doctorsHideAbleView.visibility = View.GONE
                    doctorsButton.setImageResource(R.drawable.angle_down)
                } else {
                    // If the CardView is not expanded, set its visibility
                    // to visible and change the expand more icon to expand less.
                    TransitionManager.beginDelayedTransition(
                        baseCardViewDoctors,
                        AutoTransition()
                    )
                    doctorsHideAbleView.visibility = View.VISIBLE
                    doctorsButton.setImageResource(R.drawable.angle_up)

                    if (nursesHideAbleView.visibility == View.VISIBLE) {
                        TransitionManager.beginDelayedTransition(
                            baseCardViewNurses,
                            AutoTransition()
                        )
                        nursesHideAbleView.visibility = View.GONE
                        nursesButton.setImageResource(R.drawable.angle_down)
                    }
                    if (pharmacistsHideAbleView.visibility == View.VISIBLE) {
                        TransitionManager.beginDelayedTransition(
                            baseCardViewPharmacists,
                            AutoTransition()
                        )
                        pharmacistsHideAbleView.visibility = View.GONE
                        pharmacistsButton.setImageResource(R.drawable.angle_down)
                    }
                    if (labTechsHideAbleView.visibility == View.VISIBLE) {
                        TransitionManager.beginDelayedTransition(
                            baseCardViewLabTechs,
                            AutoTransition()
                        )
                        labTechsHideAbleView.visibility = View.GONE
                        labTechButton.setImageResource(R.drawable.angle_down)
                    }
                    if (otherPractitionerHideAbleView.visibility == View.VISIBLE) {
                        TransitionManager.beginDelayedTransition(
                            baseCardViewOtherPractitioner,
                            AutoTransition()
                        )
                        otherPractitionerHideAbleView.visibility = View.GONE
                        otherPractitionerButton.setImageResource(R.drawable.angle_down)
                    }
                }

            }
            nursesButton.setOnClickListener {
                // If the CardView is already expanded, set its visibility
                //  to gone and change the expand less icon to expand more.
                if (nursesHideAbleView.visibility == View.VISIBLE) {
                    TransitionManager.beginDelayedTransition(
                        baseCardViewNurses,
                        AutoTransition()
                    )

                    nursesHideAbleView.visibility = View.GONE
                    nursesButton.setImageResource(R.drawable.angle_down)
                } else {
                    // If the CardView is not expanded, set its visibility
                    // to visible and change the expand more icon to expand less.
                    TransitionManager.beginDelayedTransition(
                        baseCardViewNurses,
                        AutoTransition()
                    )
                    nursesHideAbleView.visibility = View.VISIBLE
                    nursesButton.setImageResource(R.drawable.angle_up)

                    if (doctorsHideAbleView.visibility == View.VISIBLE) {
                        TransitionManager.beginDelayedTransition(
                            baseCardViewDoctors,
                            AutoTransition()
                        )
                        doctorsHideAbleView.visibility = View.GONE
                        doctorsButton.setImageResource(R.drawable.angle_down)
                    }
                    if (pharmacistsHideAbleView.visibility == View.VISIBLE) {
                        TransitionManager.beginDelayedTransition(
                            baseCardViewPharmacists,
                            AutoTransition()
                        )
                        pharmacistsHideAbleView.visibility = View.GONE
                        pharmacistsButton.setImageResource(R.drawable.angle_down)
                    }
                    if (labTechsHideAbleView.visibility == View.VISIBLE) {
                        TransitionManager.beginDelayedTransition(
                            baseCardViewLabTechs,
                            AutoTransition()
                        )
                        labTechsHideAbleView.visibility = View.GONE
                        labTechButton.setImageResource(R.drawable.angle_down)
                    }
                    if (otherPractitionerHideAbleView.visibility == View.VISIBLE) {
                        TransitionManager.beginDelayedTransition(
                            baseCardViewOtherPractitioner,
                            AutoTransition()
                        )
                        otherPractitionerHideAbleView.visibility = View.GONE
                        otherPractitionerButton.setImageResource(R.drawable.angle_down)
                    }
                }

            }
            pharmacistsButton.setOnClickListener {
                // If the CardView is already expanded, set its visibility
                //  to gone and change the expand less icon to expand more.
                if (pharmacistsHideAbleView.visibility == View.VISIBLE) {
                    TransitionManager.beginDelayedTransition(
                        baseCardViewPharmacists,
                        AutoTransition()
                    )

                    pharmacistsHideAbleView.visibility = View.GONE
                    pharmacistsButton.setImageResource(R.drawable.angle_down)
                } else {
                    // If the CardView is not expanded, set its visibility
                    // to visible and change the expand more icon to expand less.
                    TransitionManager.beginDelayedTransition(
                        baseCardViewPharmacists,
                        AutoTransition()
                    )
                    pharmacistsHideAbleView.visibility = View.VISIBLE
                    pharmacistsButton.setImageResource(R.drawable.angle_up)

                    if (nursesHideAbleView.visibility == View.VISIBLE) {
                        TransitionManager.beginDelayedTransition(
                            baseCardViewNurses,
                            AutoTransition()
                        )
                        nursesHideAbleView.visibility = View.GONE
                        nursesButton.setImageResource(R.drawable.angle_down)
                    }
                    if (doctorsHideAbleView.visibility == View.VISIBLE) {
                        TransitionManager.beginDelayedTransition(
                            baseCardViewDoctors,
                            AutoTransition()
                        )
                        doctorsHideAbleView.visibility = View.GONE
                        doctorsButton.setImageResource(R.drawable.angle_down)
                    }
                    if (labTechsHideAbleView.visibility == View.VISIBLE) {
                        TransitionManager.beginDelayedTransition(
                            baseCardViewLabTechs,
                            AutoTransition()
                        )
                        labTechsHideAbleView.visibility = View.GONE
                        labTechButton.setImageResource(R.drawable.angle_down)
                    }
                    if (otherPractitionerHideAbleView.visibility == View.VISIBLE) {
                        TransitionManager.beginDelayedTransition(
                            baseCardViewOtherPractitioner,
                            AutoTransition()
                        )
                        otherPractitionerHideAbleView.visibility = View.GONE
                        otherPractitionerButton.setImageResource(R.drawable.angle_down)
                    }
                }

            }
            labTechButton.setOnClickListener {
                // If the CardView is already expanded, set its visibility
                //  to gone and change the expand less icon to expand more.
                if (labTechsHideAbleView.visibility == View.VISIBLE) {
                    TransitionManager.beginDelayedTransition(
                        baseCardViewLabTechs,
                        AutoTransition()
                    )

                    labTechsHideAbleView.visibility = View.GONE
                    labTechButton.setImageResource(R.drawable.angle_down)
                } else {
                    // If the CardView is not expanded, set its visibility
                    // to visible and change the expand more icon to expand less.
                    TransitionManager.beginDelayedTransition(
                        baseCardViewLabTechs,
                        AutoTransition()
                    )
                    labTechsHideAbleView.visibility = View.VISIBLE
                    labTechButton.setImageResource(R.drawable.angle_up)

                    if (nursesHideAbleView.visibility == View.VISIBLE) {
                        TransitionManager.beginDelayedTransition(
                            baseCardViewNurses,
                            AutoTransition()
                        )
                        nursesHideAbleView.visibility = View.GONE
                        nursesButton.setImageResource(R.drawable.angle_down)
                    }
                    if (pharmacistsHideAbleView.visibility == View.VISIBLE) {
                        TransitionManager.beginDelayedTransition(
                            baseCardViewPharmacists,
                            AutoTransition()
                        )
                        pharmacistsHideAbleView.visibility = View.GONE
                        pharmacistsButton.setImageResource(R.drawable.angle_down)
                    }
                    if (doctorsHideAbleView.visibility == View.VISIBLE) {
                        TransitionManager.beginDelayedTransition(
                            baseCardViewDoctors,
                            AutoTransition()
                        )
                        doctorsHideAbleView.visibility = View.GONE
                        doctorsButton.setImageResource(R.drawable.angle_down)
                    }
                    if (otherPractitionerHideAbleView.visibility == View.VISIBLE) {
                        TransitionManager.beginDelayedTransition(
                            baseCardViewOtherPractitioner,
                            AutoTransition()
                        )
                        otherPractitionerHideAbleView.visibility = View.GONE
                        otherPractitionerButton.setImageResource(R.drawable.angle_down)
                    }
                }

            }
            otherPractitionerButton.setOnClickListener {
                // If the CardView is already expanded, set its visibility
                //  to gone and change the expand less icon to expand more.
                if (otherPractitionerHideAbleView.visibility == View.VISIBLE) {
                    TransitionManager.beginDelayedTransition(
                        baseCardViewOtherPractitioner,
                        AutoTransition()
                    )

                    otherPractitionerHideAbleView.visibility = View.GONE
                    otherPractitionerButton.setImageResource(R.drawable.angle_down)
                } else {
                    // If the CardView is not expanded, set its visibility
                    // to visible and change the expand more icon to expand less.
                    TransitionManager.beginDelayedTransition(
                        baseCardViewOtherPractitioner,
                        AutoTransition()
                    )
                    otherPractitionerHideAbleView.visibility = View.VISIBLE
                    otherPractitionerButton.setImageResource(R.drawable.angle_up)

                    if (nursesHideAbleView.visibility == View.VISIBLE) {
                        TransitionManager.beginDelayedTransition(
                            baseCardViewNurses,
                            AutoTransition()
                        )
                        nursesHideAbleView.visibility = View.GONE
                        nursesButton.setImageResource(R.drawable.angle_down)
                    }
                    if (pharmacistsHideAbleView.visibility == View.VISIBLE) {
                        TransitionManager.beginDelayedTransition(
                            baseCardViewPharmacists,
                            AutoTransition()
                        )
                        pharmacistsHideAbleView.visibility = View.GONE
                        pharmacistsButton.setImageResource(R.drawable.angle_down)
                    }
                    if (labTechsHideAbleView.visibility == View.VISIBLE) {
                        TransitionManager.beginDelayedTransition(
                            baseCardViewLabTechs,
                            AutoTransition()
                        )
                        labTechsHideAbleView.visibility = View.GONE
                        labTechButton.setImageResource(R.drawable.angle_down)
                    }
                    if (doctorsHideAbleView.visibility == View.VISIBLE) {
                        TransitionManager.beginDelayedTransition(
                            baseCardViewDoctors,
                            AutoTransition()
                        )
                        doctorsHideAbleView.visibility = View.GONE
                        doctorsButton.setImageResource(R.drawable.angle_down)
                    }
                }

            }
        }

        with(binding) {
            val personnelStatsLayoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            val doctorsLayoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            val nursesLayoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            val pharmacistsLayoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            val labTechLayoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            val otherPractitionerLayoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            rvPersonnelStats.adapter = personnelStatsAdapter
            rvDoctors.adapter = doctorsAdapter
            rvNurses.adapter = nursesAdapter
            rvPharmacists.adapter = pharmacistsAdapter
            rvLabTechs.adapter = labScientistsAdapter
            rvOtherPractitioner.adapter = otherPractitionersAdapter

            rvPersonnelStats.layoutManager = personnelStatsLayoutManager
            rvDoctors.layoutManager = doctorsLayoutManager
            rvNurses.layoutManager = nursesLayoutManager
            rvPharmacists.layoutManager = pharmacistsLayoutManager
            rvLabTechs.layoutManager = labTechLayoutManager
            rvOtherPractitioner.layoutManager = otherPractitionerLayoutManager

            rvPersonnelStats.addItemDecoration(
                DividerItemDecoration(
                    requireContext(), personnelStatsLayoutManager.orientation
                )
            )

            rvDoctors.addItemDecoration(
                DividerItemDecoration(
                    requireContext(), doctorsLayoutManager.orientation
                )
            )

            rvNurses.addItemDecoration(
                DividerItemDecoration(
                    requireContext(), nursesLayoutManager.orientation
                )
            )

            rvPharmacists.addItemDecoration(
                DividerItemDecoration(
                    requireContext(), pharmacistsLayoutManager.orientation
                )
            )

            rvLabTechs.addItemDecoration(
                DividerItemDecoration(
                    requireContext(), labTechLayoutManager.orientation
                )
            )

            rvOtherPractitioner.addItemDecoration(
                DividerItemDecoration(
                    requireContext(), otherPractitionerLayoutManager.orientation
                )
            )

        }

    }

    private fun fetchAllPractitoner() {
        viewModel.getAllPractitioners()
        viewModel.allPractitionerDetails.observe(viewLifecycleOwner, Observer { 
            when(it){
                is Resource.Success -> {
                    hideProgress()
                    Log.d(TAG, "fetchAllPractitoner: $it")
                }
                is Resource.Failure -> {
                    hideProgress()
                    Log.d(TAG, "fetchAllPractitoner: $it")
                }
                is Resource.Loading -> {
                    showProgress()
                }
            }
        })
    }

    private fun getPractitionerRoleList(){
        viewModel.getPractitionerRolesList()
        viewModel.practitionerRoleListResponse.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success -> {
                    hideProgress()
                    requireView().snackbar("Roles Fetched")
                    val fetchedPractitionerRoles = it.value.practitionerRolesList.listOfPractitionerRoles
                    Log.d(Common.TAG, "getPractitionerRoleList: ${it.value.practitionerRolesList.listOfPractitionerRoles}")
                    Common.practitionerRolesList = fetchedPractitionerRoles
                }
                is Resource.Failure -> {
                    hideProgress()
                    handleApiError(it){getPractitionerRoleList()}
                }
                is Resource.Loading -> {
                    showProgress()
                }
            }
        })
    }


    private fun fetchAllPractitionersStats() {
        viewModel.getAllPersonnelStats()
        viewModel.practitionerStats.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "fetchAllPractitionersStatsPre: ${it.toString()}")
            when(it){
                is Resource.Success -> {
                    hideProgress()
                    //show it in the recycler view
                    val testStat = it.value.data//.data._personnel
                    Log.d(TAG, "fetchAllPractitionersStatsTest: $testStat")
//                    Log.d(TAG, "fetchAllPractitionersStatsData: ${it.value .data}")
//                    Log.d(TAG, "fetchAllPractitionersStatsPersonnel: ${it.value.data._personnel}")

                    subscribeAllPractitionerStatsUI(testStat)
//                    val newalist = it.value.data._personnel

                }
                is Resource.Failure -> {
                    hideProgress()
                    Log.d(TAG, "fetchAllPractitionersStats: $it")

                    handleApiError(it) {fetchAllPractitionersStats()}
                }
                is Resource.Loading -> {
                    showProgress()
                }
            }
        })
    }


    private fun fetchPractitioners(code: String) {
        viewModel.getPractitionersByRole(code)
        viewModel.allPractitionerByRoleDetails.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    hideProgress()
                    if (it.value.isNotEmpty()) {
                        //populate the ui
                        when (code) {
                            "Doctor" -> {
//                                doctorsAdapter.submitList(it.value)
                                subscribeAllDoctorsUI(it.value)
                                Log.d(TAG, "fetchPractitioners: ${it.value}")
                            }
                            "Nurse" -> {
                                subscribeAllNursesUI(it.value)
                            }
                            "Pharmacist" -> {
                                subscribeAllPharmacistsUI(it.value)
                            }
                            "Lab Technician" -> {
                                subscribeAllLabTechsUI(it.value)
                            }
                            else -> {
                                subscribeAllOtherUI(it.value)
                            }
                        }
                    } else {
                        requireView().snackbar("No Practitioners In Database")
                    }
                }
                is Resource.Failure -> {
                    hideProgress()
                    handleApiError(it) { viewModel.getPractitionersByRole(code) }
                }
                is Resource.Loading -> {
                    showProgress()
                }
            }
        })
    }

    private fun subscribeAllPractitionerStatsUI(value: DataXX) {
        Log.d(TAG, "subscribeAllPractitionerStatsUI: $value.")
        personnelStatsAdapter.submitList(value._personnel)
    }

    private fun subscribeAllOtherUI(value: List<NewPractitioner>) {
        if (value.isNotEmpty()) {
            binding.rvOtherPractitioner.visible(true)
            otherPractitionersAdapter.submitList(value)
        } else {
            binding.rvOtherPractitioner.visible(false)
            binding.textOtherPractitionerNoData.visible(false)
        }

    }

    private fun subscribeAllLabTechsUI(value: List<NewPractitioner>) {
        if (value.isNotEmpty()) {
            binding.rvLabTechs.visible(true)
            labScientistsAdapter.submitList(value)
        } else {
            binding.rvLabTechs.visible(false)
            binding.textLabScientistNoData.visible(false)
        }

    }

    private fun subscribeAllPharmacistsUI(value: List<NewPractitioner>) {
        if (value.isNotEmpty()) {
            binding.rvPharmacists.visible(true)
            pharmacistsAdapter.submitList(value)
        } else {
            binding.rvPharmacists.visible(false)
            binding.textPharmacistNoData.visible(false)
        }


    }

    private fun subscribeAllNursesUI(value: List<NewPractitioner>) {
        if (value.isNotEmpty()) {
            binding.rvNurses.visible(true)
            nursesAdapter.submitList(value)
        } else {
            binding.rvNurses.visible(false)
            binding.textNursesNoData.visible(false)
        }


    }

    private fun subscribeAllDoctorsUI(value: List<NewPractitioner>) {
        if (value.isNotEmpty()) {
            binding.rvDoctors.visible(true)
            doctorsAdapter.submitList(value)
        } else {
            binding.rvDoctors.visible(false)
            binding.textDoctorNoData.visible(false)
        }

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
    ) = FragmentPersonnelBinding.inflate(inflater, container, false)

    override fun getFragmentRepo(): PersonnelRepo {
        val token = runBlocking { userprefs.authToken.first() }
        val api = remoteDataSource.buildApi(PersonnelApi::class.java, token)
        return PersonnelRepo(api, userprefs)
    }
}