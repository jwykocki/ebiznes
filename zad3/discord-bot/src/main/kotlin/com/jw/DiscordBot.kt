package com.jw

import dev.kord.common.entity.Snowflake
import dev.kord.core.Kord
import dev.kord.core.entity.channel.MessageChannel
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.on
import kotlinx.coroutines.runBlocking

class DiscordBot(private val token: String) {

    private lateinit var kord: Kord
    private val CHANNEL_ID = "CHANNEL ID"

    fun start() = runBlocking {
        kord = Kord(token)
        kord.login()

        kord.on<MessageCreateEvent> {
            println("Received the message: ${message.content}")

            if (message.content == "!ping") {
                message.channel.createMessage("Pong!")
            }
        }
    }

    suspend fun sendMessageToChannel(content: String): Boolean {
        val channel = kord.getChannelOf<MessageChannel>(Snowflake(CHANNEL_ID))
        channel?.createMessage(content)
        return channel != null
    }
}