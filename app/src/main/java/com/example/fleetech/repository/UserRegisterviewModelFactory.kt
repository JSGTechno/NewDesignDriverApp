package com.example.fleetech.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fleetech.viewModel.UserRegisterViewModel

//class UserRegisterviewModelFactory constructor(private val repository: UserRegisterRepo) :
//ViewModelProvider.Factory
//{
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        return if (modelClass.isAssignableFrom(UserRegisterViewModel::class.java)) {
////            UserRegisterViewModel(this.repository) as T
//        } else {
//            throw IllegalArgumentException("ViewModel Not Found")
//        }
//    }
//}