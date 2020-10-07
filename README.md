
#### Authored by Prafulla Thottasseri
#### A small scalable shopping app for displaying a list of products loaded from a local JSON file using MVVM architecture and using Dagger2 for dependency injection.
The app can be further scaled up to show a product search and product checkout flow.

#### The app has the following packages:
1. **products**: It contains the model, view and viewmodel packages.
2. **products.model.ProductsRepository**: class for parsing the JSON in a background thread - coroutines are used for this.
3. **products.model.Products**: Contains Products data classes.
4. **products.view**: Fragment and adapter for products UI.
5. **products.viewmodel.ProductsViewModel**: A products viewmodel shared by cart and products list
6. **products.viewmodel.ProductsViewModelProvider**: A ViewmodelProvider used by ProductsViewModel
7. **cart**: Contains cart related packages
8. **cart.view**: Fragment and adapter for cart UI.
9. **MainActivity**: Main activity handling the fragments
10. **SplashScreenActivity**: Provides splash screen for the app

#### Using Jetpack Architecture Components
* LiveData
* ViewModel
* Kotlin Coroutines
* RecyclerView
* Swiperefreshlayout
* Coordinatorlayout

#### Picasso image loading library is used to load images in the app

#### GSON library is used for JSON parsing


