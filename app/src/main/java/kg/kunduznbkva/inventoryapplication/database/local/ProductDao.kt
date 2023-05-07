package kg.kunduznbkva.inventoryapplication.database.local

import androidx.room.*
import kg.kunduznbkva.inventoryapplication.model.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM Product WHERE archived = 0 ORDER BY id ASC")
    suspend fun getAllProducts() :  MutableList<Product>

    @Query("SELECT * FROM Product WHERE archived = 1 ORDER BY id ASC")
    suspend fun getAllArchiveProducts() :  MutableList<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: Product)

    @Update
    suspend fun updateProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)


}