package spring_boot_java.test_itfb.config;

import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PrometheusConfig prometheusConfig() {
        return key -> {
            // Здесь вы можете определить настройки PrometheusConfig
            // Например, версию или дополнительные параметры
            if ("prometheus.someProperty".equals(key)) {
                return "someValue";
            }
            return null; // Возвращаем null для остальных настроек
        };
    }

    @Bean
    public PrometheusMeterRegistry prometheusMeterRegistry(PrometheusConfig config) {
        return new PrometheusMeterRegistry(config);
    }
}
