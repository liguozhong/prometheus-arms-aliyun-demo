package com.monitise.prometheus_demo;

import io.prometheus.client.spring.boot.EnablePrometheusEndpoint;
import io.prometheus.client.spring.boot.EnableSpringBootMetricsCollector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// sets up the prometheus endpoint /prometheus-metrics
@EnablePrometheusEndpoint
// exports the data at /metrics at a prometheus endpoint
@EnableSpringBootMetricsCollector
public class PrometheusDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(PrometheusDemoApplication.class, args);
    }
}
