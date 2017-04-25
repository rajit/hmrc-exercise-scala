package co.qeek.shoppingcart.checkout

import org.scalatest.{FlatSpec, Matchers}

class CheckoutSystemTest extends FlatSpec with Matchers {
  ".totalCost" should "return 0 for empty list" in {
    val system = new CheckoutSystem
    system.totalCost(List()) shouldBe 0
  }

  it should "return cost of product for single-item list" in {
    val system = new CheckoutSystem
    system.totalCost(List(CheckoutProduct("product-name", 19))) shouldBe 19
  }

  it should "return total cost given 2 products" in {
    val system = new CheckoutSystem
    system.totalCost(List(
      CheckoutProduct("product-name", 12),
      CheckoutProduct("other-product", 51))) shouldBe 63
  }

  it should "return 2.05 for 3 apples and an orange" in {
    val system = new CheckoutSystem

    val apple = CheckoutProduct("apple", BigDecimal("0.6"))
    val orange = CheckoutProduct("orange", BigDecimal("0.25"))
    val products = List(apple, apple, orange, apple)

    system.totalCost(products) shouldBe BigDecimal("2.05")
  }
}
