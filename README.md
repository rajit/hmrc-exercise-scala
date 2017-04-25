# HMRC Checkout System Exercise

## Assumptions
* I am to build a CheckoutSystem library, which might have a REST API built around it or a UI (no such system is included)

## Usage
```
// Create new CheckoutSystem
val system = new CheckoutSystem(List(
    CheckoutProduct("name", <price: BigDecimal>)...))
    
// Create new CheckoutSystem with offers
val system = new CheckoutSystem(
    List(CheckoutProduct("name", <price: BigDecimal>)...),
    CheckoutOffers(appleBuyOneGetOneFree = true, apple = CheckoutProduct("apple", BigDecimal("0.6")))
```