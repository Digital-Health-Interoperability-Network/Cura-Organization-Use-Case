package com.nameksolutions.regchain.curaorganization.home.profile

import com.nameksolutions.regchain.curaorganization.base.BaseRepo
import com.nameksolutions.regchain.curaorganization.utils.UserPreferences

class ProfileRepo(
    private val api: ProfileApi,
    private val prefs: UserPreferences
): BaseRepo() {
}