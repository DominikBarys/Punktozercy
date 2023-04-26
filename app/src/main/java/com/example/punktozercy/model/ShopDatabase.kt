package com.example.punktozercy.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * abstract class responsible for creating a database using User, Product
 * and ShoppingHistory Entities
 * @property userDao
 * @property productDao
 * @property shoppingHistoryDao
 * @property products
 * @property INSTANCE
 * @property DatabaseCallback
 * @property getDatabase
 */
@Database(entities = [User::class,Product::class,ShoppingHistory::class], version = 1, exportSchema = false)
abstract class ShopDatabase: RoomDatabase() {

    /**
     * function responsible for getting userDao object
     * @return UserDao object
     */
    abstract fun userDao():UserDao
    /**
     * function responsible for getting ProductDao object
     * @return ProductDao object
     */
    abstract fun productDao():ProductDao
    /**
     * function responsible for getting ShoppingHistoryDao object
     * @return ShoppingHistoryDao object
     */
    abstract fun shoppingHistoryDao():ShoppingHistoryDao


    /**
     * object responsible for initializing database with products
     */
    companion object{
        private val products :List<Product> = listOf(
            Product(0, "Butter", 5.99, "Butter made from cow's milk", "dairy", 140, "butter.jpg"),
            Product(0, "Country Sausage", 24.99, "Traditional country sausage made from pork and bacon", "meat", 500, "sausage.jpg"),
            Product(0, "Sauerkraut", 3.99, "Sauerkraut preserved in brine", "vegetables", 100, "sauerkraut.jpg"),
            Product(0, "Rye Bread", 4.49, "Whole grain rye bread", "bakery", 125, "rye_bread.jpg"),
            Product(0, "Mead", 19.99, "Natural mead with a smoky flavor", "drink", 800, "mead.jpg"),
            Product(0, "Blue Cheese", 29.99, "Assorted blue cheeses", "dairy", 750, "blue_cheese.jpg"),
            Product(0, "Buckwheat Groats", 2.49, "Unroasted buckwheat groats", "cereals", 50, "buckwheat_groats.jpg"),
            Product(0, "Ruskie Pierogi", 9.99, "Pierogi with potato and cheese filling", "ready meals", 245, "pierogi.jpg"),
            Product(0, "Toruń Gingerbread", 12.99, "Traditional gingerbread from Toruń", "sweets", 200, "gingerbread.jpg"),
            Product(0, "Apple Juice", 4.99, "100% apple juice", "drink", 130, "apple_juice.jpg"),
            Product(0, "Goat Cheese", 14.99, "Goat cheese made from goat's milk", "dairy", 300, "goat_cheese.jpg"),
            Product(0, "Homemade Sausage", 18.99, "Sausage made from pork and beef", "meat", 400, "homemade_sausage.jpg"),
            Product(0, "Pickled Cucumbers", 2.99, "Pickled cucumbers in brine", "vegetables", 50, "pickled_cucumbers.jpg"),
            Product(0, "Whole Bean Coffee", 19.99, "Whole bean coffee made from a blend of Arabica and Robusta", "drink", 500, "coffee.jpg"),
            Product(0, "Feta Cheese", 9.99, "Feta cheese made from cow's milk", "dairy", 190, "feta_cheese.jpg"),
            Product(0, "Wheat Beer", 4.99, "Craft wheat beer", "beverages", 100, "wheat_beer.jpg"),
            Product(0, "Blood Sausage", 8.99, "Blood sausage made from pork blood and fat", "meat", 180, "blood_sausage.jpg"),
            //-----------------------------------NEW PRODUCTS----------------------------------------------------------
            //READY MEALS
            Product(0, "Pudliszki stuffed cabbage", 15.00, "Stuffed cabbage in tomato sauce", "ready meals", 250, "stuffed_cabbage.jpg"),
            Product(0, "Pizza Feliciana vegan", 16.50, "Slightly spicy pizza with vegetables", "ready meals", 280, "feliciana_pizza.jpg"),
            Product(0, "Pizza Guseppe", 14.50, "Guseppe pizza with ham", "ready meals", 240, "guseppe_pizza.jpg"),
            Product(0, "Silesian sour soup", 13.50, "Nutritious soup on smoked meat with sausage, bacon and potatoes", "ready meals", 220, "silesian_sour.jpg"),
             // ALCOCHOLS

            Product(0, "Żubr", 2.80, "Żubr is full of peace and harmony, excellent for relaxing in the open air after the hardships of the week.", "beverages", 50, "zubr_bear.jpg"),
            Product(0, "Kustosz Tequilia", 3.50, "Tequila-flavored beer with a lemon-flavored drink.", "beverages", 65, "kustosz_bear.jpg"),
            Product(0, "Żubrówka", 31.00, "It draws its power from the most inaccessible areas", "beverages", 590, "zubrowka.jpg"),

            //PRZYPRAWY
            Product(0, "Seasoning for chicken 30g", 1.80, "Seasoning for chicken golden 30g Kamis is a blend of herbs and spices.", "spices", 29, "chicken_spice.jpg"),
            //FIZZY DRINK
            Product(0, "Coca-cola", 6.20, "Cola-flavored carbonated beverage.", "fizzy drink",130, "cola.jpg"),
            Product(0, "Polish apples 0.5kg", 7.20, "Delicious  apples from Poland.", "fruits",130, "apples.jpg")
        )

        @Volatile
        /**
         * database object
         */
        private var INSTANCE: ShopDatabase? = null

        /**
         * class responsible for making a callback to initialize database at the very start
         * @param context context of the application
         * @param products list of the products that will be send to the database
         */
        private class DatabaseCallback(private val context: Context, private val products: List<Product>) : RoomDatabase.Callback() {
            @OptIn(DelicateCoroutinesApi::class)
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // Wykonywanie kodu przy tworzeniu bazy danych
                val productDao = ShopDatabase.getDatabase(context).productDao()
                GlobalScope.launch {
                    productDao.insertProducts(products)
                }

            }
        }

        /**
         * function responsible for making that the database was created only once
         * (Singleton)
         */
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