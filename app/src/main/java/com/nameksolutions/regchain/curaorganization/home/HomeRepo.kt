package com.nameksolutions.regchain.curaorganization.home

import com.nameksolutions.regchain.curaorganization.auth.AuthApi
import com.nameksolutions.regchain.curaorganization.base.BaseRepo
import com.nameksolutions.regchain.curaorganization.utils.UserPreferences

class HomeRepo(
    private val api: HomeApi,
    private val prefs: UserPreferences
) : BaseRepo() {

    suspend fun saveIsFirstTime(isFirstTime: Boolean){
        prefs.saveIsFirstTime(isFirstTime)
    }
}