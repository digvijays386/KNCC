package com.thegabru.kncc

import com.google.gson.Gson
import com.thegabru.kncc.models.Contact
import com.thegabru.kncc.models.SentModel
import com.thegabru.kncc.repository.SentModelsRepository


class AppManager(private  val sentModelsRepository: SentModelsRepository) {

    private var contactCache: ArrayList<SentModel>? = null

    private val sampleJson = """
        [
{
  "First_Name": "Jasmeet",
  "Last_Name": "Singh",
  "Phone_Number": "816383299"
},
{
  "First_Name": "Digvijay",
  "Last_Name": "Singh",
  "Phone_Number": "9927945802"
},
{
  "First_Name": "Johnson",
  "Last_Name": "Herbal",
  "Phone_Number": "7906594137"
},
{
  "First_Name": "Dhirendra Partap",
  "Last_Name": "Singh",
  "Phone_Number": "8126273272"
},
{
  "First_Name": "karan",
  "Last_Name": "Chaudhary",
  "Phone_Number": "9933999999"
},
{
  "First_Name": "Lavish",
  "Last_Name": "Kumar",
  "Phone_Number": "9536799999"
},
{
  "First_Name": "Bhanu Partap",
  "Last_Name": "Singh",
  "Phone_Number": "9758065536"
}
]
    """

    fun saveSentContact(contact: SentModel) {
        contactCache?.add(contact)
        sentModelsRepository.addFavorite(contact)
    }

    fun getSavedContacts(): ArrayList<SentModel>? {
        if (contactCache == null) {
            contactCache = sentModelsRepository.getAllContacts()
        }
        contactCache!!.sortByDescending { it.time }
//        contactCache!!.sortedByDescending { it.time } as ArrayList<SentModel>
        return contactCache
    }

    fun getContacts(): List<Contact> {

        return Gson().fromJson(sampleJson, Array<Contact>::class.java).toList()
    }

}