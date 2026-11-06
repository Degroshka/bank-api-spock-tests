package org.example.api

import groovy.json.JsonSlurper
import io.restassured.response.Response

/** Разбор тела ответа в Map. Числа с дробной частью приходят как BigDecimal, без потери точности. */
class Json {

    static Map parse(Response response) {
        new JsonSlurper().parseText(response.asString()) as Map
    }
}
