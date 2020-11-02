package com.example.shoppingapplication.cart.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
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
import kotlinx.android.synthetic.main.fragment_shopping_cart.view.*

class ShoppingCartFragment : Fragment() {

    private lateinit var shoppingCartAdapter: ShoppingCartAdapter
    private lateinit var fragmentView: View
    private lateinit var cartItems: HashMap<Product, Int>

    @Inject
    lateinit var productsViewModelProvider: ProductsViewModelProvider
    private lateinit var productsViewModel: ProductsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentView = inflater.inflate(R.layout.fragment_shopping_cart, container, false)
        setupUI(fragmentView)
        subscribeUI(fragmentView)
        return fragmentView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ((activity?.application) as MyApplication).appComponent.inject(this)
    }

    /**Setup UI elements
     */
    private fun setupUI(view: View) {
        val toolbar = view.toolbar as Toolbar
        toolbar.setNavigationOnClickListener { activity!!.onBackPressed() }

        productsViewModel = ViewModelProvider(this, productsViewModelProvider).get(ProductsViewModel::class.java)

        shoppingCartAdapter = ShoppingCartAdapter(productsViewModel)
        view.shoppingCartRecyclerView.adapter = shoppingCartAdapter

        val bundle = this.arguments
        cartItems = bundle?.getSerializable("cartItems") as HashMap<Product, Int>

        shoppingCartAdapter.updateCartItems(cartItems)

        val layoutManager = LinearLayoutManager(activity)

        view.shoppingCartRecyclerView.layoutManager = layoutManager
        view.shoppingCartRecyclerView.itemAnimator = DefaultItemAnimator()

        val dividerItemDecoration = DividerItemDecoration(view.shoppingCartRecyclerView.context, layoutManager.orientation)

        view.shoppingCartRecyclerView.addItemDecoration(dividerItemDecoration)

        if (cartItems.isNullOrEmpty()) {
            view.emptyTextView.visibility = View.VISIBLE
            view.shoppingCartRecyclerView.visibility = View.GONE
        } else {
            view.emptyTextView.visibility = View.GONE
            view.shoppingCartRecyclerView.visibility = View.VISIBLE
        }
    }

    private fun subscribeUI(view: View) {
        productsViewModel.getCartTotal().observe(activity as MainActivity,
            { t ->
                val priceText = t.toString() + getString(R.string.kr_currency)
                view.totalPrice.text = priceText
            })

        productsViewModel.getCartItems().observe(activity as MainActivity,
            { t ->

                val priceText = t.toString() + getString(R.string.kr_currency)
                view.totalPrice.text = priceText

                cartItems = t
                shoppingCartAdapter.updateCartItems(t)
            })
    }
}
