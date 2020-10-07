package com.example.shoppingapplication.products.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapplication.R
import com.example.shoppingapplication.products.viewmodel.ProductsViewModel
import com.example.shoppingapplication.products.model.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.product_row_item.view.*
import javax.inject.Inject


/**Adapter class for the products list recycler view
 */
class ProductsAdapter @Inject constructor(private val productViewModel: ProductsViewModel):
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    private var products = emptyList<Product>()

    private var productMap: HashMap<Product, Int> = HashMap()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        fun bind(product: Product) {

            itemView.productName.text = product.name
            itemView.productPrice.text = "${product.price?.id.toString() + product.price?.name}"

            //TODO: filter?
            if (product.type.equals("chair")) {
                Log.d("Test", "chair - " + product.info?.material)
                itemView.productInfo.text = product.info?.material
            } else if (product.type.equals("couch")) {
                Log.d("Test", "couch - " + "${product.info?.numberOfSeats}")
                itemView.productInfo.text =
                    "${"Number of seats - " + (product.info?.numberOfSeats).toString()}"
            }


            Picasso.get().load(product.imageUrl).fit().centerInside().into(itemView.productImage)

            itemView.findViewById<ImageButton>(R.id.addToCart).setOnClickListener {
                if (productMap.containsKey(product)) {
                    productMap.put(product, productMap[product]!!.plus(1))
                } else {
                    productMap.put(product, 1)
                }


                productViewModel.setCartItems(productMap)
                productViewModel.setCartSize(productViewModel.calculateCartSize(productMap))

                Toast.makeText(itemView.context, "Item added to cart", Toast.LENGTH_LONG).show()
            }

            itemView.findViewById<ImageButton>(R.id.removeItem)
                .setOnClickListener {
                    if (productMap.containsKey(product) && productMap[product]!! >= 2) {
                        productMap.put(product, productMap[product]!!.minus(1))
                        Toast.makeText(
                            itemView.context,
                            "Item removed from cart",
                            Toast.LENGTH_LONG
                        ).show()

                        productViewModel.setCartItems(productMap)
                        productViewModel.setCartSize(productViewModel.calculateCartSize(productMap))
                    }else if(productMap.containsKey(product)  && productMap[product]!! == 1) {
                        productMap.remove(product)
                        productViewModel.setCartItems(productMap)
                        productViewModel.setCartSize(productViewModel.calculateCartSize(productMap))
                    }else{
                        Toast.makeText(
                            itemView.context,
                            "No item to remove",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.product_row_item, parent, false)

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


    fun setProductMap(cartMap: HashMap<Product,Int>){
        productMap = cartMap
        productViewModel.setCartItems(cartMap)
    }

}




