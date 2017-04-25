package co.qeek.shoppingcart.checkout

import cats.data.NonEmptyList
import cats.data.Validated.{Invalid, Valid}
import org.scalatest.{FlatSpec, Matchers}

class CheckoutSystemLookupProductsTest extends FlatSpec with Matchers {
  ".lookupProducts" should "return empty list for empty input" in {
    val system = new CheckoutSystem(Map())
    system.lookupProducts(List()) shouldBe Valid(List())
  }

  it should "return single valid product for single valid input" in {
    val product = CheckoutProduct("sample", 12)
    val system = new CheckoutSystem(Map(product.name -> product))
    system.lookupProducts(List(product.name)) shouldBe Valid(List(product))
  }

  it should "return single error for single invalid input" in {
    val system = new CheckoutSystem(Map())
    system.lookupProducts(List("doesntexist")) shouldBe
      Invalid(NonEmptyList.of(ProductNotFound("doesntexist")))
  }
}
