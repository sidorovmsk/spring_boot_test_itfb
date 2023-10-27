package spring_boot_java.test_itfb.controllers;

import io.micrometer.prometheus.PrometheusMeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PrometheusController {

    private final PrometheusMeterRegistry prometheusRegistry;

    @Autowired
    public PrometheusController(PrometheusMeterRegistry prometheusRegistry) {
        this.prometheusRegistry = prometheusRegistry;
    }

    @GetMapping("/metrics")
    public String metrics() {
        log.info("GET request to /metrics");
        return prometheusRegistry.scrape();
    }
}
