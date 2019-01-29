package com.thegabru.kncc.repository

import com.thegabru.kncc.DBHelper
import com.thegabru.kncc.models.Contact
import com.thegabru.kncc.models.SentModel
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class SentModelsRepository(var databaseHelper: DBHelper) {

        private val TABLE_NAME: String = "SentModels"

        fun addFavorite(page: SentModel) {
            databaseHelper.use {
                insert( TABLE_NAME, "id" to page.contactId,
                    "fname" to page.fname,
                    "lname" to page.lname,
                    "contact_no" to page.contactNo,
                    "time" to page.time,
                    "otp" to page.otp
                )
            }
        }

        fun getAllContacts(): ArrayList<SentModel> {
            var pages = ArrayList<SentModel>()

            databaseHelper.use {
                select(TABLE_NAME, "id", "fname", "lname", "contact_no", "time", "otp")
                    .parseList(object : MapRowParser<List<SentModel>> {
                        override fun parseRow(columns: Map<String, Any?>): List<SentModel> {
                            val id = columns.getValue("id")
                            val fname = columns.getValue("fname")
                            val lname = columns.getValue("lname")
                            val contact = columns.getValue("contact_no")
                            val time = columns.getValue("time")
                            val otp = columns.getValue("otp")

                            val page = SentModel(contactId = id.toString().toInt(), fname = fname.toString(),
                                lname = lname.toString(),contactNo = contact.toString(),
                                time = time.toString(), otp = otp.toString())

                            pages.add(page)
                            return pages
                        }
                    })
            }
            return pages
        }
    }