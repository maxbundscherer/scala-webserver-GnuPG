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
    s"<li><a href='getWorkDirPath'>Get work dir path</a></li>" +
    s"<li><a href='getPublicKeys'>Get public keys</a></li>" +
    s"</ul>" +
    s"" +
    s"</p>" +
    this.Template.getTemplateFooter

  def getWorkDirPath: String =
    this.Template.getTemplateHeader(
      metaTitle = "WorkDirPath",
      title = "Work Dir Path"
    ) +
    s"<p>${this.gnuPGService.getWorkDirPath}</p>" +
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
      "</head>" +
      "<body>" +
      s"<h1>${Config.Global.productName}</h1>" +
      s"<h2>$title</h2>"

    def getTemplateFooter: String =
      "</body>" +
      "</html>"

  }

}
