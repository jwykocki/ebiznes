package com.jw.routes

import com.jw.DiscordBot
import com.jw.models.SendMessageRequest
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.botRoutes(bot: DiscordBot) {

    post("/send") {
        val req = call.receive<SendMessageRequest>()
        val success = bot.sendMessageToChannel(req.message)
        if (success) {
            call.respondText("Message was sent successfully to the channel.", status = io.ktor.http.HttpStatusCode.OK)
        } else {
            call.respondText("Sending message failed", status = io.ktor.http.HttpStatusCode.InternalServerError)
        }
    }
}
