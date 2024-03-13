package controllers.requests

case class UserLoginRequest(
                           username: String,
                           password: String
                           )

case class UserSignUpRequest(
                             username: String,
                             password: String
                           )
case class UserAuthRequest(
                          token: String
                           )
case class UserIdResponse(
                            user_id: String
                          )
