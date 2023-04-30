package kg.kunduznbkva.inventoryapplication

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import kg.kunduznbkva.inventoryapplication.model.Product
import kg.kunduznbkva.inventoryapp.utils.loadImage
import kg.kunduznbkva.inventoryapplication.databinding.FragmentAddBinding


class AddFragment : Fragment() {
    private lateinit var binding: FragmentAddBinding
    private var product: Product? = null
    private var pos: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            checkData()
        } else {
            (activity as AppCompatActivity).supportActionBar?.title =
                getString(R.string.add_product)
        }
        initViews()
    }

    private fun checkData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            product =
                requireArguments().getSerializable(BUNDLE_PRODUCT_KEY, Product::class.java)
            setData(product!!)
        } else {
            product = arguments?.getSerializable(BUNDLE_PRODUCT_KEY) as Product
            setData(product!!)
        }
        binding.addBtn.text = getString(R.string.save)
        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.detail_about_product)
    }

    private fun initViews() {
        addProductClick()
        cancelClick()
    }

    private fun addProductClick() {
        binding.addBtn.setOnClickListener {
            val productName = binding.productNameEdit.text.toString()
            val productPrice = binding.productPriceEdit.text.toString()
            val productFabric = binding.productFabricEdit.text.toString()
            val productAmount = binding.productAmountEdit.text.toString()
            //val productImage = binding.

            val productNew = Product(product?.id,
                productName, productPrice.toDouble(),
                productFabric, productAmount.toInt(), null)


            if (product != null) {
                if (dataCheck(productNew)) {
                    App.db.productDao().updateProduct(productNew)
                    findNavController().navigate(R.id.action_navigation_detail_to_navigation_main)
                }
            } else {
                if (dataCheck(productNew)) {
                    App.db.productDao().insert(productNew)

                    findNavController().navigate(R.id.action_navigation_detail_to_navigation_main)
                }
            }
        }
    }


    private fun cancelClick() {
        binding.buttonCancel.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_detail_to_navigation_main)
        }
    }

    private fun setData(product: Product) {
        binding.productNameEdit.setText(product.name)
        binding.productFabricEdit.setText(product.fabric)
        binding.productPriceEdit.setText(product.price.toString())
        binding.productAmountEdit.setText(product.amount.toString())
        product.img?.let { binding.productImg.loadImage(it) }
    }

    private fun dataCheck(product: Product): Boolean {
        return if (product.name != null  && product.price != null &&
            product.fabric != null && product.amount != null) {
            true
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.fill_all_fields),
                Toast.LENGTH_SHORT
            ).show()
            false
        }
    }

    companion object {
        const val BUNDLE_PRODUCT_KEY = "product"
        const val BUNDLE_POSITION_KEY = "pos"
    }

}