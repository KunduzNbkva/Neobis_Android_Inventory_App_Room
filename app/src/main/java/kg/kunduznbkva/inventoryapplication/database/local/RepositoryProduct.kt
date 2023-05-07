package kg.kunduznbkva.inventoryapplication.database.local

import kg.kunduznbkva.inventoryapplication.model.Product

class RepositoryProduct(private val productDao: ProductDao) {

    suspend fun getAllProducts(): List<Product> {
        return productDao.getAllProducts()
    }

    suspend fun getAllArchived(): List<Product> {
        return productDao.getAllArchiveProducts()
    }

    suspend fun insertProduct(product: Product) {
        return productDao.insert(product)
    }

    suspend fun updateProduct(product: Product) {
        return productDao.updateProduct(product)
    }

    suspend fun deleteProduct(product: Product) {
        return productDao.deleteProduct(product)
    }

}