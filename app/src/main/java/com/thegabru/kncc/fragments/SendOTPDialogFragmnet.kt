package com.thegabru.kncc.fragments

import android.Manifest
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.thegabru.kncc.R
import com.thegabru.kncc.models.Contact
import kotlinx.android.synthetic.main.fragment_send_otp.*
import org.jetbrains.anko.toast
import kotlin.random.Random
import android.telephony.SmsManager
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.thegabru.kncc.AppManager
import com.thegabru.kncc.BaseApplication
import com.thegabru.kncc.models.SentModel
import java.text.SimpleDateFormat
import java.util.*
import okhttp3.*
import java.io.IOException
import okhttp3.OkHttpClient
import okhttp3.FormBody
import android.util.Log
import android.util.Base64
import org.jetbrains.anko.doAsync


class SendOTPDialogFragment : DialogFragment() {

    private val MY_PERMISSIONS_REQUEST_SEND_SMS = 123
    lateinit var currentContact: Contact
    var appManager: AppManager? = null
    var name: String? = null
    lateinit var finalMsg: String
    lateinit var phoneNo: String
    lateinit var mContext: Context

    var ngrok_url = "http://25dba4aa.ngrok.io/sms"

    var ACCOUNT_SID = "ACf39ad1ed1ee546bd2e656d56ab89ab7a"
    var AUTH_TOKEN = "a0fb3871c2a7f899e0e73280eed2f2ea"
    var fromPhoneNumber = "+12092523518"

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        appManager = (activity?.applicationContext as BaseApplication).appManager

        mContext = activity?.applicationContext as BaseApplication
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_send_otp, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Fetch arguments from bundle and set title
        val title = arguments!!.getString("title", "you can edit otp msg")
        var contactJson = arguments!!.getString("contact")
        currentContact = Gson().fromJson(contactJson, Contact::class.java)

        getDialog().setTitle(title)
        tv_title.text = title
        otp.text = Random.nextInt(0, 1000000).toString()

//        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

        if (checkForPermission(Manifest.permission.INTERNET)) {
            tv_add.isEnabled = true
        } else {
            ActivityCompat.requestPermissions(
                this.activity!!,
                arrayOf(Manifest.permission.INTERNET),
                MY_PERMISSIONS_REQUEST_SEND_SMS
            )
        }
        tv_cancel.setOnClickListener {
            context?.toast("Canceled !")
            dismiss()
        }
        tv_add.setOnClickListener { sendSMSMessage() }
    }

    private fun checkForPermission(permission: String): Boolean {

        var checkPermission = ContextCompat.checkSelfPermission(context!!, permission)
        return checkPermission == PackageManager.PERMISSION_GRANTED
    }

    private fun sendSMSMessage() {
//        var phoneNo = 7906594137.toString()
        phoneNo = "+91" + currentContact.contactNo
        finalMsg = "" + et_msg.text + "& otp is: " + otp.text

        if (phoneNo.isNotEmpty() && finalMsg.isNotEmpty()) {

            if (checkForPermission(Manifest.permission.INTERNET)) {
//                val smsManager = SmsManager.getDefault()
//                smsManager.sendTextMessage(phoneNo, null, finalMsg, null, null)

                sendSms(phoneNo, finalMsg)

                val calendar = Calendar.getInstance()
                val mdformat = SimpleDateFormat("dd-MM HH:mm")

                val strDate = mdformat.format(calendar.time)
                val sentModel = SentModel(
                    contactId = Random.nextInt(0, 100), fname = currentContact.fname,
                    lname = currentContact.lname, contactNo = currentContact.contactNo,
                    time = strDate, otp = otp.text.toString()
                )

                appManager?.saveSentContact(sentModel)
                context?.toast("SMS Sent")
                dismiss()
            } else {
                context?.toast("Internet Permission Denied")
                dismiss()
            }
        } else {
            context?.toast("enter a message")
        }
    }

    private fun sendSms(toPhoneNumber: String, message: String) {
        val client = OkHttpClient()
        val url = "https://api.twilio.com/2010-04-01/Accounts/$ACCOUNT_SID/SMS/Messages"
        val base64EncodedCredentials =
            "Basic " + Base64.encodeToString((ACCOUNT_SID + ":" + AUTH_TOKEN).toByteArray(), Base64.NO_WRAP)

        val body = FormBody.Builder()
            .add("From", fromPhoneNumber)
            .add("To", toPhoneNumber)
            .add("Body", message)
            .build()

        val request = Request.Builder()
            .url(url)
            .post(body)
            .header("Authorization", base64EncodedCredentials)
            .build()
        try {
            doAsync {
                val response = client.newCall(request).execute()
                Log.e("TAG", "sendSms: " + response.body().string())
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_SEND_SMS -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    tv_add.isEnabled = true
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val params = dialog.window!!.attributes
        params.width = 700
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT
        dialog.window!!.attributes = params as android.view.WindowManager.LayoutParams
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    companion object {

        fun newInstance(title: String, contact: String): SendOTPDialogFragment {

            val frag = SendOTPDialogFragment()
            val args = Bundle()
            args.putString("title", title)
            args.putString("contact", contact)

            frag.arguments = args
            return frag
        }
    }
}