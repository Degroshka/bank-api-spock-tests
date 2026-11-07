package org.example.api

import groovy.sql.Sql

/** Подключение к БД сервиса для проверки данных напрямую.
 *  Параметры: -DdbUrl, -DdbUser, -DdbPassword. Порт по умолчанию берётся из переменной окружения DB_PORT. */
class Db {

    static String defaultUrl() {
        "jdbc:postgresql://localhost:${System.getenv('DB_PORT') ?: '5432'}/bank".toString()
    }

    static Sql connect() {
        Sql.newInstance(
                System.getProperty('dbUrl', defaultUrl()),
                System.getProperty('dbUser', 'bank'),
                System.getProperty('dbPassword', 'bank'),
                'org.postgresql.Driver')
    }
}