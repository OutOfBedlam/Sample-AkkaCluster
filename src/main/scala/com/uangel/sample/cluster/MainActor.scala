package com.uangel.sample.cluster

import akka.actor.{Actor, Props}
import akka.cluster.client.ClusterClientReceptionist
import com.typesafe.scalalogging.StrictLogging

/**
  * 2017. 10. 19. - Created by Kwon, Yeong Eon
  */
class MainActor extends Actor with StrictLogging {

  override def preStart(): Unit = {
    // 클러스터 내의 멤버의 변화 상황을 추적하는 이벤트 리스너 액터를 등록
    val clusterEventListenerActorRef = context.actorOf(ClusterEventListener.props, ClusterEventListener.name)
    ClusterClientReceptionist(context.system).registerService(clusterEventListenerActorRef)

    // 클러스터 클라이언트의 접속 이벤트를 추적하는 리셉셔니스트 리스너 액터를 등록
    val receptionistActorRef = ClusterClientReceptionist.get(context.system).underlying
    context.actorOf(ReceptionistListener.props(receptionistActorRef), ReceptionistListener.name)
  }

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