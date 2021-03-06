# Spring Microservices

The goal of this project is to learn how to build microservice-based applications. I opted for Java due to the rich ecosystem offered by Spring.
For this project I followed the book [Spring Microservices in Action](https://www.amazon.com/Spring-Microservices-Action-John-Carnell/dp/1617293989).

Below you'll find a listing of all the directories / services and what they do:

| Service | Description  |
|---------|--------------|
| common | Shared library used by all the microservices for infrastructure-style code. |
| configserver | The configuration server uses [Spring Cloud Config](https://cloud.spring.io/spring-cloud-config/reference/html/) to provide externalized configuration in the distributed system of microservices. |
| config | Contains all the configuration files used by the configserver. |
| eurekaserver | This service allows for Service Registration and Discovery using [Netflix Eureka](https://github.com/Netflix/eureka). |
| licenses | This service manages licenses of various organizations. It communicates with the organization service to obtain data from the organizations. |
| organizations | This service manages all the organizations. |
| zuulserver | This service acts as a API Gateway using [Netflix Zuul](https://github.com/Netflix/zuul). Zuul interacts with the Eureka server to discover services in the distributed system. It's also the place where cross-cutting concerns are organized, such as setting correlation ID's for distributed tracing and authentication. |
| authentication | This service is responsible for authentication and authorization of users using Oauth2. |
| zipkinserver | Provides a [Zipkin](https://zipkin.io/) service for distributed tracing together with [Spring Cloud Sleuth](https://spring.io/projects/spring-cloud-sleuth) | 

To run all of the services create a package for each of them and then run:

```bash
$ docker-compose up -d
```

I've used Confluent's images for their Kafka platform. You can access the Control Panel to manage your Kafka clusters by navigating to http://0.0.0.0:9021.