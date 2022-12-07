import com.apurebase.kgraphql.GraphQL
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database

fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8080, module = Application::module).start(wait = true)
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