package kg.kunduznbkva.inventoryapplication.presenter

import android.content.Context
import kg.kunduznbkva.inventoryapplication.database.local.ProductDatabase
import kg.kunduznbkva.inventoryapplication.database.local.RepositoryProduct
import kg.kunduznbkva.inventoryapplication.model.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PresenterBottomSheet(
    context: Context
): IBottomSheetPresenter {
    private val repositoryProduct: RepositoryProduct
    private var view: IViewProducts? = null

    init {
        val db = ProductDatabase.getInstance(context)?.productDao()
        repositoryProduct = db?.let { RepositoryProduct(it) }!!
    }


    override fun deleteProduct(product: Product) {
        CoroutineScope(Dispatchers.IO).launch {
            repositoryProduct.deleteProduct(product)
        }
    }

    override fun restoreProduct(product: Product) {
        product.archived = false
        CoroutineScope(Dispatchers.IO).launch {
            repositoryProduct.updateProduct(product)
        }
    }

    override fun archiveProduct(product: Product) {
        product.archived = true
        CoroutineScope(Dispatchers.IO).launch {
            repositoryProduct.updateProduct(product)
        }
    }


}