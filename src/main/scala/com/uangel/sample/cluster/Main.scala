package com.uangel.sample.cluster

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.StrictLogging

/**
  * 2017. 10. 19. - Created by Kwon, Yeong Eon
  */
object Main extends App with StrictLogging {

  logger.info("Start Sample Akka Cluster")

  val config = ConfigFactory.load()

  val actorSystem = ActorSystem("SampleAkkaCluster", config)

  actorSystem.actorOf(MainActor.props, MainActor.name)
}
