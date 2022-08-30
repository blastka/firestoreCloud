package com.example.firebasefirestore.present.repository

import android.util.Log
import com.example.firebasefirestore.present.models.Cat
import com.example.firebasefirestore.present.models.FirebaseException
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirebaseRepository: FirebaseRepositoryInterface {
    val db = FirebaseFirestore.getInstance()

    companion object
    {
        const val PATH_USER = "users"
        const val PATH_CAT = "cat"
        const val FIELD_NAME_CAT = "name"
    }

    override suspend fun readCat(name: String): Cat? {
        val docRef = db.collection(PATH_CAT)
        return docRef
            .whereEqualTo(FIELD_NAME_CAT, name)
            .get()
            .awaitReadPaidProbe()
    }

    override suspend fun addCats(cat: Cat) {
        TODO("Not yet implemented")
    }

    private suspend  fun Task<QuerySnapshot>.awaitReadPaidProbe():  Cat?
    {
        return suspendCoroutine { continuation ->
            addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var documentProbe: Cat? = null
                    for (document in task.result!!) {
                        Log.d("TAG", document.id + " => " + document.data)
                        documentProbe  = document.toObject(Cat::class.java)
                    }
                    continuation.resume(documentProbe)
                }
            }
            addOnFailureListener{
                continuation.resumeWithException(
                    FirebaseException(
                        FirebaseException.FirebaseExceptions.DOCUMENT_FAILURE_READ))
            }
        }
    }

}