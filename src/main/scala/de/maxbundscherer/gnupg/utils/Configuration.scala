package de.maxbundscherer.gnupg.utils

trait Configuration {

  object Config {

    object Global {
      val productName: String = de.maxbundscherer.gnupg.utils.BuildInfo.name
    }

    object WebServer {
      val host: String = "0.0.0.0"
      val port: Int    = 8080
    }

    //Please check if folder exists
    object GnuPGService {
      val workDir: String = "workDir/"
    }

  }

}
