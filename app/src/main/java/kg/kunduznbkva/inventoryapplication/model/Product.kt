package kg.kunduznbkva.inventoryapplication.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id : Long? = null,
    val name: String? = null,
    val price: Double? = null,
    val fabric : String? = null,
    val amount: Int? = null,
    val img: Bitmap? = null,
    var archived: Boolean = false
):java.io.Serializable
