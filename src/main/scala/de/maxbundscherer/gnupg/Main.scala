package de.maxbundscherer.gnupg

import de.maxbundscherer.gnupg.services.{ GnuPGService, WebServerService }

import akka.actor.ActorSystem
import akka.event.LoggingAdapter

object Main extends App {

  private implicit val actorSystem: ActorSystem = ActorSystem("system")
  private implicit val log: LoggingAdapter      = actorSystem.log

  private val gnuPGService     = new GnuPGService()
  private val webServerService = new WebServerService(gnuPGService)

}
