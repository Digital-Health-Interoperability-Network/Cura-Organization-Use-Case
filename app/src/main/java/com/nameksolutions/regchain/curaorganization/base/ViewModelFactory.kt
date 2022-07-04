package com.nameksolutions.regchain.curaorganization.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nameksolutions.regchain.curaorganization.auth.AuthRepo
import com.nameksolutions.regchain.curaorganization.auth.AuthViewModel
import com.nameksolutions.regchain.curaorganization.home.HomeRepo
import com.nameksolutions.regchain.curaorganization.home.HomeViewModel
import com.nameksolutions.regchain.curaorganization.home.personnel.PersonnelRepo
import com.nameksolutions.regchain.curaorganization.home.personnel.PersonnelViewModel
import com.nameksolutions.regchain.curaorganization.home.profile.ProfileRepo
import com.nameksolutions.regchain.curaorganization.home.profile.ProfileViewModel
import com.nameksolutions.regchain.curaorganization.home.services.ServicesRepo
import com.nameksolutions.regchain.curaorganization.home.services.ServicesViewModel
import com.nameksolutions.regchain.curaorganization.home.tokens.TokensRepo
import com.nameksolutions.regchain.curaorganization.home.tokens.TokensViewModel

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
            modelClass.isAssignableFrom(ServicesViewModel::class.java) -> ServicesViewModel(repo as ServicesRepo) as T
            modelClass.isAssignableFrom(TokensViewModel::class.java) -> TokensViewModel(repo as TokensRepo) as T
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> ProfileViewModel(repo as ProfileRepo) as T

            else -> throw IllegalArgumentException("ViewModelClass Not Found")
        }
    }

}