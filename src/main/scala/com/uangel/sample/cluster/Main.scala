package com.uangel.sample.cluster

import akka.actor.ActorSystem
import akka.cluster.client.ClusterClientReceptionist
import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.StrictLogging

/**
  * 2017. 10. 19. - Created by Kwon, Yeong Eon
  */
object Main extends App with StrictLogging {

  logger.info("Start Sample Akka Cluster")

  val config = ConfigFactory.load()

  val actorSystem = ActorSystem("SampleAkkaCluster", config)

  val mainActorRef = actorSystem.actorOf(MainActor.props, MainActor.name)
  ClusterClientReceptionist(actorSystem).registerService(mainActorRef)
}
