package org.example.api

class CreateAccountSpec extends BaseApiSpec {

    def "TC-API-001: создание счёта с корректными данными"() {
        when:
        def response = AccountApi.create([owner: 'Alice', balance: 500])
        def body = Json.parse(response)

        then:
        response.statusCode() == 201
        body.id != null
        body.owner == 'Alice'
        body.balance == 500
        body.frozen == false

        and: 'запись в БД совпадает с ответом'
        def row = sql.firstRow('select owner, balance, frozen from accounts where id = ?', [body.id])
        row.owner == 'Alice'
        row.balance == 500
        row.frozen == false
    }

    def "TC-API-002: некорректные данные при создании счёта: #title"() {
        when:
        def response = AccountApi.create(requestBody)

        then:
        response.statusCode() == 400
        Json.parse(response).code == 'VALIDATION_ERROR'

        where:
        title                     | requestBody
        'пустое имя'              | [owner: '', balance: 100]
        'имя не передано'         | [balance: 100]
        'отрицательный баланс'    | [owner: 'Alice', balance: -1]
        'баланс не передан'       | [owner: 'Alice']
        'три знака после запятой' | [owner: 'Alice', balance: 10.001]
    }
}
