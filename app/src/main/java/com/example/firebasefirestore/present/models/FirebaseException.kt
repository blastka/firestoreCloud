package com.example.firebasefirestore.present.models

class FirebaseException(val number: FirebaseExceptions) : Throwable()  {
    fun getType(): FirebaseExceptions {
        return number
    }

    enum class FirebaseExceptions{
        DOCUMENT_FAILURE_READ,
        DOCUMENT_FAILURE_CREATE,
        NO_ACCOUNT_LOGIN,
    }
}