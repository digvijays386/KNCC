package com.thegabru.kncc.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thegabru.kncc.R
import com.thegabru.kncc.holders.SentListItemHolder
import com.thegabru.kncc.models.SentModel

class MessagesSentAdapter() : RecyclerView.Adapter<SentListItemHolder>() {

    var currentResults: ArrayList<SentModel> = ArrayList<SentModel>()

    override fun getItemCount(): Int {
        return currentResults.size // temporary
    }

    override fun onBindViewHolder(holder: SentListItemHolder, position: Int) {

        var contact = currentResults[position]
        holder.updateWithPage(contact)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SentListItemHolder {

        var cardItem = LayoutInflater.from(parent.context).inflate(R.layout.sent_list_item, parent, false)
        return SentListItemHolder(cardItem)
    }
}