package com.example.firebasefirestore.present.repository

import com.google.firebase.firestore.FirebaseFirestore

class FirebaseRepository: FirebaseRepositoryInterface {
    val db = FirebaseFirestore.getInstance()

    override fun getCats() {
        TODO("Not yet implemented")
    }

    override fun addCats() {
        TODO("Not yet implemented")
    }

}