package com.uangel.sample.http

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import akka.actor._
import akka.pattern.ask
import akka.util.Timeout
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import spray.json.DefaultJsonProtocol

/**
  * 2017. 10. 19. - Created by Kwon, Yeong Eon
  */
class RestApi(system: ActorSystem, timeout: Timeout) extends RestRoutes {
  implicit val requestTimeout: Timeout = timeout
  implicit def executionContext: ExecutionContext = system.dispatcher

  //def createBoxOffice = system.actorOf(BoxOffice.props, BoxOffice.name)
}

trait RestRoutes extends EventsApi with UsersApi with RestApiMarshalling {
  import StatusCodes._

  def routes: Route = eventsRoute ~ usersRoute

  def eventsRoute =
    pathPrefix("events") {
      pathEndOrSingleSlash {
        get {
          // GET /events
          onSuccess(getEvents()) { events =>
            complete(OK, events)
          }
        }
      }
    }

  def usersRoute =
    pathPrefix("entity" / "users") {
      pathEndOrSingleSlash {
        get {
          // GET /entity/users
          onSuccess(getUsers()) { users =>
            complete(OK, users)
          }
        }
      }
    }
}

trait RestApiMarshalling extends DefaultJsonProtocol {

}

trait EventsApi {
  implicit def executionContext: ExecutionContext
  implicit def requestTimeout: Timeout

  def getEvents() = Future(Seq("abc", "ddd"))

}

trait UsersApi {
  implicit def executionContext: ExecutionContext
  implicit def requestTimeout: Timeout

  def getUsers() = Future(Seq("HonGilDong", "ChunHyang"))
}