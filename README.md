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
TwentyOne is a game of chance. The chance of winning is based on the cards dealt by the program. The game is similar to Blackjack, but differs in some areas, so please read this section carefully.
All players in the game place their bets after receiving their first card. Once everyone has placed their bets, the participants get a second card.
One by one, the players of the game get the opportunity to play until they are finished. Each play, the players have the option to ‘stand’ (hold your total and end your turn, you cannot play any further), ‘hit’ (ask for a card to bring your points as close as possible to 21), or perform special actions (here only ‘split’). If a player has more than 21 points in her hand, she is ‘bust’, and her bets are lost.
If all players are ready (stand or bust) the bank must play (only if there are players who are not bust). The rules for the bank are simple: The bank must hit when it has a total of 16 points or less and must stand with a total of 17 points or more. When the bank and player have the same number of points, the bank wins. If the bank has more than 21 points, the bank is bust and all players that are standing, win.
When a player gets two identical cards, she can choose to ‘split’. This means that the cards are placed next to each other on the table and the player can play twice, one game per card.3

The number of points for the cards is as follows:
>King 3 points, queen 2 points, jack 1 point.
>Ace is 1 or 11 points of your choice.
>Cards 2 to 10 have their normal point value.
>The ‘suit’ of the card is not important.
>The Joker does not play

### Todos

 - Increase Code Coverage.
 - Separate backend from UI.
 - Add Cheats :)

License
----

MIT