package me.aroze.spyglass.listener

import com.destroystokyo.paper.event.server.AsyncTabCompleteEvent
import com.github.retrooper.packetevents.event.PacketListener
import com.github.retrooper.packetevents.event.PacketReceiveEvent
import com.github.retrooper.packetevents.protocol.packettype.PacketType
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientTabComplete
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

object PlayerTabCompleteListener : PacketListener {

    @Override
    override fun onPacketReceive(event: PacketReceiveEvent?) {
        if (event?.packetType != PacketType.Play.Client.TAB_COMPLETE) return

        val player = event.getPlayer<Player>()
        val tabComplete = WrapperPlayClientTabComplete(event)

        Bukkit.broadcastMessage("${player.name}: ${tabComplete.text}")
    }

}
