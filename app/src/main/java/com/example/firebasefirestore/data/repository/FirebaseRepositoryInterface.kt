package com.example.firebasefirestore.data.repository

import com.example.firebasefirestore.data.models.Cat

interface FirebaseRepositoryInterface {
    suspend fun readCat(name: String): Cat?
    suspend fun addCats(cat: Cat)
}