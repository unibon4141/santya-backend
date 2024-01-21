package entities

import java.time.LocalDateTime

final case class Shop(
    id: ShopId,
    name: String,
    mapId: MapId,
    genreId: GenreId,
    sceneIds: Seq[SceneId],
    lunchPriceRangeId: PriceRangeId,
    dinnerPriceRangeId: PriceRangeId,
    shopAddress: Address,
    distance: Double,
    lastUpdateAt: LocalDateTime
)
