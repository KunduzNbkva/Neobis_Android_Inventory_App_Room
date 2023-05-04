package kg.kunduznbkva.inventoryapplication.database.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kg.kunduznbkva.inventoryapplication.model.Product

@Database(entities = [Product::class], version = 1)
abstract class ProductDatabase: RoomDatabase() {
    abstract fun productDao() : ProductDao

    companion object {
        private var INSTANCE: ProductDatabase? = null
        fun getInstance(context: Context): ProductDatabase? {
            if (INSTANCE == null) {
                synchronized(ProductDatabase::class){
                    INSTANCE = Room.databaseBuilder(
                        context = context.applicationContext,
                        klass = ProductDatabase::class.java,
                        name = "inventory_app_products_base"
                    ).build()
                }

            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }

    }
}