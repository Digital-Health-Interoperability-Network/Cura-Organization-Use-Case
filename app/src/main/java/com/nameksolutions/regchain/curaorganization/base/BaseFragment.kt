package com.nameksolutions.regchain.curaorganization.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.nameksolutions.regchain.curaorganization.network.RemoteDataSource


//this base fragment is created to contain all common parameters and methods required in all fragments in this project
//its generic types are all the certain methods that will be used in all fragments alike

abstract class BaseFragment<VM: BaseViewModel, B: ViewBinding, R: BaseRepo> : Fragment() {

    protected lateinit var binding: B
    protected lateinit var viewModel: VM
    protected val remoteDataSource = RemoteDataSource()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val factory = ViewModelFactory(getFragmentRepo()) //the parameter is selected as the function below in order to get the actual repository
        viewModel = ViewModelProvider(this,factory).get(getViewModel())



        binding = getFragmentBinding(inflater, container)

        return binding.root
    }

    //this function will be called when we want to utilize viewBinding in any fragment
    abstract fun getViewModel(): Class<VM>

    //this function will be called whenever we want to inflate a fragment layout in any fragment
    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): B

    //this function will be used to interact with the Base Repo in all fragments
    abstract fun getFragmentRepo(): R


}