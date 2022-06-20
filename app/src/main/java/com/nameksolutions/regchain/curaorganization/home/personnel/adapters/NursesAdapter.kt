package com.nameksolutions.regchain.curaorganization.home.personnel.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nameksolutions.regchain.curaorganization.databinding.PersonnelDoctorsItemLayoutBinding
import com.nameksolutions.regchain.curaorganization.databinding.PersonnelNursesItemLayoutBinding
import com.nameksolutions.regchain.curaorganization.responses.NewPractitioner
import com.nameksolutions.regchain.curaorganization.responses.Practitoner

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

    private fun createOnClickListener(nurse: Practitoner?): View.OnClickListener {
        return View.OnClickListener {
            //navigate to page to show doctor details using navigation directions
        }
    }

    class NursesViewHolder(private val binding: PersonnelNursesItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(listener: View.OnClickListener, itemData: Practitoner){
            binding.apply {
                nursesItemClickListener = listener
                nursesItem = itemData
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