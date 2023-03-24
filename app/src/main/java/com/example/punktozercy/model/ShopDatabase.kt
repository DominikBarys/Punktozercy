package com.example.punktozercy.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class,Product::class,ShoppingHistory::class], version = 1, exportSchema = false)
abstract class ShopDatabase: RoomDatabase() {

    abstract fun userDao():UserDao
    abstract fun productDao():ProductDao
    abstract fun shoppingHistoryDao():ShoppingHistoryDao

    companion object{

        @Volatile
        private var INSTANCE: ShopDatabase? = null;

        fun getDatabase(context:Context): ShopDatabase{
            val tempInstance = INSTANCE
            if(tempInstance !=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ShopDatabase::class.java,
                    "shop_database"
                ).build()
                INSTANCE= instance
                return instance
            }
        }
    }
}