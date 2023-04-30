package kg.kunduznbkva.inventoryapplication.database.local

import androidx.room.*
import kg.kunduznbkva.inventoryapplication.model.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM Product")
    fun getAllProducts() :  MutableList<Product>

    @Query("SELECT * FROM Product WHERE archived = 1 ORDER BY id ASC")
    fun getAllArchiveProducts() :  MutableList<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: Product)

    @Update
    fun updateProduct(product: Product)

    @Delete
    fun deleteProduct(product: Product)


}