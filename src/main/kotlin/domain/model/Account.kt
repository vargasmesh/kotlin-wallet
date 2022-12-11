package domain.model

import org.javamoney.moneta.FastMoney

typealias AccountID = String

data class Account(val id: AccountID, val balance: FastMoney, val owner: String)

data class CreateAccountEvent(val requestID: String, val owner: String)