package com.nameksolutions.regchain.curaorganization.home.tokens

import com.nameksolutions.regchain.curaorganization.base.BaseRepo
import com.nameksolutions.regchain.curaorganization.utils.UserPreferences

class TokensRepo(
    private val api: TokensApi,
    private val prefs: UserPreferences
): BaseRepo() {
}