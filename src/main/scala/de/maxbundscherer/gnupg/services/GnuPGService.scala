package de.maxbundscherer.gnupg.services

import de.maxbundscherer.gnupg.utils.{ Configuration, FileHelper }

import org.slf4j.Logger

class GnuPGService()(implicit log: Logger) extends Configuration with FileHelper {

  import scala.util.{ Failure, Success, Try }
  import scala.language.postfixOps
  import scala.sys.process._

  log.info("GnuPGService started")

  private def handleWriteException(exception: Throwable): String = {
    val errorMsg = s"Write exception (${exception.getLocalizedMessage})"
    log.warn(errorMsg)
    errorMsg
  }

  private def formOutputToHtml(input: String): String = input.replaceAll("\n", "<br>")

  private def shellCmdWrapper(cmd: String): String =
    Try {
      cmd !!
    } match {
      case Failure(exception) =>
        val errorMsg = s"Cmd failed ($cmd) (${exception.getLocalizedMessage})"
        log.warn(errorMsg)
        errorMsg
      case Success(response) => response
    }

  private val workDir = Config.GnuPGService.workDir

  def getWorkDirFiles: String =
    this.formOutputToHtml(this.shellCmdWrapper(cmd = "find " + workDir)) + "\n\n"

  def getPublicKeys: String =
    this.formOutputToHtml(input = this.shellCmdWrapper(cmd = "gpg --list-keys"))

  def getPrivateKeys: String =
    this.formOutputToHtml(input = this.shellCmdWrapper(cmd = "gpg --list-secret-keys"))

  def writeTestFile: String =
    FileHelper.writeToFile(
      content = "Test-Content",
      filename = "testFile.txt",
      workDirPrefix = FileHelper.generateNewWorkDirPrefix
    ) match {
      case Failure(exception) => this.handleWriteException(exception)
      case Success(content)   => s"Write success (filePath=$content)"
    }

  def encryptMsg(receiverMail: String, plainText: String): String =
    FileHelper.writeToFile(
      content = plainText,
      filename = "to-encrypt.txt",
      FileHelper.generateNewWorkDirPrefix
    ) match {
      case Failure(exception)         => this.handleWriteException(exception)
      case Success(toEncryptFilePath) =>
        //Encrypt
        this.formOutputToHtml(input =
          this.shellCmdWrapper(cmd =
            s"gpg --recipient $receiverMail --encrypt --armor $toEncryptFilePath"
          )
        ) +
        //Rm plainText
        this.formOutputToHtml(input = this.shellCmdWrapper(cmd = "rm " + toEncryptFilePath)) +
        //Show encrypted data
        this.formOutputToHtml(input =
          this.shellCmdWrapper(cmd = "cat " + toEncryptFilePath + ".asc")
        )
    }

}
