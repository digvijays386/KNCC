package com.thegabru.kncc.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.github.kittinunf.fuel.Fuel
import com.google.gson.Gson
import com.thegabru.kncc.R
import com.thegabru.kncc.fragments.SendOTPDialogFragment
import com.thegabru.kncc.models.Contact
import kotlinx.android.synthetic.main.activity_contact.*
import org.jetbrains.anko.toast

class ContactActivity : AppCompatActivity() {

    lateinit var contact: Contact

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // get the page from the extras
        val wikiPageJson = intent.getStringExtra("page")
        contact = Gson().fromJson<Contact>(wikiPageJson, Contact::class.java)

        title = contact.fname

        tv_fname.text = contact.fname
        tv_lname.text = contact.lname
        tv_cellNo.text = contact.contactNo

        cv_msg.setOnClickListener {

            val fm = supportFragmentManager

            val editNameDialogFragment =
                SendOTPDialogFragment.newInstance("Send OTP", Gson().toJson(contact).toString())
//            editNameDialogFragment.setTargetFragment(this, 300)
            editNameDialogFragment.show(fm, "fragment_edit_name")

        }
        cv_call.setOnClickListener { toast("This function is not implemented yet!") }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home) {
            finish()
        }
        return true
    }

}
