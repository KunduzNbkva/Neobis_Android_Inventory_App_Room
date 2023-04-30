package kg.kunduznbkva.inventoryapplication

import android.app.Application
import androidx.room.Room
import kg.kunduznbkva.inventoryapplication.database.local.ProductDatabase

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            this,
            ProductDatabase::class.java,
            "inventory_app_products_base")
            .allowMainThreadQueries()
            .build()
    }


    companion object{
        lateinit var db:ProductDatabase
    }
}