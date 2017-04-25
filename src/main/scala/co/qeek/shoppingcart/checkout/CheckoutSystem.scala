package co.qeek.shoppingcart.checkout

class CheckoutSystem {
  def totalCost(items: List[CheckoutProduct]): BigDecimal =
    items.foldLeft(BigDecimal(0))((acc, n: CheckoutProduct) => acc + n.price)
}

final case class CheckoutProduct(name: String, price: BigDecimal)
