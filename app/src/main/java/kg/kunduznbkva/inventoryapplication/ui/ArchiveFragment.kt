package kg.kunduznbkva.inventoryapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kg.kunduznbkva.inventoryapplication.R
import kg.kunduznbkva.inventoryapplication.adapters.OnItemClickListener
import kg.kunduznbkva.inventoryapplication.adapters.OnMenuItemClick
import kg.kunduznbkva.inventoryapplication.adapters.ProductAdapter
import kg.kunduznbkva.inventoryapplication.model.Product
import kg.kunduznbkva.inventoryapplication.databinding.FragmentArchiveBinding
import kg.kunduznbkva.inventoryapplication.presenter.IShowProducts
import kg.kunduznbkva.inventoryapplication.presenter.PresenterArchived
import kg.kunduznbkva.inventoryapplication.utils.BottomSheetDialog

class ArchiveFragment : Fragment(), OnItemClickListener, OnMenuItemClick, IShowProducts {
    private lateinit var binding: FragmentArchiveBinding
    private lateinit var adapter: ProductAdapter
    private lateinit var presenter: PresenterArchived

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = ProductAdapter(this, this)
        presenter = PresenterArchived(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArchiveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        getArchiveProductsFromLocalDB()
    }

    private fun initRecycler() {
        binding.mainRecycler.adapter = adapter
    }

    private fun getArchiveProductsFromLocalDB() {
        presenter.getAllProducts()
    }

    override fun menuClick(position: Int, productModel: Product) {
        createBottomSheet(productModel)
    }

    private fun createBottomSheet(product: Product) {
        val dialog = BottomSheetDialog(product, true)
        dialog.show(parentFragmentManager, getString(R.string.modal_bottom_sheet))
    }

    override fun onItemClick(productModel: Product, position: Int) {
        if (productModel.id != null) {
            findNavController().navigate(
                R.id.action_navigation_main_to_navigation_detail, bundleOf(
                    AddFragment.BUNDLE_PRODUCT_KEY to productModel, AddFragment.BUNDLE_POSITION_KEY to position
                )
            )
        }
    }

    override fun showProducts(products: List<Product>) {
        adapter.updateProduct(products)
    }
}