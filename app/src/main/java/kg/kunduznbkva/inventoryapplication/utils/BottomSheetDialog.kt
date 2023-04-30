package kg.kunduznbkva.inventoryapplication.utils

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kg.kunduznbkva.inventoryapplication.App
import kg.kunduznbkva.inventoryapplication.databinding.BottomSheetBinding
import kg.kunduznbkva.inventoryapplication.model.Product

class BottomSheetDialog(private val product: Product, private var archive: Boolean): BottomSheetDialogFragment() {
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
        if (archive){
            binding.bottomSheetArchive.visibility = View.GONE
            binding.bottomSheetRestore.visibility = View.VISIBLE
            binding.bottomSheetDelete.visibility = View.VISIBLE
            btnRestore(product)
            btnDelete(product)
        } else{
            binding.bottomSheetArchive.visibility = View.VISIBLE
            binding.bottomSheetRestore.visibility = View.GONE
            binding.bottomSheetDelete.visibility = View.GONE
            btnArchive(product)
        }

    }
    private fun btnArchive(product: Product){
        binding.bottomSheetArchive.setOnClickListener {
            product.archived = true
            App.db.productDao().insert(product)
            Toast.makeText(requireContext(),"Archieved",Toast.LENGTH_SHORT).show()
        }
    }
    private fun btnRestore(product: Product) {
        binding.bottomSheetRestore.setOnClickListener {
            product.archived = false
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