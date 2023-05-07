package kg.kunduznbkva.inventoryapplication.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kg.kunduznbkva.inventoryapplication.database.local.ProductDatabase
import kg.kunduznbkva.inventoryapplication.databinding.BottomSheetBinding
import kg.kunduznbkva.inventoryapplication.model.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BottomSheetDialog(private val product: Product, private var archive: Boolean) :
    BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        if (archive) {
            binding.bottomSheetArchive.visibility = View.GONE
            binding.bottomSheetRestore.visibility = View.VISIBLE
            binding.bottomSheetDelete.visibility = View.VISIBLE
            btnRestore(product)
            btnDelete(product)
        } else {
            binding.bottomSheetArchive.visibility = View.VISIBLE
            binding.bottomSheetRestore.visibility = View.GONE
            binding.bottomSheetDelete.visibility = View.GONE
            btnArchive(product)
        }

    }

    private fun btnArchive(product: Product) {
        binding.bottomSheetArchive.setOnClickListener {
            requireContext().showAlertDialog(
                "Архивирование",
                "Архивировать ${product.name} из каталога?",
                "Да",
                positiveButtonAction = {
                    archiveProduct(product)
                },
                "нет",
                negativeButtonAction = {
                    dismiss()
                },
                cancelable = true
            )
        }
    }

    private fun archiveProduct(product: Product) {
        product.archived = true
        CoroutineScope(Dispatchers.IO).launch {
            val db = ProductDatabase.getInstance(requireContext())
            db?.productDao()?.insert(product)
        }
        Toast.makeText(requireContext(), "Archieved", Toast.LENGTH_SHORT).show()
    }

    private fun btnRestore(product: Product) {
        binding.bottomSheetRestore.setOnClickListener {
            requireContext().showAlertDialog(
                "Восстановление",
                "Восстановить ${product.name} из каталога?",
                "Да",
                positiveButtonAction = {
                    restoreProduct(product)
                },
                "нет",
                negativeButtonAction = {
                    dismiss()
                },
                cancelable = true
            )
        }
    }

    private fun restoreProduct(product:Product){
        product.archived = false
        CoroutineScope(Dispatchers.IO).launch {
            val db = ProductDatabase.getInstance(requireContext())
            db?.productDao()?.insert(product)
        }
        Toast.makeText(requireContext(), "Restored", Toast.LENGTH_SHORT).show()
    }

    private fun btnDelete(product: Product) {
        binding.bottomSheetDelete.setOnClickListener {
            requireContext().showAlertDialog(
                "Удаление",
                "Удалить ${product.name} из каталога?",
                "Да",
                positiveButtonAction = {
                    deleteProduct(product)
                },
                "нет",
                negativeButtonAction = {
                    dismiss()
                },
                cancelable = true
            )
        }
    }

    private fun deleteProduct(product: Product){
        CoroutineScope(Dispatchers.IO).launch {
            val db = ProductDatabase.getInstance(requireContext())
            db?.productDao()?.deleteProduct(product)
        }
        Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show()
    }
}