package com.project.foodtracker.ui.product_edit


sealed class ProductDetailEditEvent {
    class OnClickSaveProductDetail(): ProductDetailEditEvent()
    class OnClickGoBack(): ProductDetailEditEvent()
    data class OnProductTitleChanged(val title: String) : ProductDetailEditEvent()
    data class OnProductImageChanged(val image: String) : ProductDetailEditEvent()
    data class OnProductServingsChanged(val servings: Int) : ProductDetailEditEvent()
    data class OnProductReadyInMinutesChanged(val readyInMinutes: Int) : ProductDetailEditEvent()
    data class OnProductHealthScoreChanged(val healthScore: Float) : ProductDetailEditEvent()
    data class OnProductSpoonacularScoreChanged(val spoonacularScore: Float) : ProductDetailEditEvent()
    data class OnProductPricePerServingChanged(val pricePerServing: Float) : ProductDetailEditEvent()
    data class OnProductCheapChanged(val cheap: Boolean) : ProductDetailEditEvent()
    data class OnProductCreditsTextChanged(val creditsText: String) : ProductDetailEditEvent()
    class OnClickUndoProductDetail  : ProductDetailEditEvent()

}
