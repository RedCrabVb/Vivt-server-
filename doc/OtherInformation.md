# Program
This program is responsible for taking data from the databases and then sending it to the client.
The architecture of the application is currently being finalized.
The user interaction contract will be described below.
It is worth noting that design is in progress at the moment,
and this process is very sloppy, so that in the future everything can change a lot.

--------------
# Architecture

## Server:
* Command interface, GET inquiry(API).
* Database(JSON).
* Logging
* Interface for interacting with the administrator
* Server sends notifications
* Work with tasks in lk | "Elínos"

## Client:
* Client working with server API(Model)
* User interface(View)
* Controller handling user actions(controller)