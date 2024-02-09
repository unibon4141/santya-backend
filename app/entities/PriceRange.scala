package entities


case class PriceRange (
    id: PriceRangeId,
    min: Int,
    max: Int,
                            )
object PriceRange {

  def of(id: Int): PriceRange = PriceRange(
    id = PriceRangeId(id),
    min = calcMinPrice(id),
    max = calcMaxPrice(id)
  )

  private def calcMinPrice(id: Int): Int = {
    val min = (id-1)*500
    if(min >= 0) min
    else 1
  }

  private def calcMaxPrice(id: Int): Int = {
    id * 500
  }

}