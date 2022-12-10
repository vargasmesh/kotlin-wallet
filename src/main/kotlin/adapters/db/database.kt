package adapters.db

import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database

fun connectDatabase(dbConfig: config.Database): Database {
    val path = "jdbc:sqlite:${dbConfig.path}"
    Flyway.configure().dataSource(path, "", "").load().migrate()
    return Database.connect(path)
}