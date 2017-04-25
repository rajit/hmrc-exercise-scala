# HMRC Checkout System Exercise

## Assumptions
* I am to build a CheckoutSystem library, which might have a REST API built around it or a UI (no such system is included)

## Usage
```
// Create new CheckoutSystem
val system = new CheckoutSystem(List(
    CheckoutProduct("name", <price: BigDecimal>)...))
    
// Create new CheckoutSystem with apple offer
val system = new CheckoutSystem(
    List(CheckoutProduct("apple", <price: BigDecimal>)...),
    CheckoutOffers(appleBuyOneGetOneFree = true, apple = CheckoutProduct("apple", BigDecimal("0.6")))
    
// Create new CheckoutSystem with orange offer
val system = new CheckoutSystem(
    List(CheckoutProduct("orange", <price: BigDecimal>)...),
    CheckoutOffers(orangeThreeForTwo = true, orange = CheckoutProduct("orange", BigDecimal("0.25")))
```