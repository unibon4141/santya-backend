package entities

import java.time.LocalDateTime

final case class Shop(
                       id: ShopId,
                       name: String,
                       mapId: Option[MapId],
                       genre: Genre,
                       scenes: Seq[Scene],
                       lunchPriceRange: Option[PriceRange],
                       dinnerPriceRange: Option[PriceRange],
                       shopAddress: Option[Address],
                       distance: Double,
                       createAt: LocalDateTime,
                       updatedAt: LocalDateTime,
                     )