package org.example

import spock.lang.PendingFeature
import spock.lang.Specification


class BankAccountSpec extends Specification {
    private def initiateFreezeAccount(double balance) {
        def A = new BankAccount('Alice', balance)
        A.freeze()
        return A
    }

    def "TC-1.1.1: Пополнение увеличивает баланс"() {
        given:
        def A = new BankAccount('Alice', 500.0)

        when:
        A.deposit(200.0)

        then:
        A.balance == 700
    }

    def "TC-1.1.2: Снятие уменьшает баланс"() {
        given:
        def A = new BankAccount('Alice', 500.0)

        when:
        A.withdraw(amount)

        then:
        A.balance == balance

        where:
        amount | balance
        50      | 450
        150     | 350
        265     | 235
    }
    def "TC-1.1.3: Владелец возвращается корректно"(){
        given:
        def A = new BankAccount('Tricia',500.0)

        when:
        String result =A.getOwner()

        then:
        result == 'Tricia'

    }
    def "TC-1.1.4: Новый счёт не заморожен"() {
        given:
        def A = new BankAccount('Pike',500.0)
        when:
        boolean result = A.isFrozen()
        then:
        !result
    }
    def "TC-1.2.1: Снятие больше, чем на счёте"() {
        given:
        def A = new BankAccount('Alice',250.0)
        when:
        A.withdraw(amount)
        then:
        thrown(error)
        where:
        amount|error
        260.0|IllegalStateException
        300.0|IllegalStateException
        250.0001|IllegalStateException
    }
    def "TC-1.2.2: Пополнение отрицательной суммой"() {
        given:
        def A = new BankAccount('Alice',300.0)
        when:
        A.deposit(amount)
        then:
        thrown(error)
        where:
        amount|error
        -260.0|IllegalArgumentException
        -300.0|IllegalArgumentException
        -250.0001|IllegalArgumentException
    }
    def "TC-1.2.3: Снятие нуля"() {
        given:
        def A = new BankAccount('Alice',250.0)
        when:
        A.withdraw(0)
        then:
        thrown(IllegalArgumentException)
    }
    def "TC-1.2.4: Создание счёта с отрицательным балансом"() {
        when:
        new BankAccount('Alice',-300)
        then:
        def except = thrown(IllegalArgumentException)
        except.message == "Initial balance cannot be negative"
    }
    def "#id: Создание счёта с пустым именем (#title) отклоняется"(){
        when:
        new BankAccount(owner,500)
        then:
        def except = thrown(IllegalArgumentException)
        except.message == "Owner name cannot be blank"
        where:
        id|owner|error|title
        'TC-1.2.5'|null|IllegalArgumentException|'null'
        'TC-1.2.6' |""|IllegalArgumentException|'пустая'
        'TC-1.2.7'|"   "|IllegalArgumentException|'пробелы'
    }
    def "TC-1.2.8: Пополнение нулём"(){
        given:
        def A = new BankAccount('Alice',500)
        when:
        A.deposit(0)
        then:
        def except = thrown(IllegalArgumentException)
        except.message=="Deposit amount must be positive"
    }
    def "TC-1.2.9: Баланс не меняется после неудачного снятия"(){
        given:
        def A = new BankAccount('Alice',250)
        when:
        A.withdraw(260)
        then:
        thrown(IllegalStateException)
        A.getBalance() == 250
    }
    @PendingFeature(reason = "BUG-1: конструктор принимает имя из одних цифр")
    def "TC-1.2.10: Имя счета из одних цифр отклоняется"() {
        when:
        new BankAccount("1234",300)
        then:
        thrown(IllegalStateException)
    }
    @PendingFeature(reason = "BUG-2: Депозит принимает NaN")
    def "TC-1.2.11: Пополнение баланса NaN"(){
        given:
        def A = new BankAccount('Alice',500)
        when:
        A.deposit(Double.NaN)
        then:
        thrown(IllegalArgumentException)
    }
    def "TC-1.3.1: Пополнение замороженного счёта"(){
        given:
        def A=initiateFreezeAccount(100)
        when:
        A.deposit(50)
        then:
        thrown(IllegalStateException)
    }
    def "TC-1.3.2: Снятие с замороженного счёта"(){
        given:
        def A=initiateFreezeAccount(100)
        when:
        A.withdraw(50)
        then:
        thrown(IllegalStateException)
        A.getBalance()==100
    }
    def "TC-1.3.3: Пополнение после разморозки"(){
        given:
        def A=initiateFreezeAccount(100)
        when:
        A.unfreeze()
        A.deposit(50)
        then:
        A.getBalance()==150
    }
    def "TC-1.3.4: Заморозка меняет флаг"(){
        given:
        def A = new BankAccount('Alice',100)
        when:
        A.freeze()
        then:
        A.isFrozen()
    }
    def "TC-1.3.5: Разморозка меняет флаг"(){
        given:
        def A=initiateFreezeAccount(100)
        when:
        A.unfreeze()
        then:
        !A.isFrozen()
    }
    def "TC-1.4.1: Успешный перевод"(){
        given:
        def sender=new BankAccount('Alice',300)
        def receiver=new BankAccount('Boris',50)
        when:
        sender.transferTo(receiver,100)
        then:
        sender.balance==200
        receiver.balance==150
    }
    def "TC-1.4.2: Перевод с замороженного счёта"(){
        given:
        def sender = new BankAccount('Alice', 300)
        def receiver = new BankAccount('Boris', 50)
        sender.freeze()
        when:
        sender.transferTo(receiver,100)
        then:
        def e = thrown(IllegalStateException)
        e.message=="Account is frozen"
    }
    def "TC-1.4.3: Перевод больше баланса"(){
        given:
        def sender=new BankAccount('Alice',100)
        def receiver=new BankAccount('Boris',0)
        when:
        sender.transferTo(receiver,500)
        then:
        def e =thrown(IllegalStateException)
        e.message=="Insufficient funds"
    }
    @PendingFeature(reason = "BUG-3: transferTo снимает деньги при переводе на замороженный счёт")
    def "TC-1.4.4: Перевод на замороженный счёт"() {
        given:
        def sender = new BankAccount('Alice', 300)
        def receiver = new BankAccount('Boris', 50)
        receiver.freeze()

        when:
        sender.transferTo(receiver, 100)

        then:
        thrown(IllegalStateException)
        verifyAll {
            sender.balance == 300
            receiver.balance == 50
        }
    }

}

