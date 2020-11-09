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

  private def shellCmdWrapper(cmd: String): String = {

    case class OwnProcessLogger(var loggedData: String = "") extends ProcessLogger {
      override def out(s: => String): Unit = loggedData = loggedData + "<br> (Out): " + s
      override def err(s: => String): Unit = loggedData = loggedData + "<br> (Error/Warn): " + s
      override def buffer[T](f: => T): T   = ???
    }

    val ownLogger = OwnProcessLogger()

    Try {
      cmd !! ownLogger
    } match {
      case Failure(exception) =>
        val errorMsg =
          s"Cmd failed ($cmd) (${exception.getLocalizedMessage})" + "<hr> " + ownLogger.loggedData
        log.warn(errorMsg)
        errorMsg
      case Success(response) => response + "<hr> " + ownLogger.loggedData
    }
  }

  def getWorkDirFiles: String =
    this.formOutputToHtml(
      this.shellCmdWrapper(cmd = "find " + Config.GnuPGService.workDir)
    ) + "\n\n"

  def getPublicKeys: String =
    this.formOutputToHtml(input = this.shellCmdWrapper(cmd = "gpg --batch --list-keys"))

  def getPrivateKeys: String =
    this.formOutputToHtml(input = this.shellCmdWrapper(cmd = "gpg --batch --list-secret-keys"))

  def sendCmd(cmd: String): String =
    this.formOutputToHtml(input = this.shellCmdWrapper(cmd = cmd))

  def writeTestFile: String =
    FileHelper.writeToFile(
      content = "Test-Content",
      filename = "testFile.txt",
      workDirPrefix = FileHelper.generateNewWorkDirPrefix
    ) match {
      case Failure(exception) => this.handleWriteException(exception)
      case Success(content)   => s"Write success (filePath=$content)"
    }

  def importPublicKey(key: String): String =
    FileHelper.writeToFile(
      content = key,
      filename = "import-key.asc",
      FileHelper.generateNewWorkDirPrefix
    ) match {
      case Failure(exception)   => this.handleWriteException(exception)
      case Success(keyFilePath) =>
        //Import key
        this.formOutputToHtml(input =
          this.shellCmdWrapper(cmd = "gpg --batch --import  " + keyFilePath)
        ) +
        //Remove key
        this.formOutputToHtml(input = this.shellCmdWrapper(cmd = "rm " + keyFilePath))
    }

  def encryptMsg(receiver: String, plainText: String): String =
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
            s"gpg --batch --recipient $receiver --encrypt --armor $toEncryptFilePath"
          )
        ) +
        //Show encrypted data
        this.formOutputToHtml(input =
          this.shellCmdWrapper(cmd = "cat " + toEncryptFilePath + ".asc")
        ) +
        //Rm plainText
        this.formOutputToHtml(input = this.shellCmdWrapper(cmd = "rm " + toEncryptFilePath)) +
        //Rm encryptedData
        this.formOutputToHtml(input =
          this.shellCmdWrapper(cmd = "rm " + toEncryptFilePath + ".asc")
        )
    }

  def decryptMsg(author: String, encryptedText: String): String =
    FileHelper.writeToFile(
      content = encryptedText,
      filename = "to-decrypt.txt.asc",
      FileHelper.generateNewWorkDirPrefix
    ) match {
      case Failure(exception)         => this.handleWriteException(exception)
      case Success(toDecryptFilePath) =>
        //Decrypt
        this.formOutputToHtml(input =
          this.shellCmdWrapper(cmd =
            s"gpg --batch --recipient $author --decrypt $toDecryptFilePath > $toDecryptFilePath.decrypted"
          )
        ) +
        //Show encrypted data
        this.formOutputToHtml(input =
          this.shellCmdWrapper(cmd = "cat " + toDecryptFilePath + ".decryped")
        ) +
        //Rm encrypted
        this.formOutputToHtml(input = this.shellCmdWrapper(cmd = "rm " + toDecryptFilePath)) +
        //Rm decrypted
        this.formOutputToHtml(input =
          this.shellCmdWrapper(cmd = "rm " + toDecryptFilePath + ".decrypted")
        )
    }

}
