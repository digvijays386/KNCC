package com.thegabru.kncc.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thegabru.kncc.R
import com.thegabru.kncc.holders.ContactListItemHolder
import com.thegabru.kncc.models.Contact

class ContactListAdapter() : RecyclerView.Adapter<ContactListItemHolder>() {

        var currentResults: ArrayList<Contact> = ArrayList<Contact>()

        override fun getItemCount(): Int {
            return currentResults.size // temporary
        }

        override fun onBindViewHolder(holder: ContactListItemHolder, position: Int) {

            var contact = currentResults[position]
            holder.updateWithPage(contact)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactListItemHolder {

            var cardItem = LayoutInflater.from(parent.context).inflate(R.layout.contact_list_item, parent, false)
            return ContactListItemHolder(cardItem)
        }

}