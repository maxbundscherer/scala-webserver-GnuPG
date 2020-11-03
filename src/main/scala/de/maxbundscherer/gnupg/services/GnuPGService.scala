package de.maxbundscherer.gnupg.services

import de.maxbundscherer.gnupg.utils.Configuration

import akka.event.LoggingAdapter

class GnuPGService()(implicit log: LoggingAdapter) extends Configuration {

  log.info("GnuPGService started")

}
