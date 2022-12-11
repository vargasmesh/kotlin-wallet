package application.command

import domain.model.AccountID
import domain.model.CreateAccountEvent

interface AccountRepository {
    fun createAccount(event: CreateAccountEvent): AccountID
}

class AccountService(
    private val accountRepository: AccountRepository
) {
    fun createAccount(event: CreateAccountEvent): AccountID {
        return accountRepository.createAccount(event)
    }
}