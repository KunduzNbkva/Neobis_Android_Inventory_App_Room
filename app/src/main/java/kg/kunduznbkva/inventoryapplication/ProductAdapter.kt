package kg.kunduznbkva.inventoryapplication


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.kunduznbkva.inventoryapplication.model.Product
import kg.kunduznbkva.inventoryapplication.utils.loadImage
import kg.kunduznbkva.inventoryapplication.databinding.RecyclerItemBinding


class ProductAdapter(val onLongItemClick: OnLongItemClick,
                     val itemClickListener: OnItemClickListener): RecyclerView.Adapter<ProductAdapter.ProductHolder>() {

    private var productsList = mutableListOf<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        return ProductHolder(RecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent,false))
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        holder.bind(productsList[position])
    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    fun editTask(taskModel: Product, position: Int){
        productsList[position] = taskModel
        notifyItemChanged(position)
    }


    fun getItem(position: Int): Product {
        return  productsList[position]
    }

    fun removeItem(position: Int){
        productsList.removeAt(position)
        notifyItemRemoved(position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addAllTasksRoom(products: MutableList<Product>) {
        productsList = products
        notifyDataSetChanged()
    }


    inner class ProductHolder(private var binding: RecyclerItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(productModel: Product) {
            binding.itemName.text = productModel.name
            binding.itemPrice.text = productModel.price.toString()
            binding.itemAmount.text = productModel.amount.toString()
            binding.itemFabric.text = productModel.fabric
            productModel.img?.let { binding.itemImg.loadImage(it)}


            binding.root.setOnLongClickListener {
                onLongItemClick.longClick(adapterPosition,productModel)
                true
            }

            binding.root.setOnClickListener {
                itemClickListener.onItemClick(productModel,adapterPosition)
            }
        }
    }

}

interface OnItemClickListener {
    fun onItemClick( productModel: Product,position: Int)
}

interface  OnLongItemClick{
    fun longClick(position: Int,productModel: Product)
}


