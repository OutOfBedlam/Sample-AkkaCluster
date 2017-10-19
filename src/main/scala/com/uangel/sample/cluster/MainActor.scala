package com.uangel.sample.cluster

import akka.actor.{Actor, Props}
import akka.cluster.client.ClusterClientReceptionist
import com.typesafe.scalalogging.StrictLogging

/**
  * 2017. 10. 19. - Created by Kwon, Yeong Eon
  */
class MainActor extends Actor with StrictLogging {

  private val clusterEventListenerActorRef = context.actorOf(ClusterEventListener.props, ClusterEventListener.name)
  ClusterClientReceptionist(context.system).registerService(clusterEventListenerActorRef)

  override def receive: Receive = {
    case msg: String =>
      logger.info(s"Receved: $msg")

      sender() ! s"Hello $msg"
  }
}

object MainActor {
  val props: Props = Props[MainActor]
  val name: String = "mainActor"
}