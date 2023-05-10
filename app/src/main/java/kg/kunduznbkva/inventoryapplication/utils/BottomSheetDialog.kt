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
import kg.kunduznbkva.inventoryapplication.presenter.IViewProducts
import kg.kunduznbkva.inventoryapplication.presenter.PresenterBottomSheet
import kg.kunduznbkva.inventoryapplication.presenter.PresenterMain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BottomSheetDialog(private val product: Product, private var archive: Boolean) :
    BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetBinding
    private lateinit var presenter: PresenterBottomSheet


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetBinding.inflate(inflater, container, false)
        presenter = PresenterBottomSheet(requireContext())
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
                    dismiss()
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
        presenter.archiveProduct(product)
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
                    dismiss()
                },
                "Hет",
                negativeButtonAction = {
                    dismiss()
                },
                cancelable = true
            )
        }
    }

    private fun restoreProduct(product: Product) {
        presenter.restoreProduct(product)
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
                    dismiss()
                },
                "нет",
                negativeButtonAction = {
                    dismiss()
                },
                cancelable = true
            )
        }
    }

    private fun deleteProduct(product: Product) {
        presenter.deleteProduct(product)
        Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show()
    }

}