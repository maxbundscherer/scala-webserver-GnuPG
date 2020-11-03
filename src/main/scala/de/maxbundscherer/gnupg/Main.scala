package de.maxbundscherer.gnupg

object Main extends App {

  import de.maxbundscherer.gnupg.services.{ GnuPGService, WebServerService }

  import akka.actor.typed.ActorSystem
  import akka.actor.typed.scaladsl.Behaviors
  import org.slf4j.Logger

  implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "actorSystem")
  implicit val log: Logger                  = system.log

  private val gnuPGService     = new GnuPGService()
  private val webServerService = new WebServerService(gnuPGService)

  webServerService.startServer()

}
