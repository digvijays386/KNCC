package com.thegabru.kncc.holders

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.thegabru.kncc.R
import com.thegabru.kncc.activities.ContactActivity
import com.thegabru.kncc.models.SentModel
import org.jetbrains.anko.toast

class SentListItemHolder(itemView : View): RecyclerView.ViewHolder(itemView) {

    private val name: TextView = itemView.findViewById<TextView>(R.id.name)
    private val number: TextView = itemView.findViewById<TextView>(R.id.phone)
    private val time: TextView = itemView.findViewById<TextView>(R.id.time)
    private val otp: TextView = itemView.findViewById<TextView>(R.id.otp)
    private var currentContact: SentModel? = null

    init {
        itemView.setOnClickListener { view: View? ->

            itemView.context.toast("messages already sent!")
//            var detailPageIntent = Intent(itemView.context, ContactActivity::class.java)
//            var pageJson = Gson().toJson(currentContact)
//            detailPageIntent.putExtra("page", pageJson)
//            itemView.context.startActivity(detailPageIntent)
        }
    }

    fun updateWithPage(contact: SentModel) {

        name.text = contact.fname +" "+contact.lname
        number.text = contact.contactNo
        time.text = contact.time
        otp.text = contact.otp

        currentContact = contact
    }
}