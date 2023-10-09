package com.example.uklkasir.userdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Meja(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_meja") var id_meja: Int?,
    @ColumnInfo(name = "nomor_meja") var nomor_meja: String?,
    @ColumnInfo(name = "used") var used: Boolean
)
