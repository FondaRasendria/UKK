package com.example.uklkasir.userdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        DetailTransaksi::class,
        Meja::class,
        Menu::class,
        Transaksi::class,
        User::class
    ],
    version = 4
)
abstract class CafeDatabase: RoomDatabase() {
    abstract fun cafeDao(): CafeDao

    companion object{
        private var instance: CafeDatabase? = null

        fun getInstance(context: Context): CafeDatabase{
            if(instance == null){
                instance = Room.databaseBuilder(context, CafeDatabase::class.java, "cafe_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }

            return instance!!
        }
    }
}