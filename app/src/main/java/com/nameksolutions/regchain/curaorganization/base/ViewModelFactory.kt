package com.nameksolutions.regchain.curaorganization.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nameksolutions.regchain.curaorganization.auth.AuthRepo
import com.nameksolutions.regchain.curaorganization.auth.AuthViewModel
import com.nameksolutions.regchain.curaorganization.home.HomeRepo
import com.nameksolutions.regchain.curaorganization.home.HomeViewModel
import com.nameksolutions.regchain.curaorganization.home.personnel.PersonnelRepo
import com.nameksolutions.regchain.curaorganization.home.personnel.PersonnelViewModel

class ViewModelFactory(
    private val repo: BaseRepo
): ViewModelProvider.NewInstanceFactory() {

    //in its create method...
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            //... it checks the viewModel which is using it and casts the base repo to that viewModel's repo
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> AuthViewModel(repo as AuthRepo) as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(repo as HomeRepo) as T
            modelClass.isAssignableFrom(PersonnelViewModel::class.java) -> PersonnelViewModel(repo as PersonnelRepo) as T
            //modelClass.isAssignableFrom(PHRViewModel::class.java) -> PHRViewModel(repo as RecurringRecordsRepo) as T
            //modelClass.isAssignableFrom(HistoryViewModel::class.java) -> HistoryViewModel(repo as FixedRecordsRepo) as T
            else -> throw IllegalArgumentException("ViewModelClass Not Found")
        }
    }

}