package controllers.responses
case class UserLoginResponse(
                            token: Option[String]
                            )
case class UserSignUpResponse(
                              token: Option[String]
                            )
case class UserIdAuthResponse(
                               user_id: Int
                             )

case class ToggleFavoriteResponse(
                               status: Int
                             )