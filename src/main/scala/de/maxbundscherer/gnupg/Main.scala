package de.maxbundscherer.gnupg

import de.maxbundscherer.gnupg.services.{ GnuPGService, WebServerService }

import akka.actor.ActorSystem
import scala.concurrent.ExecutionContextExecutor

object Main extends App {

  private implicit val actorSystem: ActorSystem                   = ActorSystem("system")
  private implicit val executionContext: ExecutionContextExecutor = actorSystem.dispatcher

  private val gnuPGService     = new GnuPGService()
  private val webServerService = new WebServerService(gnuPGService)

}
