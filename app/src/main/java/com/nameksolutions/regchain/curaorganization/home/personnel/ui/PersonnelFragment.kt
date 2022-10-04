package com.nameksolutions.regchain.curaorganization.home.personnel.ui

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
import com.nameksolutions.regchain.curaorganization.home.personnel.PersonnelApi
import com.nameksolutions.regchain.curaorganization.home.personnel.PersonnelRepo
import com.nameksolutions.regchain.curaorganization.home.personnel.PersonnelViewModel
import com.nameksolutions.regchain.curaorganization.home.personnel.adapters.*
import com.nameksolutions.regchain.curaorganization.network.Resource
import com.nameksolutions.regchain.curaorganization.responses.PersonnelResponse
import com.nameksolutions.regchain.curaorganization.responses.PractitionerResponse
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
//
    private val practitionersAdapter = PractitionersAdapter()
    private val nursesAdapter = PractitionersAdapter()
    private val pharmacistsAdapter = PractitionersAdapter()
    private val labScientistsAdapter = PractitionersAdapter()
    private val otherPractitionersAdapter = PractitionersAdapter()
    private val personnelStatsAdapter = PersonnelStatsAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchAllPractitionersStats()
        fetchPractitioners("")

        with(binding) {

            fabAddPractitioner.setOnClickListener {
                findNavController().navigate(R.id.action_personnelFragment_to_newPersonnelFragment)
            }

            personnelBackBtn.setOnClickListener {
                findNavController().navigate(R.id.action_personnelFragment_to_homeFragment)
            }
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
            val practitionersLayoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            rvPersonnelStats.apply {
                adapter = personnelStatsAdapter
                layoutManager = personnelStatsLayoutManager
                addItemDecoration(
                    DividerItemDecoration(
                        requireContext(), personnelStatsLayoutManager.orientation
                    )
                )
            }
            rvDoctors.apply {
                adapter = practitionersAdapter
                rvDoctors.layoutManager = practitionersLayoutManager
                addItemDecoration(
                    DividerItemDecoration(
                        requireContext(), practitionersLayoutManager.orientation
                    )
                )
            }
            rvNurses.apply {
                adapter = practitionersAdapter
                rvNurses.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                addItemDecoration(
                    DividerItemDecoration(
                        requireContext(), practitionersLayoutManager.orientation
                    )
                )
            }
            rvPharmacists.apply {
                adapter = practitionersAdapter
                rvPharmacists.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                addItemDecoration(
                    DividerItemDecoration(
                        requireContext(),
                        practitionersLayoutManager.orientation
                    )
                )
            }
            rvLabTechs.apply {
                adapter = practitionersAdapter
                rvLabTechs.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    practitionersLayoutManager.orientation
                )
            )

            }
            rvOtherPractitioner.apply {
                adapter = practitionersAdapter
                rvOtherPractitioner.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

                addItemDecoration(
                DividerItemDecoration(
                    requireContext(), practitionersLayoutManager
                        .orientation
                )
            )
            }
        }
    }

