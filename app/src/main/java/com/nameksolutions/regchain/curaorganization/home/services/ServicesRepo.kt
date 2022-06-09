package com.nameksolutions.regchain.curaorganization.home.services

import com.nameksolutions.regchain.curaorganization.base.BaseRepo
import com.nameksolutions.regchain.curaorganization.home.personnel.PersonnelApi
import com.nameksolutions.regchain.curaorganization.utils.UserPreferences

class ServicesRepo(
    private val api: ServicesApi,
    private val prefs: UserPreferences
): BaseRepo() {
}