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
  import akka.http.scaladsl.server.Directives.{ entity, _ }

  private val webServerHandler: WebServerHandler = new WebServerHandler(this.gnuPGService)

  def startServer(): Unit = {

    val route =
      pathEndOrSingleSlash {
        get {
          complete(
            HttpEntity(
              ContentTypes.`text/html(UTF-8)`,
              this.webServerHandler.home
            )
          )
        }
      } ~
      pathPrefix("getWorkDirFiles") {
        get {
          complete(
            HttpEntity(
              ContentTypes.`text/html(UTF-8)`,
              this.webServerHandler.getWorkDirFiles
            )
          )
        }
      } ~
      pathPrefix("getPublicKeys") {
        get {
          complete(
            HttpEntity(
              ContentTypes.`text/html(UTF-8)`,
              this.webServerHandler.getPublicKeys
            )
          )
        }
      } ~
      pathPrefix("getPrivateKeys") {
        get {
          complete(
            HttpEntity(
              ContentTypes.`text/html(UTF-8)`,
              this.webServerHandler.getPrivateKeys
            )
          )
        }
      } ~
      pathPrefix("writeTestFile") {
        get {
          complete(
            HttpEntity(
              ContentTypes.`text/html(UTF-8)`,
              this.webServerHandler.writeTestFile
            )
          )
        }
      } ~
      pathPrefix("importPublicKey") {
        get {
          complete(
            HttpEntity(
              ContentTypes.`text/html(UTF-8)`,
              this.webServerHandler.importPublicKey
            )
          )
        }
      } ~ pathPrefix("importPublicKey2") {
        formFields("key") { key =>
          complete(
            HttpEntity(
              ContentTypes.`text/html(UTF-8)`,
              this.webServerHandler.importPublicKey2(key)
            )
          )
        }
      } ~ pathPrefix("encryptMsg") {
        get {
          complete(
            HttpEntity(
              ContentTypes.`text/html(UTF-8)`,
              this.webServerHandler.encryptMsg
            )
          )
        }
      } ~ pathPrefix("encryptMsg2") {
        formFields("receiver", "plainText") { (receiver, plainText) =>
          complete(
            HttpEntity(
              ContentTypes.`text/html(UTF-8)`,
              this.webServerHandler.encryptMsg2(receiver = receiver, plainText = plainText)
            )
          )
        }
      } ~ pathPrefix("decryptMsg") {
        get {
          complete(
            HttpEntity(
              ContentTypes.`text/html(UTF-8)`,
              this.webServerHandler.decryptMsg
            )
          )
        }
      } ~ pathPrefix("decryptMsg2") {
        formFields("author", "encryptedText") { (author, encryptedText) =>
          complete(
            HttpEntity(
              ContentTypes.`text/html(UTF-8)`,
              this.webServerHandler
                .decryptMsg2(author = author, encryptedText = encryptedText)
            )
          )
        }
      }

    val _ = Http().newServerAt(interface = Config.WebServer.host, Config.WebServer.port).bind(route)

    log.info(s"WebServer online at http://${Config.WebServer.host}:${Config.WebServer.port}")

  }

}
