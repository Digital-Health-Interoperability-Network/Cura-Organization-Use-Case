package com.nameksolutions.regchain.curaorganization.home.personnel.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nameksolutions.regchain.curaorganization.databinding.PersonnelStatsLayoutItemBinding
import com.nameksolutions.regchain.curaorganization.responses.PersonnelResponse

class PersonnelStatsAdapter: ListAdapter<PersonnelResponse, PersonnelStatsAdapter.PersonnelStatsViewHolder>(DiffCallback()){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PersonnelStatsAdapter.PersonnelStatsViewHolder {
        return PersonnelStatsViewHolder(
            PersonnelStatsLayoutItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(
        holder: PersonnelStatsAdapter.PersonnelStatsViewHolder,
        position: Int
    ) {
        val personnelStat = getItem(position)
        holder.apply {
            bind(personnelStat)
            itemView.tag = personnelStat
        }

    }

    class PersonnelStatsViewHolder(private val binding: PersonnelStatsLayoutItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(itemData: PersonnelResponse){
            binding.apply {
                personnelStatsItem = itemData
                executePendingBindings()
            }
        }
    }
    private class DiffCallback: DiffUtil.ItemCallback<PersonnelResponse>(){
        override fun areItemsTheSame(oldItem: PersonnelResponse, newItem: PersonnelResponse): Boolean {
            return oldItem.role == newItem.role
        }

        override fun areContentsTheSame(
            oldItem: PersonnelResponse,
            newItem: PersonnelResponse
        ): Boolean {
            return oldItem == newItem
        }

    }

}