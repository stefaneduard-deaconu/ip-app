package com.example.ipapp.ui.docs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DocsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Here are your docs"
    }
    val text: LiveData<String> = _text
}