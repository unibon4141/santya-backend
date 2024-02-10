package entities

 class Scene (
    val id: SceneId,
    val name: String
                 )

object Scene {

  case object DrinkingParty       extends Scene(SceneId(1), "飲み会")
  case object Lunch       extends Scene(SceneId(2), "ランチ")
  case object After14       extends Scene(SceneId(3), "14時以降も営業")

  private val values: Seq[Scene] = Seq(
    DrinkingParty,
    Lunch,
    After14
  )

  def of(id: Int): Option[Scene] = values.find(_.id.value == id)
}


