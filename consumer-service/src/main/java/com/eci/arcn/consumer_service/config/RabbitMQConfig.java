package com.eci.arcn.consumer_service.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${app.rabbitmq.queue}")
    private String queueName;

    // Declarar la cola aqui asegura que exista si el consumidor inicia primero.
    // Es importante que los parametros (nombre, durabilidad, etc.) coincidan
    // con la declaracion en el productor.
    @Bean
    Queue queue() {
        return new Queue(queueName, true); // Debe ser durable=true igual que en productor
    }
    // Nota: No es estrictamente necesario declarar el Exchange y Binding aqui
    // si el Productor ya lo hace, pero no hace dano y aumenta la resiliencia.
    // Si los declaras, asegurate que los nombres y tipos coincidan.
}
