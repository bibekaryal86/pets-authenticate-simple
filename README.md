# pets-authenticate-layer-simple

This is a simple app which provides authentication service/server for Personal Expenses Tracking System application.
This app is a scaled down version of `pets-authenticate-layer` app found
here: https://github.com/bibekaryal86/pets-authenticate-layer. The other app uses Spring Boot with RestTemplate
framework to do the exact same function as this app - `pets-authenticate-layer-simple`. However, this `simple` app does
not use any kind of Spring or any other frameworks. The web application framework is provided by Jetty server with Java
Servlets providing the endpoints. Interactions to other REST services are done by Java native HttpClient.

Because of absence of any frameworks, the footprint of this app is very grounded (~6 MB jar archive and ~100 MB runtime
JVM memory) as opposed to when using Spring Boot (~45 MB archive and ~350 MB memory). And, as a result, the app can be
deployed and continuously run 24/7 on Google Cloud Platform App Engine's free tier.

To run the app, we need to supply the following environment variables:

* Port
    * PORT: This is optional, and if it is not provided port defaults to 8080
* Active Profile
    * SPRING_PROFILES_ACTIVE (development, docker, production)
* PETS Service Security Details:
    * BASIC_AUTH_USR_PETSSERVICE (auth username of pets-service)
    * BASIC_AUTH_PWD_PETSSERVICE (auth password of pets-service)
* JWT Signing Key
    * SECRET_KEY: to sign JWT tokens (not needed in `pets-authenticate-layer`)

The app has been deployed to GCP:

* https://pets-authenticate.appspot.com/pets-authenticate/tests/ping
