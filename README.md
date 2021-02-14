# Spring Microservices in Action

The goal of this project is to learn how to build microservice-based applications. I opted for Java due to the rich ecosystem offered by Spring.
For this project I follow the book [Spring Microservices in Action](https://www.amazon.com/Spring-Microservices-Action-John-Carnell/dp/1617293989).

I created a seperate library for sharing infrastructure-style code between microservices, which can be found in the [smia-lib](https://github.com/melvinkoopmans/smia-lib/) repository.

Below you'll find a listing of all the services and what they do:

| Service | Description  |
|---------|--------------|
| configserver | The configuration server uses [Spring Cloud Config](https://cloud.spring.io/spring-cloud-config/reference/html/) to provide externalized configuration in the distributed system of microservices. |
| eurekaserver | This service allows for Service Registration and Discovery using [Netflix Eureka](https://github.com/Netflix/eureka). |
| licenses | This service manages licenses of various organizations. It communicates with the organization service to obtain data from the organizations. |
| organizations | This service manages all the organizations. |
| zuulserver | This service acts as a API Gateway using [Netflix Zuul](https://github.com/Netflix/zuul). Zuul interacts with the Eureka server to discover services in the distributed system. It's also the place where cross-cutting concerns are organized, such as setting correlation ID's for distributed tracing and authentication. |

