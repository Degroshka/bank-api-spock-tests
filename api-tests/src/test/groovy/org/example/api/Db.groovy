package org.example.api

import groovy.sql.Sql

/** Подключение к БД сервиса для проверки данных напрямую. Параметры: -DdbUrl, -DdbUser, -DdbPassword. */
class Db {

    static Sql connect() {
        Sql.newInstance(
                System.getProperty('dbUrl', 'jdbc:postgresql://localhost:5432/bank'),
                System.getProperty('dbUser', 'bank'),
                System.getProperty('dbPassword', 'bank'),
                'org.postgresql.Driver')
    }
}
