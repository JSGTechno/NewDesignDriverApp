package com.example.fleetech.retrofit

import com.example.fleetech.retrofit.model.*
import com.example.fleetech.retrofit.response.*
import com.example.fleetech.retrofit.response.ApiRegister.ACCOUNT_BALANCE
import com.example.fleetech.retrofit.response.ApiRegister.ASSIGN_ORDER_LIST
import com.example.fleetech.retrofit.response.ApiRegister.CHALLAN_LIST
import com.example.fleetech.retrofit.response.ApiRegister.CHECK_OTP
import com.example.fleetech.retrofit.response.ApiRegister.DISPATCH_ORDER_LIST
import com.example.fleetech.retrofit.response.ApiRegister.DRIVER_CHECKIN_DETAILS
import com.example.fleetech.retrofit.response.ApiRegister.DRIVER_DOCUMENTS
import com.example.fleetech.retrofit.response.ApiRegister.DRIVER_DUTY
import com.example.fleetech.retrofit.response.ApiRegister.DRIVER_LOCATION
import com.example.fleetech.retrofit.response.ApiRegister.DRIVER_PROFILE
import com.example.fleetech.retrofit.response.ApiRegister.PAYMENT_DETAIL
import com.example.fleetech.retrofit.response.ApiRegister.REGISTER_USER
import com.example.fleetech.retrofit.response.ApiRegister.RESEND_OTP
import com.example.fleetech.retrofit.response.ApiRegister.SETTLEMENT_LIST
import com.example.fleetech.retrofit.response.ApiRegister.TRIP_DETAILS
import com.example.fleetech.retrofit.response.ApiRegister.TRIP_HISTORY
import com.example.fleetech.retrofit.response.ApiRegister.UPDATE_PWD
import com.example.fleetech.retrofit.response.ApiRegister.VEHICLE_REPORTED
import com.example.fleetech.retrofit.response.ApiRegister.VERIFY_PWD
import com.example.fleetech.util.ErrorResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface Api {

    @Headers("jHeaderKey: 2822902663253743989656774574587600639682.450")
    @POST(REGISTER_USER)
    fun userRegistration(@Body registrationModel: RegistrationModel?):
            Call<RegistrationResponse>

    @Headers("jHeaderKey: 2822902663253743989656774574587600639682.450")
    @POST(CHECK_OTP)
    fun checkOTP(@Body otpModel: OTPModel?):
            Call<OTPResponse>

    @Headers("jHeaderKey: 2822902663253743989656774574587600639682.450")
    @POST(UPDATE_PWD)
    fun userUpdatePWd(@Body updateModel: UpdateModel?):
            Call<UpdatePWDResponse>

    @Headers("jHeaderKey: 2822902663253743989656774574587600639682.450")
    @POST(RESEND_OTP)
    fun resendOTP(@Body updateModel: OTPModel?):
            Call<ResendOtpResponse>

    @Headers("jHeaderKey: 2822902663253743989656774574587600639682.450")
    @POST(VERIFY_PWD)
    fun verifyPWD(@Body updateModel: UpdateModel?):
            Call<VerifyNewRespone>

    @Headers("jHeaderKey: 2822902663253743989656774574587600639682.450")
    @POST(VERIFY_PWD)
    fun loginVerifyPWD(@Body updateModel: UpdateModel?):
            Call<VerifyNewRespone>

    @Headers("jHeaderKey: 2822902663253743989656774574587600639682.450")
    @GET(ACCOUNT_BALANCE)
    fun checkAccountBalance(@Header("Authorization") token: String):
            Call<AccountBalanceResponse>

    @Headers("jHeaderKey: 2822902663253743989656774574587600639682.450")
    @GET(TRIP_DETAILS)
    fun driverTripDetails(@Header("Authorization") token: String):
            Call<TripDetailResponse>

    @Headers("jHeaderKey: 2822902663253743989656774574587600639682.450")
    @GET(DRIVER_PROFILE)
    fun driverProfile(@Header("Authorization") token: String):
            Call<ProfileResponse>

    @Headers("jHeaderKey: 2822902663253743989656774574587600639682.450")
    @GET(DRIVER_DOCUMENTS)
    fun driverDocuments(@Header("Authorization") token: String):
            Call<DriverDocuments>

    @Headers("jHeaderKey: 2822902663253743989656774574587600639682.450")
    @GET(PAYMENT_DETAIL)
    fun paymentDetail(@Header("Authorization") token: String):
            Call<PaymentDetailResponse>

    @Headers("jHeaderKey: 2822902663253743989656774574587600639682.450")
    @GET(TRIP_HISTORY)
    fun tripHistoryDetails(@Header("Authorization") token: String):
            Call<TripHistoryResponse>


    @Headers("jHeaderKey: 2822902663253743989656774574587600639682.450")
    @GET(DISPATCH_ORDER_LIST)
    fun dispatchOrderListResponse(@Header("Authorization") token: String):
            Call<DispatchOrderListResponse>

    @Headers("jHeaderKey: 2822902663253743989656774574587600639682.450")
    @GET(ASSIGN_ORDER_LIST)
    fun assignOrderListResponse(@Header("Authorization") token: String):
            Call<AssignOrderListResponse>

    @Headers("jHeaderKey: 2822902663253743989656774574587600639682.450")
    @POST(DRIVER_LOCATION)
    fun driverLocation(@Body registrationModel: LocationModel?):
            Call<locationResponse>


    @Headers("jHeaderKey: 2822902663253743989656774574587600639682.450")
    @POST(VEHICLE_REPORTED)
    fun vehicleReporteddata(@Header("Authorization") token: String,@Body registrationModel: VehicleReported?):
            Call<VehicleReporteddataResponse>


    @Headers("jHeaderKey: 2822902663253743989656774574587600639682.450")
    @POST(DRIVER_DUTY)
    fun drivrDuty(@Header("Authorization")token: String, @Body driverDutyModel: DriverDutyModel?):
            Call<DutyDataResponse>

    @Headers("jHeaderKey: 2822902663253743989656774574587600639682.450")
    @POST(DRIVER_CHECKIN_DETAILS)
    fun drivrcheckInDetails(@Header("Authorization")token: String, @Body mobile: String?):
            Call<DutyDataResponse>


    @Headers("jHeaderKey: 2822902663253743989656774574587600639682.450")
    @GET(SETTLEMENT_LIST)
    fun settlementList(@Header("Authorization") token: String):
            Call<MySettleMentResponse>

    @Headers("jHeaderKey: 2822902663253743989656774574587600639682.450")
    @GET(CHALLAN_LIST)
    fun mychallanList(@Header("Authorization") token: String):
            Call<MyChallanResponse>

    @Multipart
    @Headers("jHeaderKey:2822902663253743989656774574587600639682.450")
    @POST("https://Fleetech.in/FleetechDriverAPI/api/jDriver/jFleetDispatchDocs")
    fun updateProfilePhotoProcess(
        @Header("Authorization") token: String,
        @Query("OrderID") orderID: String?,
        @Part file: MultipartBody.Part,
        @Part file2: MultipartBody.Part?

    ): Call<PODResponse>

    @Multipart
    @Headers("jHeaderKey:2822902663253743989656774574587600639682.450")
    @POST("https://Fleetech.in/FleetechDriverAPI/api/jDriver/jFleetDeliveryDocs")
    fun updatePODData(
        @Header("Authorization") token: String,
        @Query("OrderID") orderID: String?,
        @Part file: MultipartBody.Part,
        @Part file2: MultipartBody.Part?

    ): Call<PODResponse>
}