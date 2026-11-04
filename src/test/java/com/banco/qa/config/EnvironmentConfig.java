package com.banco.qa.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public record EnvironmentConfig(String environment, String baseUrl) {

    public static EnvironmentConfig load() {
        String environment = System.getProperty("env", "qa").trim().toLowerCase();
        if (!environment.equals("qa") && !environment.equals("uat")) {
            throw new IllegalArgumentException("Ambiente no soportado: " + environment + ". Usa qa o uat.");
        }

        Properties properties = new Properties();
        String resource = "/config/" + environment + ".properties";
        try (InputStream input = EnvironmentConfig.class.getResourceAsStream(resource)) {
            if (input == null) {
                throw new IllegalStateException("No se encontró la configuración " + resource);
            }
            properties.load(input);
        } catch (IOException exception) {
            throw new IllegalStateException("No se pudo leer la configuración " + resource, exception);
        }

        String baseUrl = System.getProperty("api.base-url", properties.getProperty("api.base-url"));
        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalStateException("Falta configurar api.base-url para el ambiente " + environment);
        }
        return new EnvironmentConfig(environment, baseUrl.replaceAll("/+$", ""));
    }
}
