package com.example.firebasefirestore.present.repository

import com.example.firebasefirestore.present.models.Cat

interface FirebaseRepositoryInterface {
    suspend fun readCat(name: String): Cat?
    suspend fun addCats(cat: Cat)
}