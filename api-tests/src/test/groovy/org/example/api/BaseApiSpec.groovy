package org.example.api

import groovy.sql.Sql
import spock.lang.Shared
import spock.lang.Specification

abstract class BaseApiSpec extends Specification {

    @Shared
    Sql sql

    def setupSpec() {
        sql = Db.connect()
    }

    def cleanupSpec() {
        sql?.close()
    }

    /** Создаёт счёт через API и возвращает его id. Для подготовки данных, а не для проверки создания. */
    protected Long newAccount(String owner = 'Alice', def balance = 500) {
        def response = AccountApi.create([owner: owner, balance: balance])
        assert response.statusCode() == 201
        Json.parse(response).id as Long
    }
}
