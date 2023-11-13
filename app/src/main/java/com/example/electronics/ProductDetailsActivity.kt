package com.example.electronics

import Product
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProductDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        val product: Product? = intent.getSerializableExtra("product") as? Product

        product?.let {
            val productImageViewDetails: ImageView = findViewById(R.id.productImageViewDetails)
            val productTitleTextViewDetails: TextView = findViewById(R.id.productTitleTextViewDetails)
            val productDescriptionTextViewDetails: TextView = findViewById(R.id.productDescriptionTextViewDetails)
            val productPriceTextViewDetails: TextView = findViewById(R.id.productPriceTextViewDetails)
            val homeButtonDetails: Button = findViewById(R.id.homeButtonDetails)

            productImageViewDetails.setImageResource(it.imageResourceId)
            productTitleTextViewDetails.text = it.productName
            productDescriptionTextViewDetails.text = it.productDescription
            productPriceTextViewDetails.text = "$${it.cost}"

            homeButtonDetails.setOnClickListener {
                finish()
            }
        } ?: run {
            finish()
        }
    }
}
