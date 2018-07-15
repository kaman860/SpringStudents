# Spring Boot REST API for students

### Running inside Docker

You can run this spring boot app after  you have docker and docker-compose installed on your system.
For running this app inside docker, please follow the following steps:

```sh
cd docker
docker-compose up
```
This will create the basic docker service and expose `PORT:8080` to you.

You can then visit localhost:8080 to send `GET`, `PATCH`, `POST`, or `DELETE` requests.

If you want to stop the running docker service, run:
```sh
docker-compose down
```
### Running on local PC

By default, you can clone this code, or the `spring.jar` file in your system. Then you must have postgres installed on your student, and it must have the following properties:
```sh
user = postgres
database = studentdb
password = postgres123
```