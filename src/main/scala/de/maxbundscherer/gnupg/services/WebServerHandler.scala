package de.maxbundscherer.gnupg.services

import de.maxbundscherer.gnupg.utils.Configuration

import org.slf4j.Logger

private class WebServerHandler(gnuPGService: GnuPGService)(implicit log: Logger)
    extends Configuration {

  log.debug("WebServerHandler online")

}
