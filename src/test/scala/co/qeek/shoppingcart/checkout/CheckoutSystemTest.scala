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
}
