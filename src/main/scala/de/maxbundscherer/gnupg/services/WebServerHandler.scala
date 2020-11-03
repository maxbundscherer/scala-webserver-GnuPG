package de.maxbundscherer.gnupg.services

import de.maxbundscherer.gnupg.utils.Configuration

import akka.actor.ActorSystem
import akka.event.LoggingAdapter

private class WebServerHandler(gnuPGService: GnuPGService)(implicit
    actorSystem: ActorSystem,
    log: LoggingAdapter
) extends Configuration {}
