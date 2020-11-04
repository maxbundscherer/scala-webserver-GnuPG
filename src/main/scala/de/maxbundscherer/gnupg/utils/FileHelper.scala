package de.maxbundscherer.gnupg.utils

trait FileHelper extends Configuration {

  import scala.util.Try
  import java.io._

  object FileHelper {

    def generateNewWorkDirPrefix: String = System.currentTimeMillis().toString + "-temp/"

    def writeToFile(content: String, filename: String, workDirPrefix: String): Try[Unit] =
      Try {

        val pw = new PrintWriter(new File(s"${Config.GnuPGService.workDir}$workDirPrefix$filename"))
        pw.write(content)
        pw.close()

      }

  }

}
