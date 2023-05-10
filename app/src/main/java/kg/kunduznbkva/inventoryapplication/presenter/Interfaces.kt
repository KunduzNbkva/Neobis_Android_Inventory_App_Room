package kg.kunduznbkva.inventoryapplication.presenter

import kg.kunduznbkva.inventoryapplication.model.Product


interface IViewProducts {
    fun viewProducts(products: List<Product>)
}

interface IMainPresenter {
    fun insertProduct(product: Product)
    fun deleteProduct(product: Product)
    fun getAllProducts()
    fun updateProduct(product: Product)
    fun attachView(view: IViewProducts)
    fun detachView()
}

interface IBottomSheetPresenter{
    fun deleteProduct(product: Product)
    fun restoreProduct(product: Product)
    fun archiveProduct(product: Product)
}




