package com.nameksolutions.regchain.curaorganization.home.profile.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nameksolutions.regchain.curaorganization.databinding.ProfileTelecomsItemBinding
import com.nameksolutions.regchain.curaorganization.home.personnel.ui.PersonnelFragmentDirections
import com.nameksolutions.regchain.curaorganization.responses.PractitionerResponse
import com.nameksolutions.regchain.curaorganization.responses.profile.GetOrganizationResponse
import com.nameksolutions.regchain.curaorganization.responses.profile.Telecom
import com.nameksolutions.regchain.curaorganization.utils.snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TelecomAdapter :
    ListAdapter<Telecom, TelecomAdapter.TelecomViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TelecomAdapter.TelecomViewHolder {
        return TelecomViewHolder(
            ProfileTelecomsItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(
        holder: TelecomAdapter.TelecomViewHolder,
        position: Int
    ) {
        val telecom = getItem(position)
        holder.apply {
            bind(createOnClickListener(telecom), telecom)
            itemView.tag = telecom
        }

    }

    private fun createOnClickListener(telecom: Telecom): View.OnClickListener {
        return View.OnClickListener {
            //navigate to page to show doctor details using navigation directions
//            val direction =
//                PersonnelFragmentDirections.actionPersonnelFragmentToPractitionerItem2(telecom.id)
//            it.findNavController().navigate(direction)
            it.snackbar("${telecom.value} clicked!")
        }
    }

    class TelecomViewHolder(private val binding: ProfileTelecomsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: View.OnClickListener, itemData: Telecom) {
            binding.apply {
                profileTelecomItemClickListener = listener
                telecomItem = itemData

                executePendingBindings()
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Telecom>() {
        override fun areItemsTheSame(
            oldItem: Telecom,
            newItem: Telecom
        ): Boolean {
            return oldItem.value == newItem.value
        }

        override fun areContentsTheSame(
            oldItem: Telecom,
            newItem: Telecom
        ): Boolean {
            return oldItem == newItem
        }

    }

}