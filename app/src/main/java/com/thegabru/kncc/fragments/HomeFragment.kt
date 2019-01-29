package com.thegabru.kncc.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.thegabru.kncc.AppManager
import com.thegabru.kncc.BaseApplication
import com.thegabru.kncc.R
import com.thegabru.kncc.adapters.ContactListAdapter
import com.thegabru.kncc.models.Contact
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.doAsync

class HomeFragment : Fragment() {

    private var appManger: AppManager? = null
    var contactListRecycler: RecyclerView? = null
    private val contactAdapter = ContactListAdapter()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        appManger = (activity?.applicationContext as BaseApplication).appManager
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fragment_home, null, false)

        contactListRecycler = rootView.findViewById<RecyclerView>(R.id.rv_contact)
        contactListRecycler!!.layoutManager = LinearLayoutManager(context)
        contactListRecycler!!.adapter = contactAdapter

        return rootView
    }

    override fun onResume() {
        super.onResume()

        doAsync {
            val contact = appManger!!.getContacts()

            if (contact.isEmpty()) {
                tv_empty_list.visibility = View.VISIBLE
            } else {
                tv_empty_list.visibility = View.GONE
                contactAdapter.currentResults.clear()
                contactAdapter.currentResults.addAll(contact as ArrayList<Contact>)
                activity!!.runOnUiThread { contactAdapter.notifyDataSetChanged() }
            }
        }
    }


}