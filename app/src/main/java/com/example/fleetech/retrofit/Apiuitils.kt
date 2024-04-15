package com.example.fleetech.retrofit

object Apiuitils {
    //Deprecated
    //private const val SEMI_STAGING_URL = "https://Fleetech.in/NRVDriverAPI/api/"

    //Demo API
    //        private const val SEMI_STAGING_URL = "https://Fleetech.in/DemoDriverAPI/api/"

    //NRV
 //   private const val SEMI_STAGING_URL = "https://Fleetech.in/NRVDriverMAPI/api/"
    //Test
    private const val SEMI_STAGING_URL = "https://fleetech.in/FleetechDriverAPI/api/"

    val testUrl: Api
        get() = RetrofitClient.getClient(SEMI_STAGING_URL)!!.create(
            Api::class.java
        )


}