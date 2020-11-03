package de.maxbundscherer.gnupg.services

import de.maxbundscherer.gnupg.utils.Configuration

import akka.actor.ActorSystem

class WebServerService(gnuPGService: GnuPGService)(implicit actorSystem: ActorSystem)
    extends Configuration {}
