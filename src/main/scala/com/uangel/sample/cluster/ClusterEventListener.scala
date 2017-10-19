package com.uangel.sample.cluster

import akka.actor.{Actor, Props}
import akka.cluster.{Cluster, Member}
import akka.cluster.ClusterEvent._
import com.typesafe.scalalogging.StrictLogging

/**
  * 2017. 10. 19. - Created by Kwon, Yeong Eon
  */
class ClusterEventListener extends Actor with StrictLogging {

  private val cluster = Cluster(context.system)

  override def preStart(): Unit = {
    cluster.subscribe(self, initialStateMode = InitialStateAsEvents, classOf[MemberEvent], classOf[UnreachableMember])
  }

  override def receive: Receive = {
    case "GetCurrentClusterState" =>
      cluster.state.members.foreach{ member =>
        logger.info(s"Member: ${member.uniqueAddress} [${member.status}] role: ${member.roles}")
      }
      sender() ! cluster.state.copy()

    case MemberUp(member) =>
      logger.info(s"Member is Up: ${member.address}")
    case MemberRemoved(member, previousStatus) =>
      logger.info(s"Member is Removed: ${member.address} after $previousStatus")
    case UnreachableMember(member) =>
      logger.info(s"Member detected as unreachable: $member")
    case evt :MemberEvent =>
      logger.info(s"Member event: ${evt.toString}")
  }
}

object ClusterEventListener {
  val props: Props = Props[ClusterEventListener]
  val name: String = "clusterEventListener"
}