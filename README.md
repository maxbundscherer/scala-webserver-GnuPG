# GnuPG Webserver (Scala)

[![](https://upload.wikimedia.org/wikipedia/de/thumb/6/6b/GnuPG.svg/1920px-GnuPG.svg.png)]()

![](./doc/screenshot.png)
GnuPG Webserver written in Scala based on GnuPG-Shell. Docker container included. 

``gnuPG - scala - akka http - shell``

Tested on macOS (with openJDK-11)

[![shields.io](http://img.shields.io/badge/license-Apache2-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.txt)

[![](https://github.com/maxbundscherer/scala-webserver-GnuPG/workflows/Release%20Workflow/badge.svg)](https://github.com/maxbundscherer/scala-webserver-GnuPG/actions)

Author: [Maximilian Bundscherer](https://bundscherer-online.de)

## Features

- ✅ Simple html-engine with webServer ``http://localhost:8080/`` included
- ✅ Simple shell to html-engine included
- ✅ Continuous Integration (GitHub Actions) included (triggered auto by tag)
- ✅ Docker container (build docker container from src-files)
- ✅ Import public gpg-keys
- ✅ Encrypt/Decrypt messages
- ✅ See your public gpg-keys
- ✅ See your private gpg-keys
- ✅ Debug tools included
- ...

## We still have things to do...

- ➡️ Add Docker container to GitHub-Packages and CI
- ➡️ Encrypt/Decrypt files
- ➡️ Administrate public-keys
- ➡️ Administrate secret-keys
- ➡️ Generate keys
- ➡️ Awesome front-end
- ➡️ Don't use shell-gpg as dependency
- ➡️ Server/Client...
- ➡️ Improve Encrypt/Decrypt message...
- ...

## Let's get started

### Required

- ``gnupg`` (``brew install gnupg`` on macOS)
- ``java``
- ``sbt`` (only as developer)

### Let's go (no developer)

- Read section ``Use release version from CI`` below.

### Let's go (developer)

- See [config](./src/main/scala/de/maxbundscherer/gnupg/utils/Configuration.scala)
- Run with ``sbt run``
- Triggered Restart (recommend) ``sbt ~reStart``
- Assembly (jar) with ``sbt assembly`` (see ``target/`` folder)
- Run jar with ``java -jar fileName.jar``

#### Docker way

- See [config](./src/main/scala/de/maxbundscherer/gnupg/utils/Configuration.scala)
- Run ``sbt docker:publishLocal``
- Run ``docker run -v <LOCAL-PATH-GPG-FOLDER>:/home/demiourgos728/.gnupg -v <LOCAL-PATH-WORKDIR-FOLDER>:/opt/docker/workDir --name "tmp-scala-gnupg-webserver" --rm -p 8080:8080 scala-gnupg-webserver:v0.1.3``

## Continuous Integration

### Use release version from CI

- Download jar from [last build](https://github.com/maxbundscherer/scala-webserver-GnuPG/actions?query=workflow%3A%22Release+Workflow%22)
- Extract zip-file
- Create dir from [config](./src/main/scala/de/maxbundscherer/gnupg/utils/Configuration.scala) (e.g. ``workDir``)
- Run ``java -jar artifact.jar``

### Release own version

- Merge to ``main``-branch
- Set version in ``build.sbt`` e.g. ``v0.0.2``
- Release tag on gitHub e.g. ``v0.0.2``