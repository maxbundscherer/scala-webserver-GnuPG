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
    s"<ul>" +
    s"<li><a href='getWorkDirFiles'>Get work dir files</a></li>" +
    s"<li><a href='getPublicKeys'>Get public keys</a></li>" +
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

  private object Template {

    def getTemplateHeader(metaTitle: String, title: String): String =
      "<html>" +
      "<head>" +
      s"<title>$metaTitle - ${Config.Global.productName}</title>" +
      s"<style>" +
      "p, li, h1, h2, h3 {font-family: \"Verdana\"}" +
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
      s"<h2>$title</h2>" +
      s"<hr>"

    def getTemplateFooter: String =
      "<hr>" +
      "<button onClick='goBack()'>Back</button>" +
      "</body>" +
      "</html>"

  }

}
