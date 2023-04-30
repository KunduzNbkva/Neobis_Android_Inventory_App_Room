package kg.kunduznbkva.inventoryapplication.database.local

import androidx.room.Database
import androidx.room.RoomDatabase
import kg.kunduznbkva.inventoryapplication.model.Product

@Database(entities = [Product::class], version = 1)
abstract class ProductDatabase: RoomDatabase() {
    abstract fun productDao() : ProductDao
}