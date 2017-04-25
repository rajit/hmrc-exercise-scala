package co.qeek.shoppingcart.checkout

import cats.data.Validated.{Invalid, Valid}
import cats.data.ValidatedNel
import cats.implicits._

class CheckoutSystem(products: Map[String, CheckoutProduct], offers: CheckoutOffers = CheckoutOffers()) {
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

  def calculateBuyOneGetOneFree(items: List[CheckoutProduct]): List[Discount] =
    (offers.appleBuyOneGetOneFree, offers.apple) match {
      case (true, Some(product)) =>
        val noOfApples = items.count(_.name == product.name)
        if (noOfApples > 1) {
          // This assumes apples are always the same price, but that is not currently enforced by the interface
          Discount(Math.floor(noOfApples.toDouble / 2d) * product.price) :: Nil
        } else Nil
      case _ => Nil
    }

  def applyTheseDiscounts(totalCost: BigDecimal, discounts: List[Discount]): BigDecimal =
    discounts.foldLeft(totalCost)((acc, n) => acc - n.amount)

  def applyAnyDiscounts(items: List[CheckoutProduct], totalCost: BigDecimal): BigDecimal = {
    val buyOneDiscount = calculateBuyOneGetOneFree(items)
    applyTheseDiscounts(totalCost, buyOneDiscount)
  }

  // Returns total cost in £
  def totalCost(items: List[String]): ValidatedNel[ProductLookupError, BigDecimal] =
    lookupProducts(items).map { products =>
      applyAnyDiscounts(products, totalCostForProducts(products))
    }
}

// Price is in £
final case class CheckoutProduct(name: String, price: BigDecimal)

sealed abstract class ProductLookupError
final case class ProductNotFound(name: String) extends ProductLookupError

final case class CheckoutOffers(appleBuyOneGetOneFree: Boolean = false, apple: Option[CheckoutProduct] = None)
final case class Discount(amount: BigDecimal)
