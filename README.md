# Twenty One ("Eenentwintigen")
[![CircleCI](https://circleci.com/gh/ziadmoubayed/twentyone.svg?style=svg)](https://circleci.com/gh/ziadmoubayed/twentyone)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=ziadmoubayed_twentyone&metric=alert_status)](https://sonarcloud.io/dashboard?id=ziadmoubayed_twentyone)

Twenty One is a dutch variant of Black Jack. When the game starts it presents a REPL which can be used to play the game.
The normal flow of the game is as follows:
  - Enter the players' names.
  - Players play one by one until all players are bust or standing.
  - Bank takes turn playing.
  - Winners are chosen.

## Installation

You can run Twenty One using Dockerfile or building from source.

### Docker (Recommended)
Twenty One is very easy to install and deploy in a Docker container.

```sh
$ docker pull docker.pkg.github.com/ziadmoubayed/twentyone/twentyone:1.1
```
This will pull the image and the necessary dependencies.
Note: You need to start the docker in interactive mode to use the REPL.

```sh
$ docker run -it docker.pkg.github.com/ziadmoubayed/twentyone/twentyone:1.1
```

### Building for source
Choose and download a [release](https://github.com/ziadmoubayed/twentyone/releases) from github. Make sure to have **Maven** and **Java 11** installed.

```sh
$ mvn clean package
$ java -jar target/twentyone-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## Game Rules


### Todos

 - Increase Code Coverage.
 - Separate backend from UI.
 - Add Cheats :)

License
----

MIT