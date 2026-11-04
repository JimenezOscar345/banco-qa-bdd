package com.banco.qa.steps;

import com.banco.qa.clients.CuentaClient;
import com.banco.qa.config.EnvironmentConfig;
import io.cucumber.java.Before;
import io.restassured.response.Response;

public class Hooks {

    @Before
    public void prepararEscenario() {
        EnvironmentConfig config = EnvironmentConfig.load();
        if (config.environment().equals("qa")) {
            Response response = new CuentaClient(config).reiniciarDatos();
            if (response.statusCode() != 204) {
                throw new IllegalStateException("No se pudieron reiniciar los datos de QA en "
                        + config.baseUrl() + ". HTTP " + response.statusCode()
                        + ". Respuesta: " + response.asString());
            }
        }
    }
}
