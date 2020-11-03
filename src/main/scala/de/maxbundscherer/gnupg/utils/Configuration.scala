package de.maxbundscherer.gnupg.utils

trait Configuration {

  object Config {

    object Global {
      val productName: String = "Scala-GnuPG Webserver"
    }

    object WebServer {
      val host: String = "0.0.0.0"
      val port: Int    = 8080
    }

    object GnuPGService {
      val workDir: String = "workDir/"
    }

  }

}
