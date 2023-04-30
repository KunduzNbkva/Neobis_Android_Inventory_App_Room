package kg.kunduznbkva.inventoryapplication.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kg.kunduznbkva.inventoryapplication.App
import kg.kunduznbkva.inventoryapplication.databinding.BottomSheetBinding
import kg.kunduznbkva.inventoryapplication.model.Product

class BottomSheetDialog(private val product: Product): BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        btnArchive(product)
        btnRestore(product)
        btnDelete(product)
    }
    private fun btnArchive(product: Product){
        binding.bottomSheetArchive.setOnClickListener {
            App.db.productDao().insert(product)
            Toast.makeText(requireContext(),"Archieved",Toast.LENGTH_SHORT).show()
        }
    }
    private fun btnRestore(product: Product) {
        binding.bottomSheetRestore.setOnClickListener {
            App.db.productDao().insert(product)
            Toast.makeText(requireContext(),"Restored",Toast.LENGTH_SHORT).show()
        }
    }
    private fun btnDelete(product: Product) {
        binding.bottomSheetDelete.setOnClickListener {
            App.db.productDao().deleteProduct(product)
            Toast.makeText(requireContext(),"Deleted",Toast.LENGTH_SHORT).show()
        }
    }
}