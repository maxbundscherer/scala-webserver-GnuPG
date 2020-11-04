package de.maxbundscherer.gnupg.utils

trait FileHelper extends Configuration {

  import scala.util.Try
  import better.files._
  import java.io.{ File => JFile }

  object FileHelper {

    def generateNewWorkDirPrefix: String = System.currentTimeMillis().toString + "-temp/"

    /**
      * @param content String
      * @param filename String
      * @param workDirPrefix String
      * @return Try with FilePath
      */
    def writeToFile(content: String, filename: String, workDirPrefix: String): Try[String] =
      Try {

        File(s"${Config.GnuPGService.workDir}$workDirPrefix")
          .createDirectoryIfNotExists()

        File(s"${Config.GnuPGService.workDir}$workDirPrefix$filename")
          .createFileIfNotExists()
          .write(content)
          .pathAsString

      }

  }

}
