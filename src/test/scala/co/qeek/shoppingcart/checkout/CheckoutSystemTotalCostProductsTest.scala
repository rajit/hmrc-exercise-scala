package co.qeek.shoppingcart.checkout

import org.scalatest.{FlatSpec, Matchers}
import scala.language.reflectiveCalls

class CheckoutSystemTotalCostProductsTest extends FlatSpec with Matchers {
  def fixture = new {
    val system = new CheckoutSystem(Map("dummy" -> CheckoutProduct("dummy", 1)))
  }

  ".totalCostForProducts" should "return 0 for empty list" in {
    val f = fixture
    f.system.totalCostForProducts(List()) shouldBe 0
  }

  it should "return cost of product for single-item list" in {
    val f = fixture
    f.system.totalCostForProducts(List(CheckoutProduct("product-name", 19))) shouldBe 19
  }

  it should "return total cost given 2 products" in {
    val f = fixture
    f.system.totalCostForProducts(List(
      CheckoutProduct("product-name", 12),
      CheckoutProduct("other-product", 51))) shouldBe 63
  }

  it should "return 2.05 for 3 apples and an orange" in {
    val f = fixture

    val apple = CheckoutProduct("apple", BigDecimal("0.6"))
    val orange = CheckoutProduct("orange", BigDecimal("0.25"))
    val products = List(apple, apple, orange, apple)

    f.system.totalCostForProducts(products) shouldBe BigDecimal("2.05")
  }
}
