package com.jw

import com.jw.routes.botRoutes
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.runBlocking

fun main() {
    val bot = DiscordBot("YOUR TOKEN")
        bot.start()

        embeddedServer(Netty, port = 8080) {
            module(bot)
        }.start(wait = true)
    }


fun Application.module(bot: DiscordBot) {
    install(ContentNegotiation) {
        json()
    }

    routing {
        botRoutes(bot)
    }
}
