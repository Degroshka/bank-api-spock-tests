package org.example.api

import groovy.json.JsonOutput
import io.restassured.RestAssured
import io.restassured.config.DecoderConfig
import io.restassured.config.RestAssuredConfig
import io.restassured.http.ContentType
import io.restassured.response.Response
import io.restassured.specification.RequestSpecification

/**
 * Клиент к API сервиса: один метод на один эндпоинт.
 * Адрес берётся из -DbaseUrl=..., по умолчанию http://localhost:8080
 */
class AccountApi {

    static String baseUrl() {
        System.getProperty('baseUrl', 'http://localhost:8080')
    }

    private static RequestSpecification request() {
        RestAssured.given()
                .config(RestAssuredConfig.config()
                        .decoderConfig(DecoderConfig.decoderConfig().defaultContentCharset('UTF-8')))
                .baseUri(baseUrl())
                .contentType(ContentType.JSON.withCharset('UTF-8'))
    }

    static Response create(Map body) {
        request().body(JsonOutput.toJson(body)).post('/api/accounts')
    }

    static Response get(Object id) {
        request().get("/api/accounts/${id}".toString())
    }

    static Response deposit(Object id, Object amount) {
        request().body(JsonOutput.toJson([amount: amount])).post("/api/accounts/${id}/deposit".toString())
    }

    static Response withdraw(Object id, Object amount) {
        request().body(JsonOutput.toJson([amount: amount])).post("/api/accounts/${id}/withdraw".toString())
    }

    static Response freeze(Object id) {
        request().post("/api/accounts/${id}/freeze".toString())
    }

    static Response unfreeze(Object id) {
        request().post("/api/accounts/${id}/unfreeze".toString())
    }

    static Response transfer(Object fromId, Object toId, Object amount) {
        request().body(JsonOutput.toJson([fromId: fromId, toId: toId, amount: amount])).post('/api/transfers')
    }
}
