package com.example.firebasefirestore.present.presents

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.firebasefirestore.present.repository.FirebaseRepository

class MainActivityFactory(val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainActivityViewModel(FirebaseRepository()) as T
    }
}