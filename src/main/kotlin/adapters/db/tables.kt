package adapters.db

import org.jetbrains.exposed.sql.Table

object Events : Table() {
    val id = varchar("id", 27)
    val type = varchar("type", 255)
    val entityID = varchar("entity_id", 36)
    val data = text("data").nullable()

    override val primaryKey = PrimaryKey(id)
}

object Accounts : Table() {
    val id = varchar("id", 36)
    val lastSummaryEvent = reference("last_summary_event", Events.entityID)

    override val primaryKey = PrimaryKey(id)
}