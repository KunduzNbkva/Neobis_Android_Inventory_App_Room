package kg.kunduznbkva.inventoryapplication.presenter

import kg.kunduznbkva.inventoryapplication.model.Product


interface IShowProducts {
    fun showProducts(products: List<Product>)
}

interface IMainPresenter {
    fun insertProduct(product: Product)
    fun deleteProduct(product: Product)
    fun getAllProducts()
    fun updateProduct(product: Product)
}

interface IArchivePresenter {

}