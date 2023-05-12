package kg.kunduznbkva.inventoryapplication.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kg.kunduznbkva.inventoryapplication.R
import kg.kunduznbkva.inventoryapplication.databinding.BottomSheetBinding
import kg.kunduznbkva.inventoryapplication.model.Product
import kg.kunduznbkva.inventoryapplication.presenter.PresenterBottomSheet

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
            createAlert("Архивировать ${product.name} из каталога?") { product ->
                archiveProduct(product)
            }
        }
    }

    private fun archiveProduct(product: Product) {
        presenter.archiveProduct(product)
        Toast.makeText(requireContext(), "Archieved", Toast.LENGTH_SHORT).show()
    }

    private fun btnRestore(product: Product) {
        binding.bottomSheetRestore.setOnClickListener {
            createAlert("Восстановить ${product.name} из каталога?") { product ->
                restoreProduct(product)
            }
        }
    }

    private fun restoreProduct(product: Product) {
        presenter.restoreProduct(product)
        Toast.makeText(requireContext(), "Restored", Toast.LENGTH_SHORT).show()
    }

    private fun btnDelete(product: Product) {
        binding.bottomSheetDelete.setOnClickListener {
            createAlert("Удалить ${product.name} из каталога?") { product ->
                deleteProduct(product)
            }
        }
    }

    private fun deleteProduct(product: Product) {
        presenter.deleteProduct(product)
        Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show()
    }

    private fun createAlert(s: String, method: (Product) -> Unit) {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_custom, null)
        builder.setView(dialogView)

        val dialog = builder.create()
        val window = dialog.window
        window?.setBackgroundDrawableResource(R.drawable.alert_bg)

        val dialogTitle = dialogView.findViewById<TextView>(R.id.dialog_title)
        dialogTitle.text = s

        val dialogConfirmButton = dialogView.findViewById<TextView>(R.id.dialog_yes)
        dialogConfirmButton.setOnClickListener {
            method(product)
            dialog.dismiss()
            dismiss()
        }

        val dialogCancelButton = dialogView.findViewById<TextView>(R.id.dialog_no)
        dialogCancelButton.setOnClickListener {
            dialog.dismiss()
            dismiss()
        }
        dialog.show()
    }

}