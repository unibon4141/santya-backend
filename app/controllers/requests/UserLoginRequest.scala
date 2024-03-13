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
