package com.java.backend.mediator.Consumer;

public interface IConsumerService {
    Consumer findConsumerByUserId(String id);
    Consumer createConsumer(Consumer consumer);
    Consumer saveConsumer(Consumer consumer);
}
