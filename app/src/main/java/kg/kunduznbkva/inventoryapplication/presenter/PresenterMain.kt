package kg.kunduznbkva.inventoryapplication.presenter

import android.content.Context
import kg.kunduznbkva.inventoryapplication.database.local.ProductDatabase
import kg.kunduznbkva.inventoryapplication.database.local.RepositoryProduct
import kg.kunduznbkva.inventoryapplication.model.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PresenterMain(
    context: Context
) : IMainPresenter {
    private val repositoryProduct: RepositoryProduct
    private var view: IViewProducts? = null

    init {
        val productDao = ProductDatabase.getInstance(context)?.productDao()
        repositoryProduct = productDao?.let { RepositoryProduct(it) }!!
    }



    override fun getAllProducts() {
        CoroutineScope(Dispatchers.IO).launch {
            val productsList = repositoryProduct.getAllProducts()
            withContext(Dispatchers.Main) {
                view?.viewProducts(productsList)
            }
        }
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

    override fun updateProduct(product: Product) {
        CoroutineScope(Dispatchers.IO).launch {
            repositoryProduct.updateProduct(product)
        }
    }

    override fun searchProduct(searchQuery: String) {
        val searchedProducts = mutableListOf<Product>()
        CoroutineScope(Dispatchers.Main).launch{
        val productsList = repositoryProduct.getAllProducts()
        for (itemProduct in productsList) {
            if (itemProduct.name!!.contains(searchQuery,true)) searchedProducts.add(itemProduct)
        }
        view?.viewProducts(searchedProducts)
        }
    }

    override fun attachView(view: IViewProducts) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }
}