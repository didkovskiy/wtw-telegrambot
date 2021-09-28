# WhatToWatch Telegram Bot

<img alt="logo" height="200" src="https://github.com/didkovskiy/wtw-telegrambot/blob/main/docs/logo.PNG" width=auto/>

Hello, WhatToWatch Telegram Bot at your service 😏.
I'm here to help you to find a movie to watch or at least to steer you in the right direction to achieve this goal.

## What the Telegram user can do
- User can find movie by typing keywords.
- User can find a completely random movie from most popular.
- User can save movies in 'Watch Later' list and see results.
- User can remove movies and clear the 'Watch Later' list.
- User can search YouTube trailer of a specific movie.
- User can get help about working with bot.
- Admin has ability to see bot statistics.

#### These behaviors are shown below:

<img alt="logo" height="220" src="https://github.com/didkovskiy/wtw-telegrambot/blob/main/docs/watchlater.gif" width=auto/>

/random command allows you to search a movie or get it by random: 

<img alt="logo" height="240" src="https://github.com/didkovskiy/wtw-telegrambot/blob/main/docs/random.gif" width=auto/>
<img alt="logo" height="290" src="https://github.com/didkovskiy/wtw-telegrambot/blob/main/docs/randomkey.gif" width=auto/>

Finding a movie trailer is easy:

<img alt="logo" height="300" src="https://github.com/didkovskiy/wtw-telegrambot/blob/main/docs/trailer.gif" width=auto/>

Statistics available only to admins:

<img alt="logo" height="300" src="https://github.com/didkovskiy/wtw-telegrambot/blob/main/docs/stat.gif" width=auto/>

## Deployment (not available now)
Required software:
- terminal for running bash scripts
- docker
- docker-compose

Run bash script to deploy application:

`$ bash start.sh ${bot_username} ${bot_token}`

## Technologies used
- [Spring Boot](https://spring.io/projects/spring-boot) 2.5.4 as a skeleton framework
- MySQL database as a database for saving telegram user and his 'watch later' list
- Spring Boot Starter Data JPA
- [Flyway](https://mvnrepository.com/artifact/org.flywaydb/flyway-core) database migration tool
- [Telegram Bots Spring Boot starter](https://mvnrepository.com/artifact/org.telegram/telegrambots-spring-boot-starter) 5.3.0
- [Unirest](https://github.com/kong/unirest-java) 3.13.0 for working with REST calls

## License 
Apache License 2.0 - see the [LICENSE](https://github.com/didkovskiy/wtw-telegrambot/blob/main/LICENSE) file for details.

## Contributions
Please suggest new features via [github Issue](https://github.com/didkovskiy/wtw-telegrambot/issues/new).
