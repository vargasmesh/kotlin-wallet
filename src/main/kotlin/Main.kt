import com.apurebase.kgraphql.GraphQL
import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.sources.EnvironmentVariablesPropertySource
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.flywaydb.core.Flyway

fun main(args: Array<String>) {
    val config = ConfigLoaderBuilder.default()
        .addPropertySource(EnvironmentVariablesPropertySource(true, true))
        .build()
        .loadConfigOrThrow<Config>()

    Flyway.configure().dataSource("jdbc:sqlite:${config.database.path}", "", "").load().migrate()


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