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
            val direction = PersonnelFragmentDirections.actionPersonnelFragmentToPractitionerItem2(nurse)
            it.findNavController().navigate(direction)
        }
    }

    class NursesViewHolder(private val binding: PractitionersItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(listener: View.OnClickListener, itemData: PractitionerResponse){
            binding.apply {
//                nursesItemClickListener = listener
//                nursesItem = itemData
                practitionersItemClickListener = listener
                practitionersItem = itemData
//                val nurseNames = mutableListOf<String>(nursesItem.name!!.given!![0].substring(0, 1), nursesItem.name!!.family!!.substring(0, 1))
//                nurseIconText.text = "${nurseNames[0]}${nurseNames[1]}"
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