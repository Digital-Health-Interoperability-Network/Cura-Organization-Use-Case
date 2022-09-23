package com.nameksolutions.regchain.curaorganization.home.personnel.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nameksolutions.regchain.curaorganization.databinding.PersonnelNursesItemLayoutBinding
import com.nameksolutions.regchain.curaorganization.home.personnel.ui.PersonnelFragmentDirections

class NursesAdapter: ListAdapter<Practitoner, NursesAdapter.NursesViewHolder>(NursesAdapter.DiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NursesAdapter.NursesViewHolder {
        return NursesViewHolder(
            PersonnelNursesItemLayoutBinding.inflate(
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

    private fun createOnClickListener(nurse: Practitoner): View.OnClickListener {
        return View.OnClickListener {
            //navigate to page to show doctor details using navigation directions
            val direction = PersonnelFragmentDirections.actionPersonnelFragmentToPractitionerItem(nurse)
            it.findNavController().navigate(direction)
        }
    }

    class NursesViewHolder(private val binding: PersonnelNursesItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(listener: View.OnClickListener, itemData: Practitoner){
            binding.apply {
                nursesItemClickListener = listener
                nursesItem = itemData
//                val nurseNames = mutableListOf<String>(nursesItem.name!!.given!![0].substring(0, 1), nursesItem.name!!.family!!.substring(0, 1))
//                nurseIconText.text = "${nurseNames[0]}${nurseNames[1]}"
                executePendingBindings()
            }
        }
    }
    private class DiffCallback: DiffUtil.ItemCallback<Practitoner>(){
        override fun areItemsTheSame(oldItem: Practitoner, newItem: Practitoner): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Practitoner,
            newItem: Practitoner
        ): Boolean {
            return oldItem == newItem
        }

    }

}