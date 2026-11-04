package com.banco.qa.clients;

import com.banco.qa.config.EnvironmentConfig;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class CuentaClient {
    private final EnvironmentConfig config;

    public CuentaClient(EnvironmentConfig config) {
        this.config = config;
    }

    public Response crearCuenta(String clienteId, String tipo, double saldoInicial) {
        return given()
                .baseUri(config.baseUrl())
                .contentType("application/json")
                .body(Map.of("tipo", tipo, "saldoInicial", saldoInicial))
                .when()
                .post("/api/clientes/{clienteId}/cuentas", clienteId);
    }

    public Response consultarCuenta(String cuentaId) {
        return given()
                .baseUri(config.baseUrl())
                .when()
                .get("/api/cuentas/{cuentaId}", cuentaId);
    }

    public Response transferir(String cuentaOrigen, String cuentaDestino, double monto) {
        return given()
                .baseUri(config.baseUrl())
                .contentType("application/json")
                .body(Map.of(
                        "cuentaOrigen", cuentaOrigen,
                        "cuentaDestino", cuentaDestino,
                        "monto", monto
                ))
                .when()
                .post("/api/transferencias");
    }

    public Response bloquearCuenta(String cuentaId) {
        return given()
                .baseUri(config.baseUrl())
                .when()
                .post("/api/cuentas/{cuentaId}/bloquear", cuentaId);
    }

    public Response retirar(String cuentaId, double monto) {
        return given()
                .baseUri(config.baseUrl())
                .contentType("application/json")
                .body(Map.of("monto", monto))
                .when()
                .post("/api/cuentas/{cuentaId}/retiros", cuentaId);
    }

    public Response reiniciarDatos() {
        return given()
                .baseUri(config.baseUrl())
                .when()
                .delete("/api/test-data");
    }
}
