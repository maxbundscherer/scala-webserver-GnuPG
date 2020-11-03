package de.maxbundscherer.gnupg.utils

trait Configuration {

  object Config {

    object WebServer {

      val host: String = "0.0.0.0"
      val port: Int    = 8080

    }

    object GnuPGService {}

  }

}
