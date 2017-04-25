package co.qeek.shoppingcart.checkout

import cats.data.Validated.Valid
import org.scalatest.{FlatSpec, Matchers}

class CheckoutSystemBuyOneGetOneFreeTest extends FlatSpec with Matchers {
  ".totalCost" should "charge for one item of two if buy-one-get-one-free applies" in {
    val product = CheckoutProduct("item1", 2)
    val system = new CheckoutSystem(Map(product.name -> product), CheckoutOffers(appleBuyOneGetOneFree = true, Some(product)))
    system.totalCost(List(product.name, product.name)) shouldBe Valid(2)
  }

  ".totalCost" should "charge for one item at price 10 of two if get one free discount applies" in {
    val product = CheckoutProduct("item1", 10)
    val system = new CheckoutSystem(Map(product.name -> product), CheckoutOffers(appleBuyOneGetOneFree = true, Some(product)))
    system.totalCost(List(product.name, product.name)) shouldBe Valid(10)
  }

  ".totalCost" should "charge for 2 items, plus 2 others, if 2 have get one free discount and there are 4" in {
    val product1 = CheckoutProduct("item1", 6)
    val product2 = CheckoutProduct("item2", 2)
    val product3 = CheckoutProduct("item3", 1)
    val system = new CheckoutSystem(Map(
      product1.name -> product1,
      product2.name -> product2,
      product3.name -> product3),
      CheckoutOffers(appleBuyOneGetOneFree = true, Some(product1)))
    system.totalCost(List(
      product1.name,
      product1.name,
      product2.name,
      product1.name,
      product3.name,
      product1.name)) shouldBe
      Valid(15) // 6 + 6 + 2 + 6 + 1 + 6 - (6 + 6)
  }
}
