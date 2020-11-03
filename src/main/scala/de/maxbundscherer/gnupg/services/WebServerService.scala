package de.maxbundscherer.gnupg.services

import de.maxbundscherer.gnupg.utils.Configuration

import akka.actor.typed.ActorSystem
import org.slf4j.Logger

class WebServerService(gnuPGService: GnuPGService)(implicit
    system: ActorSystem[Nothing],
    log: Logger
) extends Configuration {

  import akka.http.scaladsl.Http
  import akka.http.scaladsl.model._
  import akka.http.scaladsl.server.Directives._

  private val webServerHandler: WebServerHandler = new WebServerHandler(this.gnuPGService)

  def startServer(): Unit = {

    val route =
      pathEndOrSingleSlash {
        get {
          complete(
            HttpEntity(
              ContentTypes.`text/html(UTF-8)`,
              "<h1>Say hello to akka-http (v0)</h1>"
            )
          )
        }
      }

    val _ = Http().newServerAt(interface = Config.WebServer.host, Config.WebServer.port).bind(route)

    log.info("WebServer online at http://0.0.0.0:8080")

  }

}
