package com.example.shoppingapplication.products.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapplication.products.model.Product
import com.example.shoppingapplication.products.model.Products
import com.example.shoppingapplication.products.model.ProductsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**View model class
 */
class ProductsViewModel(private val productsRepository: ProductsRepository) : ViewModel() {


    private var productsLiveData: MutableLiveData<Products> = MutableLiveData()

    private var isLoading: MutableLiveData<Boolean> = MutableLiveData()

    private var cartSize: MutableLiveData<Int> = MutableLiveData()

    private var cartMap: MutableLiveData<HashMap<Product, Int>> = MutableLiveData()

    private var cartTotal: MutableLiveData<Double> = MutableLiveData()

    /**load products from the file
     */
    init {
        fetchProducts()
    }


    fun fetchProducts() {
        viewModelScope.launch(Dispatchers.Default) {
            setIsLoading(true)
            setProductsResponse(productsRepository.parseJSON())
        }
    }

    /**Set loading status
     * @param loading flag
     */
    private fun setIsLoading(loading: Boolean) {
        isLoading.postValue(loading)
    }

    /**Set products response
     * @param products
     */
    private fun setProductsResponse(products: Products?) {
        setIsLoading(false)

        if (products != null) {
            productsLiveData.postValue(products)
        }
    }

    /**Get the products response
     * @return productsLiveData
     */
    fun getProductsResponse(): MutableLiveData<Products> {
        return productsLiveData
    }

    /**Get the loading status
     * @return isLoading flag as live data
     */
    fun getLoadingStatus(): MutableLiveData<Boolean> {
        return isLoading
    }

    /**Get the loading status
     * @return isLoading flag as live data
     */
    fun setCartSize(cartSize: Int) {
        this.cartSize.postValue(cartSize)
    }


    fun setCartItems(cartMap: HashMap<Product, Int>) {
        this.cartMap.postValue(cartMap)
    }

    fun getCartItems(): MutableLiveData<HashMap<Product, Int>> {
        return this.cartMap
    }

    fun updateCartTotal(cartTotal: Double) {
        this.cartTotal.postValue(cartTotal)
    }

    fun getCartTotal(): MutableLiveData<Double> {
        return cartTotal
    }

    fun calculateCartSize(hashMap: HashMap<Product, Int>): Int {
        var total = 0

        hashMap.forEach { (_, value) ->
            total += value
        }

        return total
    }


    fun calculateCartTotal(cartItems: HashMap<Product, Int>): Double {
        var total = 0.0
        for ((key, value) in cartItems) {
            println("$key = $value")
            val price = key.price?.id ?: return 0.0
            total += price.times(value.toDouble())
        }

        return String.format("%.1f", total).toDouble()
    }
}