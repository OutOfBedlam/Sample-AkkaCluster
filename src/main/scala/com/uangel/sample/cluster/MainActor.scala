package com.uangel.sample.cluster

import akka.actor.{Actor, Props}
import com.typesafe.scalalogging.StrictLogging

/**
  * 2017. 10. 19. - Created by Kwon, Yeong Eon
  */
class MainActor extends Actor with StrictLogging {
  override def receive = {
    case msg: String =>
      logger.info(s"Receved: $msg")
  }
}

object MainActor {
  val props = Props[MainActor]
}