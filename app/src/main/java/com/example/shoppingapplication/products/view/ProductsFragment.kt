package com.example.shoppingapplication.products.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingapplication.MainActivity
import com.example.shoppingapplication.MyApplication
import com.example.shoppingapplication.R
import com.example.shoppingapplication.products.model.Product
import com.example.shoppingapplication.products.viewmodel.ProductsViewModel
import com.example.shoppingapplication.products.viewmodel.ProductsViewModelProvider
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_products.view.*

class ProductsFragment : Fragment() {

    @Inject
    lateinit var productsViewModelProvider: ProductsViewModelProvider
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var productsViewModel: ProductsViewModel

    private var cartMap: HashMap<Product, Int> = HashMap()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentView = inflater.inflate(R.layout.fragment_products, container, false)
        setupUI(fragmentView)
        subscribeUI(fragmentView)
        return fragmentView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ((activity?.application) as MyApplication).appComponent.inject(this)
    }

    private fun subscribeUI(view: View) {
        productsViewModel.getLoadingStatus().observe(activity as MainActivity,
            {
                if (it) {
                    view.progressBar.visibility = View.VISIBLE
                } else {
                    view.progressBar.visibility = View.GONE
                }
            })

        productsViewModel.getProductsResponse().observe(activity as MainActivity,
            { t ->
                if (!t.products.isNullOrEmpty()) {
                    view.swipeRefreshLayout.isRefreshing = false
                    view.errorText.visibility = View.GONE
                    productsAdapter.setProductsList(t.products)
                } else {
                    view.errorText.visibility = View.VISIBLE
                }
            })

        productsViewModel.getCartItems().observe(activity as MainActivity,
            { t ->
                cartMap = t
                if (t.isEmpty()) {
                    view.cartSize?.text = "0"
                } else {
                    view.cartSize?.text = productsViewModel.calculateCartSize(t).toString()
                    productsAdapter.setProductMap(t)
                }
            })
    }

    /**Setup UI elements
     */
    private fun setupUI(view: View) {
        productsViewModel =
            ViewModelProvider(this, productsViewModelProvider)
                .get(ProductsViewModel::class.java)

        val cartItemsMap = productsViewModel.getCartItems()
        if (!cartItemsMap.value.isNullOrEmpty()) {
            cartMap = cartItemsMap.value!!
        }

        productsAdapter = ProductsAdapter(productsViewModel)

        view.productsRecyclerview.adapter = productsAdapter

        val layoutManager = LinearLayoutManager(activity)

        view.productsRecyclerview.layoutManager = layoutManager

        view.productsRecyclerview.itemAnimator = DefaultItemAnimator()

        val dividerItemDecoration = DividerItemDecoration(
            view.productsRecyclerview.context,
            layoutManager.orientation
        )
        view.productsRecyclerview.addItemDecoration(dividerItemDecoration)

        view.swipeRefreshLayout.setOnRefreshListener {
            view.swipeRefreshLayout.isRefreshing = false
            productsViewModel.fetchProducts()
        }

        view.showCart.setOnClickListener {
            productsAdapter.setProductMap(cartMap)
            (activity as MainActivity).onCartIconClicked(cartMap)
        }
    }
}
