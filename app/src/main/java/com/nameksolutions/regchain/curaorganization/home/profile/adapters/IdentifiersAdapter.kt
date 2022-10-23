package com.nameksolutions.regchain.curaorganization.home.profile.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nameksolutions.regchain.curaorganization.databinding.ProfileIdentifiersItemBinding
import com.nameksolutions.regchain.curaorganization.responses.profile.Identifier
import com.nameksolutions.regchain.curaorganization.responses.profile.Telecom
import com.nameksolutions.regchain.curaorganization.utils.snackbar

class IdentifiersAdapter :
    ListAdapter<Identifier, IdentifiersAdapter.IdentifierViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IdentifiersAdapter.IdentifierViewHolder {
        return IdentifierViewHolder(
            ProfileIdentifiersItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(
        holder: IdentifiersAdapter.IdentifierViewHolder,
        position: Int
    ) {
        val identifier = getItem(position)
        holder.apply {
            bind(createOnClickListener(identifier), identifier)
            itemView.tag = identifier
        }

    }

    private fun createOnClickListener(identifier: Identifier): View.OnClickListener {
        return View.OnClickListener {
            //navigate to page to show doctor details using navigation directions
//            val direction =
//                PersonnelFragmentDirections.actionPersonnelFragmentToPractitionerItem2(telecom.id)
//            it.findNavController().navigate(direction)
            it.snackbar("${identifier.value} clicked!")
        }
    }

    class IdentifierViewHolder(private val binding: ProfileIdentifiersItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: View.OnClickListener, itemData: Identifier) {
            binding.apply {
                profileIdentifierItemClickListener = listener
                identifierItem = itemData
                executePendingBindings()
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Identifier>() {
        override fun areItemsTheSame(
            oldItem: Identifier,
            newItem: Identifier
        ): Boolean {
            return oldItem.value == newItem.value
        }

        override fun areContentsTheSame(
            oldItem: Identifier,
            newItem: Identifier
        ): Boolean {
            return oldItem == newItem
        }

    }

}