package com.example.shoppingapplication.cart.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapplication.R
import com.example.shoppingapplication.products.model.Product
import com.example.shoppingapplication.products.viewmodel.ProductsViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cart_list_item.view.*
import kotlinx.android.synthetic.main.product_row_item.view.productImage
import kotlinx.android.synthetic.main.product_row_item.view.productName
import kotlinx.android.synthetic.main.product_row_item.view.productPrice
import javax.inject.Inject


/**Adapter class for the cart list recycler view
 */
class ShoppingCartAdapter @Inject constructor(val productsViewModel: ProductsViewModel) :
    RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder>() {

    private var cartItems = HashMap<Product, Int>()
    private var keyList = emptyList<Product>()
    private var valueList = emptyList<Int>()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(product: Product, quantity: Int) {

            itemView.productName.text = product.name
            val priceText = product.price?.id.toString() + product.price?.name
            itemView.productPrice.text = priceText
            Picasso.get().load(product.imageUrl).fit().centerInside().into(itemView.productImage)


            productsViewModel.updateCartTotal(productsViewModel.calculateCartTotal(cartItems))

            itemView.findViewById<ImageButton>(R.id.addItem).setOnClickListener {
                Toast.makeText(
                    itemView.context,
                    itemView.context.getString(R.string.item_added),
                    Toast.LENGTH_LONG
                ).show()

                if (cartItems.containsKey(product)) {
                    cartItems[product] = cartItems[product]!!.plus(1)

                } else {
                    cartItems[product] = 1
                }

                updateCartItems(cartItems)
                productsViewModel.setCartItems(cartItems)
                productsViewModel.setCartSize(productsViewModel.calculateCartSize(cartItems))
                productsViewModel.updateCartTotal(productsViewModel.calculateCartTotal(cartItems))

            }

            itemView.findViewById<ImageButton>(R.id.deleteItem).setOnClickListener {

                if (cartItems.containsKey(product) && cartItems[product]!! >= 2) {
                    Toast.makeText(itemView.context, itemView.context.getString(R.string.item_reduced), Toast.LENGTH_LONG).show()
                    cartItems[product] = cartItems[product]!!.minus(1)
                } else {
                    cartItems.remove(product)
                    Toast.makeText(itemView.context, itemView.context.getString(R.string.product_removed), Toast.LENGTH_LONG).show()
                }

                updateCartItems(cartItems)

                productsViewModel.setCartItems(cartItems)
                productsViewModel.setCartSize(productsViewModel.calculateCartSize(cartItems))
                productsViewModel.updateCartTotal(productsViewModel.calculateCartTotal(cartItems))

            }

            itemView.findViewById<ImageButton>(R.id.removeItem)
                .setOnClickListener {
                    Toast.makeText(itemView.context, itemView.context.getString(R.string.product_removed), Toast.LENGTH_LONG).show()
                    cartItems.remove(product)
                    updateCartItems(cartItems)

                    productsViewModel.setCartItems(cartItems)
                    productsViewModel.setCartSize(productsViewModel.calculateCartSize(cartItems))
                    productsViewModel.updateCartTotal(productsViewModel.calculateCartTotal(cartItems))

                }
            itemView.productQuantity.text = quantity.toString()

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.cart_list_item, parent, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(keyList[position], valueList[position])
    }

    override fun getItemCount(): Int {
        return cartItems.size

    }

    fun updateCartItems(cartItems: HashMap<Product, Int>) {
        this.cartItems = cartItems
        keyList = ArrayList(cartItems.keys)
        valueList = ArrayList(cartItems.values)
        notifyDataSetChanged()
    }


}





