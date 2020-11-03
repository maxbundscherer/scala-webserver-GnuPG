package de.maxbundscherer.gnupg.services

import de.maxbundscherer.gnupg.utils.Configuration

import org.slf4j.Logger

class GnuPGService()(implicit log: Logger) extends Configuration {

  log.info("GnuPGService started")

  def getWorkDirPath: String = Config.GnuPGService.workDir

  def getPublicKeys: String = "not implemented"

}
