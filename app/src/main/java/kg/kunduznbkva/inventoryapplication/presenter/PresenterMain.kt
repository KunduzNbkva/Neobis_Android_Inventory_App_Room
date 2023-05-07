package kg.kunduznbkva.inventoryapplication.presenter

import android.content.Context
import kg.kunduznbkva.inventoryapplication.database.local.ProductDatabase
import kg.kunduznbkva.inventoryapplication.database.local.RepositoryProduct
import kg.kunduznbkva.inventoryapplication.model.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PresenterMain(
    private var context: Context
) : IMainPresenter {
    private val repositoryProduct: RepositoryProduct

    init {
        val productDao = ProductDatabase.getInstance(context)?.productDao()
        repositoryProduct = productDao?.let { RepositoryProduct(it) }!!
    }

    override fun insertProduct(product: Product) {
        CoroutineScope(Dispatchers.IO).launch {
            repositoryProduct.insertProduct(product)
        }
    }

    override fun deleteProduct(product: Product) {
        CoroutineScope(Dispatchers.IO).launch {
            repositoryProduct.deleteProduct(product)
        }
    }

    override fun getAllProducts() {
        CoroutineScope(Dispatchers.IO).launch {
            repositoryProduct.getAllProducts()
        }
    }

    override fun updateProduct(product: Product) {
        CoroutineScope(Dispatchers.IO).launch {
            repositoryProduct.updateProduct(product)
        }
    }
}