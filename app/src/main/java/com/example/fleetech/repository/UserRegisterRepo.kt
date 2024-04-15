package com.example.fleetech.repository

import com.example.fleetech.retrofit.Api
import com.example.fleetech.retrofit.RegistrationModel

class UserRegisterRepo constructor(val retrofitService: Api) {

    lateinit var user: RegistrationModel

     fun getRegisterData() = retrofitService.userRegistration(user)
}
