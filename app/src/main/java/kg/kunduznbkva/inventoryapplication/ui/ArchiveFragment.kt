package kg.kunduznbkva.inventoryapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kg.kunduznbkva.inventoryapplication.R
import kg.kunduznbkva.inventoryapplication.adapters.OnItemClickListener
import kg.kunduznbkva.inventoryapplication.adapters.OnMenuItemClick
import kg.kunduznbkva.inventoryapplication.adapters.ProductAdapter
import kg.kunduznbkva.inventoryapplication.model.Product
import kg.kunduznbkva.inventoryapplication.databinding.FragmentArchiveBinding
import kg.kunduznbkva.inventoryapplication.presenter.IViewProducts
import kg.kunduznbkva.inventoryapplication.presenter.PresenterArchived
import kg.kunduznbkva.inventoryapplication.utils.BottomSheetDialog

class ArchiveFragment : Fragment(), OnItemClickListener, OnMenuItemClick, IViewProducts {
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
        initSearchView()
        getArchiveProductsFromLocalDB()
    }

    private fun initRecycler() {
        binding.mainRecycler.adapter = adapter
    }

    private fun initSearchView(){
        binding.searchViewMain.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query != null) presenter.searchProduct(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { presenter.searchProduct(it) }
                return false
            }
        })
    }

    private fun getArchiveProductsFromLocalDB() {
        presenter.attachView(this)
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

    override fun viewProducts(products: List<Product>) {
        adapter.updateProduct(products)
    }

    override fun onResume() {
        super.onResume()
        presenter.getAllProducts()
    }

}