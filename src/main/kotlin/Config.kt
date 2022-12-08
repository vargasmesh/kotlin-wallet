data class Database(val path: String)
data class Server(val port: Int)
data class Config(val database: Database, val server: Server)