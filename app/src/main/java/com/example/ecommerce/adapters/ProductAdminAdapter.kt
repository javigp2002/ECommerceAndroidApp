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

class ProductAdminAdapter :
    ListAdapter<Product, ProductAdminAdapter.ProductViewHolder>(ProductDiffCallback()) {

    var onEditProductClicked: (Product) -> Unit = {}
    var onDeleteProductClicked: (Product) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.product_item_admin_list, parent, false)
        return ProductViewHolder(view)
    }


    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = currentList[position]
        holder.textViewName.text = product.name
        holder.textViewPrice.text = MyNumberFormat.doubleToStringEuros(product.price)
        holder.editProductButton.setOnClickListener {
            onEditProductClicked(product)
        }

        holder.deleteProductButton.setOnClickListener {
            onDeleteProductClicked(product)
        }
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        val textViewPrice: TextView = itemView.findViewById(R.id.textViewPrice)
        val editProductButton: ImageButton = itemView.findViewById(R.id.addButton)
        val deleteProductButton: ImageButton = itemView.findViewById(R.id.deleteButton)
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
