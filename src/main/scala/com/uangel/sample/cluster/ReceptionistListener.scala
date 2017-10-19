package com.uangel.sample.cluster

import akka.actor.{Actor, ActorRef, Props}
import akka.cluster.client.{ClusterClientUnreachable, ClusterClientUp, ClusterClients, SubscribeClusterClients}
import com.typesafe.scalalogging.StrictLogging

/**
  * 2017. 10. 19. - Created by Kwon, Yeong Eon
  */
class ReceptionistListener(targetReceptionist: ActorRef) extends Actor with StrictLogging {

  override def preStart(): Unit = {
    targetReceptionist ! SubscribeClusterClients
  }

  override def receive: Receive = receiveWithClusterClients(Set.empty)

  def receiveWithClusterClients(clusterClients: Set[ActorRef]): Receive = {
    case ClusterClients(cs) =>
      logger.info(s"Cluster clients: ${cs.mkString(", ")}")
      context.become(receiveWithClusterClients(cs))

    case ClusterClientUp(c) =>
      logger.info(s"Cluster client up: ${c.toString()}")
      context.become(receiveWithClusterClients(clusterClients + c))

    case ClusterClientUnreachable(c) =>
      logger.info(s"Cluster client unreachable: ${c.toString()}")
      context.become(receiveWithClusterClients(clusterClients - c))
  }
}

object ReceptionistListener {
  def props(targetReceptionist: ActorRef): Props = Props(new ReceptionistListener(targetReceptionist))
  val name: String = "receptionistListener"
}