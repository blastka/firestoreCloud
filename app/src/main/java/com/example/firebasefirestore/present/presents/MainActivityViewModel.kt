package com.example.firebasefirestore.present.presents

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebasefirestore.present.models.Cat
import com.example.firebasefirestore.present.repository.FirebaseRepository

class MainActivityViewModel(private val firebaseRepository: FirebaseRepository) : ViewModel(){

    val dataCat: MutableLiveData<Cat> by lazy { MutableLiveData<Cat>() }

    fun findCat(){

    }
}