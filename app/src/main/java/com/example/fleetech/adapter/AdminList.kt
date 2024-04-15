package com.example.fleetech.adapter

import android.content.res.Resources
import com.example.fleetech.retrofit.Admin

/* Returns initial list of flowers. */
fun adminList(resources: Resources): List<Admin> {
    return listOf(
        Admin(
            srNo = "1",
            name = "Amit",
            vehicleNo = "HR0567",
            status = "Free",
            lastLogin = "12/04",
            active = "yes"
        ),
        Admin(
            srNo = "2",
            name = "Rahul",
            vehicleNo = "HR0567",
            status = "Free",
            lastLogin = "12/04",
            active = "yes"
        ),
        Admin(
            srNo = "3",
            name = "Amit",
            vehicleNo = "HR0567",
            status = "Free",
            lastLogin = "12/04",
            active = "yes"
        )

    )
}