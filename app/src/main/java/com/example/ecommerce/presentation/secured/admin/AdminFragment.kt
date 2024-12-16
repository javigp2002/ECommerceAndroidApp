package com.example.ecommerce.presentation.secured.admin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.R
import com.example.ecommerce.adapters.ProductAdminAdapter
import com.example.ecommerce.dependency.AppContainerImpl
import com.example.ecommerce.domain.repository.model.Product
import com.example.ecommerce.presentation.admin.edit.AddProductActivity
import com.example.ecommerce.presentation.secured.admin.edit.EditProductActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AdminFragment : Fragment() {
    private lateinit var viewModel: AdminFragmentVm
    private val productAdapter = ProductAdminAdapter()
    private lateinit var recyclerView: RecyclerView
    private lateinit var textViewPrice: TextView
    private lateinit var editButton: ImageButton
    private lateinit var deleteButton: ImageButton
    private lateinit var addFloatingButton: FloatingActionButton
    private lateinit var logoutFloatingButton: FloatingActionButton


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.admin_list_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerViewProducts)
        textViewPrice = view.findViewById(R.id.textViewPrice)
        editButton = view.findViewById(R.id.addButton)
        deleteButton = view.findViewById(R.id.deleteButton)
        addFloatingButton = view.findViewById(R.id.addProductFloatingButton)
        logoutFloatingButton = view.findViewById(R.id.logoutFloatingButton)

        viewModel = AppContainerImpl.adminFragmentVM

        recyclerView.layoutManager = GridLayoutManager(this.context, 1)
        recyclerView.adapter = productAdapter
        subscribeToViewModel()

        productAdapter.onEditProductClicked = {
            val newIntent = Intent(activity, EditProductActivity::class.java)
            newIntent.putExtra("productName", it.name)
            newIntent.putExtra("productPrice", it.price)
            newIntent.putExtra("productId", it.id)
            startActivity(newIntent)
        }

        productAdapter.onDeleteProductClicked = {
            viewModel.deleteProduct(it)
        }

        addFloatingButton.setOnClickListener {
            val addProductIntent = Intent(activity, AddProductActivity::class.java)
            startActivity(addProductIntent)
        }

        logoutFloatingButton.setOnClickListener {
            viewModel.logout()
        }

    }

    private fun subscribeToViewModel() {
        val products = Observer { products: List<Product> ->
            recyclerView.adapter = productAdapter
            productAdapter.submitList(products.toList())
        }

        viewModel.products.observe(viewLifecycleOwner, products)
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateProducts()
    }

    companion object {
        fun newInstance(): Fragment {
            return AdminFragment()
        }
    }
}
