package kg.kunduznbkva.inventoryapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kg.kunduznbkva.inventoryapplication.model.Product
import kg.kunduznbkva.inventoryapplication.utils.loadImage
import kg.kunduznbkva.inventoryapplication.databinding.RecyclerItemBinding
import kg.kunduznbkva.inventoryapplication.utils.ProductsDiffUtils

class ProductAdapter(
    val onMenuItemClick: OnMenuItemClick,
    val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<ProductAdapter.ProductHolder>() {

    private var productsList = listOf<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        return ProductHolder(
            RecyclerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        holder.bind(productsList[position])
    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    fun updateProduct(products: List<Product>) {
        val diffResult = DiffUtil.calculateDiff(ProductsDiffUtils(productsList, products))
        productsList = products
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ProductHolder(private var binding: RecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(productModel: Product) {
            binding.itemName.text = productModel.name
            binding.itemPrice.text = productModel.price.toString()
            binding.itemAmount.text = productModel.amount.toString()
            binding.itemFabric.text = productModel.fabric
            productModel.img?.let { binding.itemImg.loadImage(it) }

            binding.itemMenu.setOnClickListener {
                onMenuItemClick.menuClick(
                    adapterPosition,
                    productModel
                )
            }

            binding.root.setOnClickListener {
                itemClickListener.onItemClick(productModel, adapterPosition)
            }
        }
    }

}

interface OnItemClickListener {
    fun onItemClick(productModel: Product, position: Int)
}

interface OnMenuItemClick {
    fun menuClick(position: Int, productModel: Product)
}


