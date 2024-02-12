package controllers.responses

import entities.Scene

case class SceneResponse (
                           scene_id: Int,
                           scene_name: String,
                         )

object SceneResponse {
  def make(scenes: Seq[Scene]): Seq[SceneResponse] = {
    scenes.map{ s =>
      SceneResponse(
        s.id.value,
        s.name
      )
    }
  }

}

