package de.maxbundscherer.gnupg.services

import de.maxbundscherer.gnupg.utils.Configuration

import akka.actor.ActorSystem
import akka.event.LoggingAdapter

class WebServerService(gnuPGService: GnuPGService)(implicit
    actorSystem: ActorSystem,
    log: LoggingAdapter
) extends Configuration {

  private val webServerHandler = new WebServerHandler(this.gnuPGService)

  def startWebServer(): Unit =
    log.error("Should start webServer")

}
