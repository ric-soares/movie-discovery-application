package com.example.moviediscoveryapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.example.moviediscoveryapplication.R

class ProfileScreenViewModel : ViewModel() {

    fun mapIcon(itemName: String): Int? {
        return when (itemName.lowercase()) {
            "Change password".lowercase() -> R.drawable.ic_password
            "Notification".lowercase() -> R.drawable.ic_notification
            "Language".lowercase() -> R.drawable.ic_language
            "Clear Cache".lowercase() -> R.drawable.ic_delete
            "Help & Feedback".lowercase() -> R.drawable.ic_question
            "About Us".lowercase() -> R.drawable.ic_about
            else -> null
        }
    }
}