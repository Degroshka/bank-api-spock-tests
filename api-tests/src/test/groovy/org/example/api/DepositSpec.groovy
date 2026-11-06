package org.example.api

class DepositSpec extends BaseApiSpec {

    def "TC-API-010: пополнение увеличивает баланс"() {
        given:
        def id = newAccount('Alice', 500)

        when:
        def response = AccountApi.deposit(id, 200)

        then:
        response.statusCode() == 200
        Json.parse(response).balance == 700

        and: 'баланс в БД обновился'
        sql.firstRow('select balance from accounts where id = ?', [id]).balance == 700
    }
}
