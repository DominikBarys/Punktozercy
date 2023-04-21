package com.example.punktozercy.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@Database(entities = [User::class,Product::class,ShoppingHistory::class], version = 1, exportSchema = false)
abstract class ShopDatabase: RoomDatabase() {

    abstract fun userDao():UserDao
    abstract fun productDao():ProductDao
    abstract fun shoppingHistoryDao():ShoppingHistoryDao

    companion object{
        private val products :List<Product> = listOf(
            Product(0, "Butter", 5.99, "Butter made from cow's milk", "dairy", 140, "butter.jpg"),
            Product(0, "Country Sausage", 24.99, "Traditional country sausage made from pork and bacon", "meat", 500, "sausage.jpg"),
            Product(0, "Sauerkraut", 3.99, "Sauerkraut preserved in brine", "vegetables", 100, "sauerkraut.jpg"),
            Product(0, "Rye Bread", 4.49, "Whole grain rye bread", "bakery", 125, "rye_bread.jpg"),
            Product(0, "Mead", 19.99, "Natural mead with a smoky flavor", "beverages", 800, "mead.jpg"),
            Product(0, "Blue Cheese", 29.99, "Assorted blue cheeses", "dairy", 750, "blue_cheese.jpg"),
            Product(0, "Buckwheat Groats", 2.49, "Unroasted buckwheat groats", "cereals", 50, "buckwheat_groats.jpg"),
            Product(0, "Ruskie Pierogi", 9.99, "Pierogi with potato and cheese filling", "ready meals", 245, "pierogi.jpg"),
            Product(0, "Toruń Gingerbread", 12.99, "Traditional gingerbread from Toruń", "sweets", 200, "gingerbread.jpg"),
            Product(0, "Apple Juice", 4.99, "100% apple juice", "beverages", 130, "apple_juice.jpg"),
            Product(0, "Goat Cheese", 14.99, "Goat cheese made from goat's milk", "dairy", 300, "goat_cheese.jpg"),
            Product(0, "Homemade Sausage", 18.99, "Sausage made from pork and beef", "meat", 400, "homemade_sausage.jpg"),
            Product(0, "Pickled Cucumbers", 2.99, "Pickled cucumbers in brine", "vegetables", 50, "pickled_cucumbers.jpg"),
            Product(0, "Whole Bean Coffee", 19.99, "Whole bean coffee made from a blend of Arabica and Robusta", "beverages", 500, "coffee.jpg"),
            Product(0, "Feta Cheese", 9.99, "Feta cheese made from cow's milk", "dairy", 190, "feta_cheese.jpg"),
            Product(0, "Wheat Beer", 4.99, "Craft wheat beer", "beverages", 100, "wheat_beer.jpg"),
            Product(0, "Blood Sausage", 8.99, "Blood sausage made from pork blood and fat", "meat", 180, "blood_sausage.jpg"))

        @Volatile
        private var INSTANCE: ShopDatabase? = null;

        // Klasa Callback do zainicjalizowania tabeli danymi przed jej stworzeniem
        private class DatabaseCallback(private val context: Context, private val products: List<Product>) : RoomDatabase.Callback() {
            @OptIn(DelicateCoroutinesApi::class)
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // Wykonywanie kodu przy tworzeniu bazy danych
                // Można tutaj zainicjalizować tabelę danymi
                val productDao = ShopDatabase.getDatabase(context).productDao()
                GlobalScope.launch {
                    productDao.insertProducts(products)
                }

            }
        }

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
                ).addCallback(DatabaseCallback(context, products))
                    .build()
                INSTANCE= instance
                return instance
            }
        }
    }
}