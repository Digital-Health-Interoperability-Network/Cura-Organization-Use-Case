package com.nameksolutions.regchain.curaorganization.home.personnel.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nameksolutions.regchain.curaorganization.databinding.PractitionersItemLayoutBinding
import com.nameksolutions.regchain.curaorganization.home.personnel.ui.PersonnelFragmentDirections
import com.nameksolutions.regchain.curaorganization.responses.PractitionerResponse
import com.nameksolutions.regchain.curaorganization.utils.Common.TAG

class NursesAdapter: ListAdapter<PractitionerResponse, NursesAdapter.NursesViewHolder>(NursesAdapter.DiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NursesAdapter.NursesViewHolder {
        return NursesViewHolder(
            PractitionersItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(
        holder: NursesAdapter.NursesViewHolder,
        position: Int
    ) {
        val nurse = getItem(position)
        holder.apply {
            bind(createOnClickListener(nurse), nurse)
            itemView.tag = nurse
        }

    }

    private fun createOnClickListener(nurse: PractitionerResponse): View.OnClickListener {
        return View.OnClickListener {
            //navigate to page to show doctor details using navigation directions
            Log.d(TAG, "createOnClickListener: nurse clicked!")
            val direction = PersonnelFragmentDirections.actionPersonnelFragmentToPractitionerItem2(nurse.id)
            it.findNavController().navigate(direction)
        }
    }

    class NursesViewHolder(private val binding: PractitionersItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(listener: View.OnClickListener, itemData: PractitionerResponse){
            binding.apply {
                practitionersItemClickListener = listener
                practitionersItem = itemData
                executePendingBindings()
            }
        }
    }
    private class DiffCallback: DiffUtil.ItemCallback<PractitionerResponse>(){
        override fun areItemsTheSame(oldItem: PractitionerResponse, newItem: PractitionerResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PractitionerResponse,
            newItem: PractitionerResponse
        ): Boolean {
            return oldItem == newItem
        }

    }

}