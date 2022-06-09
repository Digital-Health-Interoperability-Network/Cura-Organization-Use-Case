package com.nameksolutions.regchain.curaorganization.home.tokens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nameksolutions.regchain.curaorganization.R
import com.nameksolutions.regchain.curaorganization.base.BaseFragment
import com.nameksolutions.regchain.curaorganization.databinding.FragmentTokenBinding

class TokenFragment : BaseFragment<TokensViewModel, FragmentTokenBinding, TokensRepo>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun getViewModel() = TokensViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentTokenBinding.inflate(inflater, container, false)

    override fun getFragmentRepo() = TokensRepo(remoteDataSource.buildApi(TokensApi::class.java), userprefs)

}