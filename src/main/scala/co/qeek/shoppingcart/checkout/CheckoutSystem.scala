package co.qeek.shoppingcart.checkout

import cats.data.Validated.{Invalid, Valid}
import cats.data.ValidatedNel
import cats.implicits._

class CheckoutSystem(products: Map[String, CheckoutProduct]) {
  def totalCostForProducts(items: List[CheckoutProduct]): BigDecimal =
    items.foldLeft(BigDecimal(0))((acc, n) => acc + n.price)

  def lookupProducts(items: List[String]): ValidatedNel[ProductLookupError, List[CheckoutProduct]] =
    items.traverseU { (item: String) =>
      products.get(item) match {
        case Some(product) =>
          Valid(product).toValidatedNel
        case None =>
          Invalid(ProductNotFound(item)).toValidatedNel
      }
    }

  // Returns total cost in £
  def totalCost(items: List[String]): ValidatedNel[ProductLookupError, BigDecimal] =
    lookupProducts(items).map(totalCostForProducts)
}

// Price is in £
final case class CheckoutProduct(name: String, price: BigDecimal)

sealed abstract class ProductLookupError
final case class ProductNotFound(name: String) extends ProductLookupError
