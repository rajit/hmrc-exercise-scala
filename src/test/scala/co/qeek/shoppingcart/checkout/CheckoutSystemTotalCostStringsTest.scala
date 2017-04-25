package co.qeek.shoppingcart.checkout

import cats.data.NonEmptyList
import cats.data.Validated.{Invalid, Valid}
import org.scalatest.{FlatSpec, Matchers}

class CheckoutSystemTotalCostStringsTest extends FlatSpec with Matchers {
  ".totalCost(strings)" should "return 0 for empty list" in {
    val system = new CheckoutSystem(Map())
    system.totalCost(List()) shouldBe Valid(0)
  }

  it should "return 10 for single recognised product of price 10" in {
    val system = new CheckoutSystem(Map("item" -> CheckoutProduct("item", 10)))
    system.totalCost(List("item")) shouldBe Valid(10)
  }

  it should "return all lookup errors" in {
    val product1 = CheckoutProduct("item1", 7)
    val product2 = CheckoutProduct("item2", 3)
    val product3 = CheckoutProduct("item3", 6)
    val system = new CheckoutSystem(Map(
      product1.name -> product1, product2.name -> product2, product3.name -> product3))
    system.totalCost(List("item2", "item2", "item1", "item4", "item3", "asdf")) shouldBe
      Invalid(NonEmptyList.of(ProductNotFound("item4"), ProductNotFound("asdf")))
  }

  it should "return total cost for muliple valid products" in {
    val product1 = CheckoutProduct("item1", 7)
    val product2 = CheckoutProduct("item2", 3)
    val product3 = CheckoutProduct("item3", 6)
    val system = new CheckoutSystem(Map(
      product1.name -> product1, product2.name -> product2, product3.name -> product3))
    system.totalCost(List("item2", "item2", "item1", "item3")) shouldBe
      Valid(BigDecimal(19))
  }
}
