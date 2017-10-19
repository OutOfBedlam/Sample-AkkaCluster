package com.uangel.sample.http

import akka.util.Timeout
import com.typesafe.config.Config

/**
  * 2017. 10. 19. - Created by Kwon, Yeong Eon
  */
trait RequestTimeout {
  import scala.concurrent.duration._
  def requestTimeout(config: Config): Timeout = {
    val t = config.getString("akka.http.server.request-timeout")
    val d = Duration(t)
    FiniteDuration(d.length, d.unit)
  }
}
