package kg.kunduznbkva.inventoryapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import kg.kunduznbkva.inventoryapplication.AddFragment.Companion.BUNDLE_POSITION_KEY
import kg.kunduznbkva.inventoryapplication.model.Product
import kg.kunduznbkva.inventoryapplication.AddFragment.Companion.BUNDLE_PRODUCT_KEY
import kg.kunduznbkva.inventoryapplication.databinding.FragmentMainBinding
import kg.kunduznbkva.inventoryapplication.utils.BottomSheetDialog

class MainFragment : Fragment(), OnLongItemClick, OnItemClickListener {

    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = ProductAdapter(this, this)
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
        initRecycler()
        initViews()

    }

    private fun initViews() {
        floatingBtnClick()
        getProductsFromLocalDB()
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
        val listAllTasks = App.db.productDao().getAllProducts()
        adapter.addAllTasksRoom(listAllTasks)
    }

    override fun longClick(position: Int,productModel: Product) {
       createBottomSheet(productModel)
    }

    private fun createBottomSheet(product: Product){
        val dialog = BottomSheetDialog(product)
        dialog.show(parentFragmentManager,"ModalBottomSheet")
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
}