//    private fun fetchAllPractitioner() {
//        viewModel.getAllPractitioners()
//        viewModel.allPractitionerDetails.observe(viewLifecycleOwner, Observer {
//            when(it){
//                is Resource.Success -> {
//                    hideProgress()
//                    //populate the ui
//                    val practitioners = it.value.data.practitoners
//                    Log.d(TAG, "fetchAllPractitioner: $practitioners")
//                    val doctors = mutableListOf<Practitoner>()
//                    val nurses = mutableListOf<Practitoner>()
//                    val pharmacists = mutableListOf<Practitoner>()
//                    val labScientists = mutableListOf<Practitoner>()
//                    val otherPractitioners = mutableListOf<Practitoner>()
//                    for (practitioner in practitioners){
//                        val practitionerRoles = practitioner.practitionerRole
//                        for (practitionerRole in practitionerRoles){
//                            when (practitionerRole.code) {
//                                "Doctor" -> {
//                                    if (practitionerRole.code == "Doctor")
//                                        doctors.add(practitioner)
//                                    subscribeAllDoctorsUI(doctors)
//                                    Log.d(TAG, "fetchAllPractitioner: Are Doctors")
//                                }
//                                "Nurse" -> {
//                                    if (practitionerRole.code == "Nurse")
//                                        nurses.add(practitioner)
//                                    subscribeAllNursesUI(nurses)
//                                    Log.d(TAG, "fetchAllPractitioner: Are Nurses")
//                                }
//                                "Pharmacist" -> {
//                                    if (practitionerRole.code == "Pharmacist")
//                                        pharmacists.add(practitioner)
//                                    subscribeAllPharmacistsUI(pharmacists)
//                                    Log.d(TAG, "fetchAllPractitioner: Are Pharmacists")
//                                }
//                                "Lab Scientist" -> {
//                                    if (practitionerRole.code == "Lab Scientist")
//                                        labScientists.add(practitioner)
//                                    subscribeAllLabTechsUI(labScientists)
//                                    Log.d(TAG, "fetchAllPractitioner: Are Lab Scientists")
//                                }
//                                else -> {
//                                    otherPractitioners.add(practitioner)
//                                    subscribeAllOtherUI(otherPractitioners)
//                                    Log.d(TAG, "fetchAllPractitioner: Are Other Practitioners")
//                                }
//                            }
//                        }
//                    }
//                    Log.d(TAG, "fetchAllPractitionerDoctors: $doctors")
//                    Log.d(TAG, "fetchAllPractitoner: $it")
//                }
//                is Resource.Failure -> {
//                    hideProgress()
//                    Log.d(TAG, "fetchAllPractitoner: $it")
//                }
//                is Resource.Loading -> {
//                    showProgress()
//                }
//            }
//        })
//    }

    private fun fetchAllPractitionersStats() {
        viewModel.getAllPersonnelStats()
        viewModel.practitionerStats.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "fetchAllPractitionersStatsPre: ${it.toString()}")
            when(it){
                is Resource.Success -> {
                    hideProgress()
                    //show it in the recycler view
                    val testStat = it.value._personnel
                    Log.d(TAG, "fetchAllPractitionersStatsTest: $testStat")
                    val statsList = mutableListOf<PersonnelResponse>()
                    for (stat in testStat){
                        statsList.add(stat)
                    }
                    Log.d(TAG, "fetchAllPractitionersStatsList: $statsList")
                    subscribeAllPractitionerStatsUI(statsList)

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


    private fun fetchPractitioners(code: String?) {
        viewModel.getPractitionersByRole(code)
        viewModel.allPractitionerByRoleDetails.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    hideProgress()
//                    if (it.value.data.practitoners.isNotEmpty()) {
//                        populate the ui
                    //fetch all practitioner list
                    val practitioners = it.value.practitioners
                    for (practitioner in practitioners){
                        //fetch the list of roles in for each practitioner
                        val practitionerRoles = practitioner.practitionerRoles
                        for (practitionerRole in practitionerRoles){
                            //check the role of each practitioner
                            if (practitionerRole.code.contains("Doctor")){
                                subscribeAllDoctorsUI(practitioners)
                            } else if (practitionerRole.code.contains("Nurse")){
                                subscribeAllNursesUI(practitioners)
                            } else if (practitionerRole.code.contains("Pharmacist")){
                                subscribeAllPharmacistsUI(practitioners)
                            } else if (practitionerRole.code.contains("Lab Scientist")){
                                subscribeAllLabTechsUI(practitioners)
                            } else{
                                    subscribeAllOtherUI(practitioners)
                                }
                        }
                    }
//
                }
                is Resource.Failure -> {
                    hideProgress()
                    Log.d(TAG, "fetchPractitioners: $it")
                    handleApiError(it) { viewModel.getPractitionersByRole(code) }
                }
                is Resource.Loading -> {
                    showProgress()
                }
            }
        })
    }

    private fun subscribeAllPractitionerStatsUI(value: List<PersonnelResponse>) {
        personnelStatsAdapter.submitList(value)
    }

    private fun subscribeAllOtherUI(value: List<PractitionerResponse>) {
        if (value.isNotEmpty()) {
            binding.rvOtherPractitioner.visible(true)
            practitionersAdapter.submitList(value)
        } else {
            binding.rvOtherPractitioner.visible(false)
            binding.textOtherPractitionerNoData.visible(false)
        }

    }

    private fun subscribeAllLabTechsUI(labTechs: List<PractitionerResponse>) {
        if (labTechs.isNotEmpty()) {
            binding.rvLabTechs.visible(true)
            practitionersAdapter.submitList(labTechs)
        } else {
            binding.rvLabTechs.visible(false)
            binding.textLabScientistNoData.visible(false)
        }

    }

    private fun subscribeAllPharmacistsUI(pharmacists: List<PractitionerResponse>) {
        if (pharmacists.isNotEmpty()) {
            binding.rvPharmacists.visible(true)
            practitionersAdapter.submitList(pharmacists)
        } else {
            binding.rvPharmacists.visible(false)
            binding.textPharmacistNoData.visible(false)
        }


    }

    private fun subscribeAllNursesUI(nurses: List<PractitionerResponse>) {
        if (nurses.isNotEmpty()) {
            binding.rvNurses.visible(true)
            practitionersAdapter.submitList(nurses)
        } else {
            binding.rvNurses.visible(false)
            binding.textNursesNoData.visible(false)
        }


    }

    private fun subscribeAllDoctorsUI(doctors: List<PractitionerResponse>) {
        if (doctors.isNotEmpty()) {
            binding.rvDoctors.visible(true)
        Log.d(TAG, "subscribeAllDoctorsUI: $doctors")
            practitionersAdapter.submitList(doctors)
        } else {
            binding.rvDoctors.visible(false)
            binding.textDoctorsNoData.visible(false)
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