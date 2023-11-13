import java.io.Serializable

data class Product(
    val productName: String,
    val productDescription: String,
    val cost: Double,
    val imageResourceId: Int,
    var inCart: Boolean = false
) : Serializable