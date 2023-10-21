package spring_boot_java.test_itfb.controllers;

import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrometheusController {

    private PrometheusMeterRegistry prometheusRegistry;

    @Autowired
    public PrometheusController(PrometheusMeterRegistry prometheusRegistry) {
        this.prometheusRegistry = prometheusRegistry;
    }

    @GetMapping("/metrics")
    public String metrics() {
        return prometheusRegistry.scrape();
    }
}
