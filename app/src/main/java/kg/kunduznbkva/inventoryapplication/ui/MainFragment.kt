package kg.kunduznbkva.inventoryapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import kg.kunduznbkva.inventoryapplication.R
import kg.kunduznbkva.inventoryapplication.ui.AddFragment.Companion.BUNDLE_POSITION_KEY
import kg.kunduznbkva.inventoryapplication.model.Product
import kg.kunduznbkva.inventoryapplication.ui.AddFragment.Companion.BUNDLE_PRODUCT_KEY
import kg.kunduznbkva.inventoryapplication.adapters.OnItemClickListener
import kg.kunduznbkva.inventoryapplication.adapters.OnMenuItemClick
import kg.kunduznbkva.inventoryapplication.adapters.ProductAdapter
import kg.kunduznbkva.inventoryapplication.databinding.FragmentMainBinding
import kg.kunduznbkva.inventoryapplication.presenter.IViewProducts
import kg.kunduznbkva.inventoryapplication.presenter.PresenterMain
import kg.kunduznbkva.inventoryapplication.utils.BottomSheetDialog

class MainFragment : Fragment(), OnMenuItemClick, OnItemClickListener,IViewProducts {
    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: ProductAdapter
    private lateinit var presenter: PresenterMain

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = ProductAdapter(this, this)
        presenter = PresenterMain(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        initRecycler()
        initSearchView()
        getProductsFromLocalDB()
        floatingBtnClick()
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

    private fun initRecycler() {
        binding.mainRecycler.adapter = adapter
    }

    private fun floatingBtnClick() {
        binding.addButton.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.navigation_detail)
        }
    }

    private fun getProductsFromLocalDB() {
        presenter.attachView(this)
        presenter.getAllProducts()
    }

    override fun onItemClick(productModel: Product, position: Int) {
        if (productModel.id != null) {
            findNavController().navigate(
                R.id.action_navigation_main_to_navigation_detail, bundleOf(
                    BUNDLE_PRODUCT_KEY to productModel, BUNDLE_POSITION_KEY to position
                )
            )
        }
    }

    override fun menuClick(position: Int, productModel: Product) {
        createBottomSheet(productModel)
    }

    private fun createBottomSheet(product: Product){
        val dialog = BottomSheetDialog(product,false)
        dialog.show(parentFragmentManager,getString(R.string.modal_bottom_sheet))
    }

    override fun viewProducts(products: List<Product>) {
        adapter.updateProduct(products)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}