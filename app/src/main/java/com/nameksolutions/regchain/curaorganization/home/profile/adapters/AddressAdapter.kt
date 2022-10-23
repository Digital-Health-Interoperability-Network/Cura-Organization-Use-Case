package com.nameksolutions.regchain.curaorganization.home.profile.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nameksolutions.regchain.curaorganization.databinding.ProfileAddressesItemBinding
import com.nameksolutions.regchain.curaorganization.responses.profile.Addres
import com.nameksolutions.regchain.curaorganization.responses.profile.Telecom
import com.nameksolutions.regchain.curaorganization.utils.snackbar

class AddressAdapter :
    ListAdapter<Addres, AddressAdapter.AddressViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddressAdapter.AddressViewHolder {
        return AddressViewHolder(
            ProfileAddressesItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(
        holder: AddressAdapter.AddressViewHolder,
        position: Int
    ) {
        val address = getItem(position)
        holder.apply {
            bind(createOnClickListener(address), address)
            itemView.tag = address
        }

    }

    private fun createOnClickListener(address: Addres): View.OnClickListener {
        return View.OnClickListener {
            //navigate to page to show doctor details using navigation directions
//            val direction =
//                PersonnelFragmentDirections.actionPersonnelFragmentToPractitionerItem2(telecom.id)
//            it.findNavController().navigate(direction)
            it.snackbar("${address.text} clicked!")
        }
    }

    class AddressViewHolder(private val binding: ProfileAddressesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: View.OnClickListener, itemData: Addres) {
            binding.apply {
                profileAddressItemClickListener = listener
                addressItem = itemData
                executePendingBindings()
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Addres>() {
        override fun areItemsTheSame(
            oldItem: Addres,
            newItem: Addres
        ): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(
            oldItem: Addres,
            newItem: Addres
        ): Boolean {
            return oldItem == newItem
        }

    }

}