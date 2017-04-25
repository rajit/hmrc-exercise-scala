package co.qeek.shoppingcart.checkout

import cats.data.Validated.Valid
import org.scalatest.{FlatSpec, Matchers}

class CheckoutSystem3For2Test extends FlatSpec with Matchers {
  ".totalCost" should "charge for 2 items of 3 if 3 for 2 applies" in {
    val product = CheckoutProduct("item1", 2)
    val system = new CheckoutSystem(Map(product.name -> product), CheckoutOffers(orangeThreeForTwo = true, orange = Some(product)))
    system.totalCost(List(product.name, product.name, product.name)) shouldBe Valid(4)
  }

  ".totalCost" should "charge for 2 items at price 10 of 3 for 2 applies" in {
    val product = CheckoutProduct("item1", 10)
    val system = new CheckoutSystem(Map(product.name -> product), CheckoutOffers(orangeThreeForTwo = true, orange = Some(product)))
    system.totalCost(List(product.name, product.name, product.name)) shouldBe Valid(20)
  }

  ".totalCost" should "charge for 4 items, plus 2 others, if 3 for 2 applies twice" in {
    val product1 = CheckoutProduct("item1", 7)
    val product2 = CheckoutProduct("item2", 13)
    val system = new CheckoutSystem(
      Map(product1.name -> product1, product2.name -> product2),
      CheckoutOffers(orangeThreeForTwo = true, orange = Some(product1)))
    system.totalCost(List(
      product1.name,
      product1.name,
      product2.name,
      product1.name,
      product2.name,
      product1.name,
      product1.name,
      product1.name)) shouldBe Valid(54)
  }
}
