package com.thegabru.kncc.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thegabru.kncc.AppManager
import com.thegabru.kncc.BaseApplication
import com.thegabru.kncc.R
import com.thegabru.kncc.adapters.MessagesSentAdapter
import com.thegabru.kncc.models.Contact
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.jetbrains.anko.doAsync

class DashBoardFragment : Fragment() {

    private var appManger: AppManager? = null
    private var msgSentAdapter = MessagesSentAdapter()
    var msgListRecyclerView: RecyclerView? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        appManger = (activity?.applicationContext as BaseApplication).appManager
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fragment_dashboard, null, false)

        msgListRecyclerView = rootView.findViewById<RecyclerView>(R.id.rv_dashboard)
        msgListRecyclerView!!.layoutManager = LinearLayoutManager(context)
        msgListRecyclerView!!.adapter = msgSentAdapter

        return rootView
    }

    override fun onResume() {
        super.onResume()

        doAsync {
            val contact = appManger!!.getSavedContacts()

            if (contact?.isEmpty()!!) {
                tv_empty_list.visibility = View.VISIBLE
            } else {
                tv_empty_list.visibility = View.GONE
                msgSentAdapter.currentResults.clear()
                msgSentAdapter.currentResults.addAll(contact)
                activity!!.runOnUiThread { msgSentAdapter.notifyDataSetChanged() }
            }
        }
    }

}