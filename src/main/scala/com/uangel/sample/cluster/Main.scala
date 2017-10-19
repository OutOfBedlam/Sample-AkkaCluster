package com.uangel.sample.cluster

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.StrictLogging
import com.uangel.sample.http.{RequestTimeout, RestApi}

import scala.concurrent.{ExecutionContext, Future}

/**
  * 2017. 10. 19. - Created by Kwon, Yeong Eon
  */
object Main extends App with StrictLogging with RequestTimeout{

  logger.info("Start Sample Akka Cluster")

  val config = ConfigFactory.load()
  val host = config.getString("http.host")
  val port = config.getInt("http.port")

  implicit val actorSystem: ActorSystem = ActorSystem("SampleAkkaCluster", config)
  implicit val ec: ExecutionContext = actorSystem.dispatcher
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  val api = new RestApi(actorSystem, requestTimeout(config)).routes
  val bindingFuture: Future[ServerBinding] = Http().bindAndHandle(api, host, port)

  actorSystem.actorOf(MainActor.props, MainActor.name)
}
