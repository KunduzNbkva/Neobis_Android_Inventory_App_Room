package kg.kunduznbkva.inventoryapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey(autoGenerate = true)
    var id : Long? = null,
    var name: String? = null,
    var price: Double? = null,
    var fabric : String? = null,
    var amount: Int? = null,
    var img: Int? = null,
    var archived: Boolean = false
):java.io.Serializable
