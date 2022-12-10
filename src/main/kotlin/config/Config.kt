package config

import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.sources.EnvironmentVariablesPropertySource

data class Database(val path: String)
data class Server(val port: Int)
data class Config(val database: Database, val server: Server)


fun loadConfig(): Config {
    return ConfigLoaderBuilder.default()
        .addPropertySource(EnvironmentVariablesPropertySource(true, true))
        .build()
        .loadConfigOrThrow<Config>()
}