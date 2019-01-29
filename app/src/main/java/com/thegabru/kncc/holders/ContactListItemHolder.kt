package com.thegabru.kncc.holders

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.thegabru.kncc.R
import com.thegabru.kncc.activities.ContactActivity
import com.thegabru.kncc.models.Contact

class ContactListItemHolder(itemView : View): RecyclerView.ViewHolder(itemView) {

    private val fname: TextView = itemView.findViewById<TextView>(R.id.fname)
    private val lname: TextView = itemView.findViewById<TextView>(R.id.lname)
    private val number: TextView = itemView.findViewById<TextView>(R.id.number)
    private var currentContact: Contact? = null

    init {
        itemView.setOnClickListener { view: View? ->
            var detailPageIntent = Intent(itemView.context, ContactActivity::class.java)
            var pageJson = Gson().toJson(currentContact)
            detailPageIntent.putExtra("page", pageJson)
            itemView.context.startActivity(detailPageIntent)
        }
    }

    fun updateWithPage(contact: Contact) {

        fname.text = contact.fname
        lname.text = contact.lname
        number.text = contact.contactNo

        currentContact = contact
    }
}