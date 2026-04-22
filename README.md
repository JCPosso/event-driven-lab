# Event Driven Lab

Microservicios con Spring Boot + RabbitMQ + Docker Compose.

## Objetivo

Construir dos servicios:

- Productor: publica mensajes via API REST.
- Consumidor: escucha y procesa mensajes desde RabbitMQ.

## Estructura

```text
event-driven-lab/
|-- producer-service/
|-- consumer-service/
|-- .devcontainer/
`-- README.md
```

## Requisitos

- Java 17
- Maven
- Docker
- Docker Hub

## 1) Configurar Codespaces

1. Crea el repositorio en GitHub.
2. Agrega `.devcontainer/devcontainer.json`.
3. Crea el Codespace desde `main`.


## 2) Producer Service

Dependencias: Spring Web, Spring AMQP.

Configuracion clave en `producer-service/src/main/resources/application.properties`:

```properties
server.port=8080
spring.rabbitmq.host=rabbitmq
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
app.rabbitmq.exchange=messages.exchange
app.rabbitmq.queue=messages.queue
app.rabbitmq.routingkey=messages.routingkey
```

## 3) Consumer Service

Dependencia: Spring AMQP.

Configuracion clave en `consumer-service/src/main/resources/application.properties`:

```properties
spring.rabbitmq.host=rabbitmq
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
app.rabbitmq.queue=messages.queue
```

## 4) Docker Hub

Ejecuta en cada servicio:

```bash
mvn clean package
docker build -t <servicio> .
docker tag <servicio> <tu-usuario>/<servicio>:latest
docker login -u <tu-usuario>
docker push <tu-usuario>/<servicio>:latest
```

### Evidencia

- [ ] Imagenes creadas
- [ ] Push de producer
- [ ] Push de consumer

```md
![Evidencia paso 4 imagenes](docs/evidencias/paso-4-docker-images.png)
![Evidencia paso 4 push producer](docs/evidencias/paso-4-docker-push-producer.png)
![Evidencia paso 4 push consumer](docs/evidencias/paso-4-docker-push-consumer.png)
```

## 5) Docker Compose

Crea `docker-compose.yml` en la raiz:

```yaml
version: '3.8'

services:
  rabbitmq:
    image: rabbitmq:management
    container_name: rabbitmq
    hostname: rabbitmq
    ports:
      - "5672:5672"
      - "15672:15672"
    networks:
      - event_network

  producer:
    image: juancamiloposso/producer-service
    container_name: producer-service
    ports:
      - "8080:8080"
    depends_on:
      - rabbitmq
    environment:
      - SPRING_RABBITMQ_HOST=rabbitmq
      - SPRING_RABBITMQ_PORT=5672
      - SPRING_RABBITMQ_USERNAME=guest
      - SPRING_RABBITMQ_PASSWORD=guest
    networks:
      - event_network

  consumer:
    image: juancamiloposso/consumer-service
    container_name: consumer-service
    depends_on:
      - rabbitmq
    environment:
      - SPRING_RABBITMQ_HOST=rabbitmq
      - SPRING_RABBITMQ_PORT=5672
    networks:
      - event_network

networks:
  event_network:
    driver: bridge
```

Levantar servicios:

```bash
docker compose up -d
docker logs -f consumer-service
```

Subir cambios al repositorio:

```bash
git add docker-compose.yml README.md
git commit -m "Add docker-compose setup for step 5"
git push
```

### Evidencia

- [ ] `docker compose up -d`
- [ ] RabbitMQ UI
- [ ] Mensaje enviado y procesado

```md
![Evidencia paso 5 compose](docs/evidencias/paso-5-compose-up.png)
![Evidencia paso 5 rabbitmq](docs/evidencias/paso-5-rabbitmq-ui.png)
![Evidencia paso 5 logs](docs/evidencias/paso-5-consumer-log.png)
```

## Verificacion final

1. RabbitMQ activo.
2. Productor responde en 8080.
3. Consumidor procesa mensajes.
4. Imagenes publicadas en Docker Hub.
