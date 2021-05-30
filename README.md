# Instructions:
* Intellij idea - IDE for development
* [Connecting Gson in IntelliJ IDEA](http://blog.harrix.org/article/7348)
* The application requires configs to work:
	1. Config.json - for the application to work,
	2. log.config - for data logging
* It is advisable to run the program with parameters, otherwise, the default data will be used.
  Example: '-configPath Config.json'

--------------
# About programm
This program is responsible for taking data from the databases and then sending it to the client.
The architecture of the application is currently being finalized.
The user interaction contract will be described below.
It is worth noting that design is in progress at the moment,
and this process is very sloppy, so that in the future everything can change a lot.

--------------
# Сontract(API):
* Registration.
* Get a list of all messages.
* Get personalized data.
* Get a class schedule.
* Get academic achievement.
* Get news feed.
* Close connect

--------------
# Architecture

## Server:
* Command interface(API).
* Database.
* The server is waiting for a connection,
  processing commands and interacting
  with the database.
* Logging
* Interface for interacting with the administrator

## Client:
* Client working with server API(Model)
* User interface(View)
* Controller handling user actions(controller)