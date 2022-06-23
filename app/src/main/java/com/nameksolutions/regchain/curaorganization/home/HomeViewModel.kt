package com.nameksolutions.regchain.curaorganization.home

import androidx.lifecycle.viewModelScope
import com.nameksolutions.regchain.curaorganization.auth.AuthRepo
import com.nameksolutions.regchain.curaorganization.base.BaseViewModel
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repo: HomeRepo
) : BaseViewModel(repo) {

    fun saveIsFirstTime(isFirstTime: Boolean) = viewModelScope.launch {
        repo.saveIsFirstTime(isFirstTime)
    }
}