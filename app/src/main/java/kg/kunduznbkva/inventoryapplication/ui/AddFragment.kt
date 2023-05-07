package kg.kunduznbkva.inventoryapplication.ui

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import kg.kunduznbkva.inventoryapplication.R
import kg.kunduznbkva.inventoryapplication.model.Product
import kg.kunduznbkva.inventoryapplication.databinding.FragmentAddBinding
import kg.kunduznbkva.inventoryapplication.presenter.PresenterMain
import kg.kunduznbkva.inventoryapplication.utils.loadImage

class AddFragment : Fragment() {
    private lateinit var binding: FragmentAddBinding
    private var product: Product? = null
    private var imageUri: Uri? = null
    private lateinit var presenter: PresenterMain
    private val selectImageFromGalleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                binding.productImg.setImageURI(uri)
                imageUri = uri
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        presenter = PresenterMain(requireContext())
        checkArguments()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun checkArguments() {
        if (arguments != null) {
            checkData()
        } else {
            (activity as AppCompatActivity).supportActionBar?.title =
                getString(R.string.add_product)
        }
    }

    @Suppress("DEPRECATION")
    private fun checkData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            product = requireArguments().getSerializable(BUNDLE_PRODUCT_KEY, Product::class.java)
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
        binding.productImg.setOnClickListener { selectImageFromGalleryResult.launch("image/*") }
    }

    private fun addProductClick() {
        val productName = binding.productNameEdit.text.toString()
        val productPrice = binding.productPriceEdit.text.toString()
        val productFabric = binding.productFabricEdit.text.toString()
        val productAmount = binding.productAmountEdit.text.toString()

        val productNew = Product(
            product?.id,
            productName,
            productPrice.toDouble(),
            productFabric,
            productAmount.toInt(),
            convertUriToBitmap()
        )

        binding.addBtn.setOnClickListener {
            if (product != null && dataCheck(productNew)) {
                presenter.updateProduct(productNew)
                findNavController().navigate(R.id.action_navigation_detail_to_navigation_main)
            } else if (dataCheck(productNew)) {
                presenter.insertProduct(productNew)
                findNavController().navigate(R.id.action_navigation_detail_to_navigation_main)
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun convertUriToBitmap(): Bitmap {
        val bitmap: Bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val src = imageUri?.let { ImageDecoder.createSource(context?.contentResolver!!, it) }
            ImageDecoder.decodeBitmap(src!!)
        } else MediaStore.Images.Media.getBitmap(context?.contentResolver, imageUri)
        return bitmap
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
        return if (product.name != null && product.price != null &&
            product.fabric != null && product.amount != null
        ) {
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