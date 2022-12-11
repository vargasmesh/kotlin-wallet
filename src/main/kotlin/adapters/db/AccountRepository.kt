package adapters.db

import com.github.ksuid.Ksuid
import com.google.gson.Gson
import domain.model.AccountEvents
import domain.model.AccountID
import domain.model.CreateAccountEvent
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

data class AccountCreatedEvent(val request_id: String, val owner: String)
data class AccountSummaryEvent(val owner: String, val balance: String)

class AccountRepository(val db: Database) : application.command.AccountRepository {
    override
    fun createAccount(event: CreateAccountEvent): AccountID {
        val gson = Gson()
        val accountID = transaction(db) {
            val accountCreatedEventID = Ksuid.newKsuid().toString()
            val summaryEventID = Ksuid.newKsuid().toString()
            val accountID = UUID.randomUUID().toString()

            Events.insert {
                it[id] = accountCreatedEventID
                it[type] = AccountEvents.ACCOUNT_CREATED.name
                it[entityID] = accountID
                it[data] = gson.toJson(AccountCreatedEvent(event.requestID, event.owner))
            }

            Events.insert {
                it[id] = summaryEventID
                it[type] = AccountEvents.ACCOUNT_SUMMARY.name
                it[entityID] = accountID
                it[data] = gson.toJson(AccountSummaryEvent(event.owner, "0.00"))
            }

            Accounts.insert {
                it[id] = accountID
                it[lastSummaryEvent] = summaryEventID
            }

            commit()
            accountID
        }

        return accountID
    }
}