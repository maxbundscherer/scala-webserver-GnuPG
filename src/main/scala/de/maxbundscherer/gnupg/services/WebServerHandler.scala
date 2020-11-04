package de.maxbundscherer.gnupg.services

import de.maxbundscherer.gnupg.utils.Configuration

import org.slf4j.Logger

private class WebServerHandler(gnuPGService: GnuPGService)(implicit log: Logger)
    extends Configuration {

  def home: String =
    this.Template.getTemplateHeader(
      metaTitle = "Home",
      title = "Welcome"
    ) +
    s"<p>" +
    s"" +
    s"<p>We still have things to do... (see README.md)</p>" +
    s"" +
    s"<ul>" +
    s"<li>Product-Name: '${Config.Global.productName}'</li>" +
    s"<li>Product-Version: '${de.maxbundscherer.gnupg.utils.BuildInfo.version}'</li>" +
    s"<li>Scala-Version: '${de.maxbundscherer.gnupg.utils.BuildInfo.scalaVersion}'</li>" +
    s"<li>SBT-Version: '${de.maxbundscherer.gnupg.utils.BuildInfo.sbtVersion}'</li>" +
    s"<li>Host:Port: '${Config.WebServer.host}:${Config.WebServer.port}'</li>" +
    s"<li>WorkDir: '${Config.GnuPGService.workDir}'</li>" +
    s"</ul>" +
    s"" +
    s"<ul>" +
    s"<li>${this.Template.getTemplateLink("Get work dir files", "getWorkDirFiles")}</li>" +
    s"<li>${this.Template.getTemplateLink("Get public keys", "getPublicKeys")}</li>" +
    s"<li>${this.Template.getTemplateLink("Get private keys", "getPrivateKeys")}</li>" +
    s"<li>${this.Template.getTemplateLink("Write file to workDir", "writeTestFile")}</li>" +
    s"<li>${this.Template.getTemplateLink("Encrypt msg", "encryptMsg")}</li>" +
    s"</ul>" +
    s"" +
    s"</p>" +
    this.Template.getTemplateFooter

  def getWorkDirFiles: String =
    this.Template.getTemplateHeader(
      metaTitle = "WorkDirFiles",
      title = "Work Dir Files"
    ) +
    s"<p>${this.gnuPGService.getWorkDirFiles}</p>" +
    this.Template.getTemplateFooter

  def getPublicKeys: String =
    this.Template.getTemplateHeader(
      metaTitle = "GetPublicKeys",
      title = "Public keys"
    ) +
    s"<p>${this.gnuPGService.getPublicKeys}</p>" +
    this.Template.getTemplateFooter

  def getPrivateKeys: String =
    this.Template.getTemplateHeader(
      metaTitle = "GetPrivateKeys",
      title = "Private keys"
    ) +
    s"<p>${this.gnuPGService.getPrivateKeys}</p>" +
    this.Template.getTemplateFooter

  def writeTestFile: String =
    this.Template.getTemplateHeader(
      metaTitle = "WriteTestFile",
      title = "Write Test File"
    ) +
    s"<p>${this.gnuPGService.writeTestFile}</p>" +
    this.Template.getTemplateFooter

  def encryptMsg: String =
    this.Template.getTemplateHeader(
      metaTitle = "EncryptMsg",
      title = "Encrypt Msg"
    ) +
    this.Template.getTemplateForm(
      form = this.Template.TemplateForm(
        items = Vector(
          this.Template.TemplateFormTextBox(hName = "receiverMail", label = "Receiver-Mail"),
          this.Template.TemplateFormTextArea(hName = "plainText", label = "Message (Plain Text)")
        ),
        hMethod = "post",
        hAction = "encryptMsg2"
      )
    ) +
    this.Template.getTemplateFooter

  def encryptMsg2(receiverMail: String, plainText: String): String =
    this.Template.getTemplateHeader(
      metaTitle = "EncryptMsg2",
      title = "Encrypt Msg Finish"
    ) +
    s"<p>${this.gnuPGService.encryptMsg(receiverMail = receiverMail, plainText = plainText)}</p>" +
    this.Template.getTemplateFooter

  def decryptMsg2(authorMail: String, encryptedText: String): String =
    this.Template.getTemplateHeader(
      metaTitle = "DecryptMsg2",
      title = "Decrypt Msg Finish"
    ) +
    s"<p>${this.gnuPGService.decryptMsg(authorMail = authorMail, encryptedText = encryptedText)}</p>" +
    this.Template.getTemplateFooter

  private object Template {

    abstract class TemplateFormItem(hName: String, label: String) {
      def convertToHtml(): String
    }

    case class TemplateFormTextBox(hName: String, label: String)
        extends TemplateFormItem(hName = hName, label = label) {
      override def convertToHtml(): String =
        s"<label>$label</label><br><input type='text' name='$hName'><br>"
    }

    case class TemplateFormTextArea(hName: String, label: String)
        extends TemplateFormItem(hName = hName, label = label) {
      override def convertToHtml(): String =
        s"<label>$label</label><br><textarea name='$hName'></textarea><br>"
    }

    case class TemplateForm(items: Vector[TemplateFormItem], hMethod: String, hAction: String)

    def getTemplateHeader(metaTitle: String, title: String): String =
      "<html>" +
      "<head>" +
      s"<title>$metaTitle - ${Config.Global.productName}</title>" +
      s"<style>" +
      "p, li, h1, h2, h3, label, input, button, textarea {font-family: \"Verdana\"}" +
      s"</style>" +
      s"" +
      s"" +
      s"<script>" +
      s"function goBack() {" +
      s"window.history.back();" +
      s"}" +
      s"</script>" +
      s"" +
      "</head>" +
      "<body>" +
      s"<h1>${Config.Global.productName}</h1>" +
      s"<p>by Maximilian Bundscherer</p>" +
      s"<h2>$title</h2>" +
      s"<hr>"

    def getTemplateFooter: String =
      "<hr>" +
      this.getTemplateLink("Go back", "javascript:goBack()") +
      "<br>" +
      "" +
      this.getTemplateLink("Go to home", "/") +
      "</body>" +
      "</html>"

    def getTemplateLink(label: String, href: String): String = s"<a href='$href'>$label</a>"

    def getTemplateForm(form: TemplateForm): String = {
      val formData: String = form.items.map(_.convertToHtml() + "<br>").mkString
      s"<form action='${form.hAction}' method='${form.hMethod}'>" +
      s"$formData" +
      "<button type='submit'>Send</button>" +
      "</form>" +
      ""
    }

  }

}
