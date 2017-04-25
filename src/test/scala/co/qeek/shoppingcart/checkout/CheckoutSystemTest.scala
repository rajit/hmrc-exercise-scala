package co.qeek.shoppingcart.checkout

import org.scalatest.{FlatSpec, Matchers}

class CheckoutSystemTest extends FlatSpec with Matchers {
  ".totalCost" should "return 0 for empty list" in {
    val system = new CheckoutSystem
    system.totalCost(List()) shouldBe 0
  }

  ".totalCost" should "return cost of product for single-item list" in {
    val system = new CheckoutSystem
    system.totalCost(List(CheckoutProduct("product-name", 19))) shouldBe 19
  }
}
