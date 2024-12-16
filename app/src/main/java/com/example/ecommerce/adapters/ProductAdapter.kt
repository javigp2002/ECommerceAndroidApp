package com.example.ecommerce.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.R
import com.example.ecommerce.domain.repository.model.Product
import com.example.ecommerce.utils.MyNumberFormat

class ProductAdapter :
    ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {

    private val onCartName = "Remove from cart"
    private val notOnCartName = "Add to cart"


    var onProductClicked: (Product) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.product_item_v2, parent, false)
        return ProductViewHolder(view)
    }


    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = currentList[position]
        holder.textViewName.text = product.name
        holder.textViewPrice.text = MyNumberFormat.doubleToStringEuros(product.price)
        holder.buttonAddProduct.setOnClickListener {
            onProductClicked(product)
        }

        if (product.onCart) {
            holder.buttonAddProduct.setImageResource(R.drawable.remove_24px)
        } else {
            holder.buttonAddProduct.setImageResource(R.drawable.add_shopping_cart_24px)
        }
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        val textViewPrice: TextView = itemView.findViewById(R.id.textViewPrice)
        val buttonAddProduct: ImageButton = itemView.findViewById(R.id.buttonAddProduct)
    }

    class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}
