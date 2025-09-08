package org.example;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RequestMetrics {

    private final MeterRegistry registry;

    @Value("${pod.name:unknown}") // Получаем имя пода из переменной окружения
    private String podName;

    private Counter httpRequestCounter;


    public RequestMetrics(MeterRegistry registry) {
        this.registry = registry;
    }

    @PostConstruct
    public void init() {
        httpRequestCounter = registry.counter(
                "http.requests.total",
                Tags.of("pod", podName)  // Добавляем podName как лейбл
        );
    }

    public void incrementHttpRequestCount() {
        httpRequestCounter.increment();
    }
}
