package com.example.ceibatest.data.model.responses

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class User(
    @PrimaryKey(autoGenerate = false)
    @Json(name = "id")
    val id: Int?,

    @Json(name = "name")
    val name: String?,

    @Json(name = "email")
    val email: String?,

    @Json(name = "phone")
    val phone: String?,
    )
