package com.example.shoppingapplication.products.view

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
import javax.inject.Inject
import kotlinx.android.synthetic.main.product_row_item.view.*

/**Adapter class for the products list recycler view
 */
class ProductsAdapter @Inject constructor(private val productViewModel: ProductsViewModel) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    private var products = emptyList<Product>()
    private var productMap: HashMap<Product, Int> = HashMap()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(product: Product) {
            itemView.productName.text = product.name
            val priceText = product.price?.id.toString() + product.price?.name
            itemView.productPrice.text = priceText
            // TODO: search filter?
            if (product.type.equals("chair")) {
                itemView.productInfo.text = product.info?.material
            } else if (product.type.equals("couch")) {
                val numberOfSeatsText = itemView.context.getString(R.string.number_of_seats) + (product.info?.numberOfSeats).toString()
                itemView.productInfo.text = numberOfSeatsText
            }

            Picasso.get().load(product.imageUrl).fit().centerInside().into(itemView.productImage)
            itemView.findViewById<ImageButton>(R.id.addToCart).setOnClickListener {
                if (productMap.containsKey(product)) {
                    productMap[product] = productMap[product]!!.plus(1)
                } else {
                    productMap[product] = 1
                }
                productViewModel.setCartItems(productMap)
                productViewModel.setCartSize(productViewModel.calculateCartSize(productMap))
                Toast.makeText(itemView.context, itemView.context.getString(R.string.item_added_cart), Toast.LENGTH_LONG).show()
            }

            itemView.findViewById<ImageButton>(R.id.removeItem)
                .setOnClickListener {
                    if (productMap.containsKey(product) && productMap[product]!! >= 2) {
                        productMap[product] = productMap[product]!!.minus(1)
                        Toast.makeText(itemView.context, itemView.context.getString(R.string.item_removed_cart), Toast.LENGTH_LONG).show()
                        productViewModel.setCartItems(productMap)
                        productViewModel.setCartSize(productViewModel.calculateCartSize(productMap))
                    } else if (productMap.containsKey(product) && productMap[product]!! == 1) {
                        productMap.remove(product)
                        productViewModel.setCartItems(productMap)
                        productViewModel.setCartSize(productViewModel.calculateCartSize(productMap))
                    } else {
                        Toast.makeText(itemView.context, itemView.context.getString(R.string.no_item_to_remove), Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_row_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product: Product = products[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun setProductsList(productList: List<Product>) {
        products = productList
        notifyDataSetChanged()
    }

    fun setProductMap(cartMap: HashMap<Product, Int>) {
        productMap = cartMap
        productViewModel.setCartItems(cartMap)
    }
}
