package com.example.electronics

import Product
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val products = ArrayList<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        products.add(Product("iPad", "iPad Pro 11-inch", 400.0, R.drawable.ipad_image))
        products.add(Product("MacBook M3 Pro", "12-core CPU\n18-core GPU", 2500.00, R.drawable.macbook_m3_pro_image))
        products.add(Product("Dell Inspiron", "13th Gen Intel® Core™ i7", 1499.00, R.drawable.dell_inspiron_image))
        products.add(Product("Logitech Keyboard", "Logitech - PRO X\nTKL LIGHTSPEED Wireless", 199.00, R.drawable.logitech_keyboard_image))
        products.add(Product("MacBook M3 Max", "14-core CPU\n30-core GPU", 3499.00, R.drawable.macbook_m3_max_image))

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ProductAdapter(products)

        val viewCartButton: Button = findViewById(R.id.viewCartButton)
        viewCartButton.setOnClickListener {
            showCart()
        }
    }

    private fun showCart() {
        val inflater = layoutInflater
        val layout: View = inflater.inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_container))

        val cartItems = products.filter { it.inCart }
        if (cartItems.isNotEmpty()) {
            val cartMessage = cartItems.joinToString("\n") { it.productName }
            val toast = Toast(this)
            toast.duration = Toast.LENGTH_SHORT
            toast.view = layout
            val toastText: TextView = layout.findViewById(R.id.toastText)
            toastText.text = "Items in Cart:\n$cartMessage"
            toast.show()
        } else {
            Toast.makeText(this, "Cart is empty", Toast.LENGTH_SHORT).show()
        }
    }


    inner class ProductAdapter(private val productList: List<Product>) :
        RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_product, parent, false)
            return ProductViewHolder(view)
        }

        override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
            holder.bind(productList[position])

            holder.itemView.setOnClickListener {
                val intent = Intent(this@MainActivity, ProductDetailsActivity::class.java)
                intent.putExtra("product", productList[position])
                startActivity(intent)
            }
        }

        override fun getItemCount(): Int {
            return productList.size
        }

        inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val productImageView: ImageView = itemView.findViewById(R.id.productImageView)
            private val productNameTextView: TextView = itemView.findViewById(R.id.productNameTextView)
            private val productDescriptionTextView: TextView =
                itemView.findViewById(R.id.productDescriptionTextView)
            private val productCostTextView: TextView =
                itemView.findViewById(R.id.productCostTextView)
            private val addButton: Button = itemView.findViewById(R.id.addButton)

            fun bind(product: Product) {
                productNameTextView.text = product.productName
                productDescriptionTextView.text = product.productDescription
                productCostTextView.text = "$${product.cost}"

                productImageView.setImageResource(product.imageResourceId)

                addButton.setOnClickListener {
                    product.inCart = true
                    Toast.makeText(
                        itemView.context,
                        "${product.productName} added to cart",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
