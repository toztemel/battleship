# Battleship

This is a REST API based Battleship game as described by the specifications of Lunatech.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Dependencies


```
Java 8
Maven
A rest client for testing, (e.g.Postman)
```

### Installing

A step by step series of examples that tell you how to get a development env running

Unzip the file

```
unzip battlefield.zip
cd battlefield
```

Build the project

```
mvn clean install
```



## Running the tests

Tests can simply run by maven
```
mvn test
```

### Integration tests

Tests named as *ITest.java are end-to-end integration tests. 
In these tests, usually two game instances are run at ports 7000 and 7001. 
Tests continue by two users firing at each other. 
Tests needs further improvement to support JWT-based Authentication and Authorization feature.
For the sake of simplicity and timing, security checks are switched off while running these automated tests.

## Built With

* [Javalin](https://javalin.io) - A lightweight web framework to serve User and Protocol API's
* [Maven](https://maven.apache.org/) - Dependency Management
* [Java Jwt](https://github.com/jwtk/jjwt) - Used for JWT-based authentication and authorization
* [Jersey-Client](https://jersey.github.io/documentation/latest/client.html) - JAX-RS Client to consume opponent's Protocol API

## Main Components
BattleshipGame.java configures and initializes the services and api's
