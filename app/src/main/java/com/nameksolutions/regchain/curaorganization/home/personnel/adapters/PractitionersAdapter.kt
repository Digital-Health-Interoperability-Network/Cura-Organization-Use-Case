package com.nameksolutions.regchain.curaorganization.home.personnel.adapters

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

class PractitionersAdapter: ListAdapter<PractitionerResponse, PractitionersAdapter.PractitionersViewHolder>(DiffCallback()){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PractitionersAdapter.PractitionersViewHolder {
        return PractitionersViewHolder(
            PractitionersItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(
        holder: PractitionersAdapter.PractitionersViewHolder,
        position: Int
    ) {
        val practitioner = getItem(position)
        holder.apply {
            bind(createOnClickListener(practitioner), practitioner)
            itemView.tag = practitioner
        }

    }

    private fun createOnClickListener(practitioner: PractitionerResponse): View.OnClickListener {
        return View.OnClickListener {
            //navigate to page to show doctor details using navigation directions
            val direction = PersonnelFragmentDirections.actionPersonnelFragmentToPractitionerItem2(practitioner)
            it.findNavController().navigate(direction)
        }
    }

    class PractitionersViewHolder(private val binding: PractitionersItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
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