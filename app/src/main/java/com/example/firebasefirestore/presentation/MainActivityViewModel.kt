package com.example.firebasefirestore.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebasefirestore.data.models.Cat
import com.example.firebasefirestore.data.models.FirebaseException
import com.example.firebasefirestore.data.repository.FirebaseRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivityViewModel(private val firebaseRepository: FirebaseRepository) : ViewModel(){

    val dataCat: MutableLiveData<Cat> by lazy { MutableLiveData<Cat>() }
    val catName: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    fun findCat(){
        GlobalScope.launch{
            try{
                val cat = firebaseRepository.readCat(catName.value.toString())
                if (cat != null) {
                    Log.e("MY", "name ${cat.name}")
                    dataCat.postValue(cat)
                }
            }catch (e: FirebaseException){

            }
        }
    }

    fun setCatName(name : String){
        catName.postValue(name)
        Log.e("MY", "name $name")
    }
}