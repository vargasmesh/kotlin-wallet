import adapters.db.connectDatabase
import com.apurebase.kgraphql.GraphQL
import config.loadConfig
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    val config = loadConfig()
    val db = connectDatabase(config.database)

    embeddedServer(Netty, config.server.port, module = Application::module).start(wait = true)
}

fun Application.module() {
    install(GraphQL) {
        playground = true
        schema {
            query("hello") {
                resolver { -> "World!" }
            }
        }
    }
}