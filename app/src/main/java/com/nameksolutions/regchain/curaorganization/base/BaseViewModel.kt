package com.nameksolutions.regchain.curaorganization.base

import androidx.lifecycle.ViewModel
import com.nameksolutions.regchain.curaorganization.auth.AuthApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseViewModel(private val repository: BaseRepo) : ViewModel() {

    suspend fun logout(api: AuthApi) = withContext(Dispatchers.IO) { repository.logout(api) }

}