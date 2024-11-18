package me.aroze.spyglass.listener

import com.github.retrooper.packetevents.event.PacketListener
import com.github.retrooper.packetevents.event.PacketReceiveEvent
import com.github.retrooper.packetevents.protocol.packettype.PacketType
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientTabComplete
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

object PlayerTabCompleteListener : PacketListener {

    private val completionData = mutableMapOf<UUID, TabCompleteData>()

    @Override
    override fun onPacketReceive(event: PacketReceiveEvent?) {
        if (event?.packetType != PacketType.Play.Client.TAB_COMPLETE) return

        val player = event.getPlayer<Player>().player ?: return
        val tabComplete = WrapperPlayClientTabComplete(event)

        val completionData = completionData[player.uniqueId]
            ?: TabCompleteData()

        val lastCompletion = completionData.text
        val newCompletion = tabComplete.text

        completionData.text = newCompletion

        val isConsecutiveType = newCompletion.startsWith(lastCompletion) && newCompletion.length == lastCompletion.length + 1
        if (completionData.changes == 0 && isConsecutiveType) {
            completionData.startTime = System.currentTimeMillis()
        }

        if (!isConsecutiveType && completionData.changes > 0) {
            val totalTime = completionData.lastChangeTime - completionData.startTime
            if (completionData.changes > 1) {
                val cpm = completionData.changes / (totalTime / 60000.0)
                val wpm = Math.round(cpm / 5.0)
                Bukkit.broadcastMessage("${completionData.lastLoggedText} -> $lastCompletion with $wpm wpm (${totalTime}ms, ${completionData.changes} changes)")
            } else {
                Bukkit.broadcastMessage("${completionData.lastLoggedText} -> $lastCompletion")
            }
            completionData.reset()
        }

        if (isConsecutiveType) {
            completionData.changes++
        }

        completionData.lastChangeTime = System.currentTimeMillis()

        this.completionData[player.uniqueId] = completionData
    }

}

class TabCompleteData(
    var startTime: Long = System.currentTimeMillis(),
    var lastChangeTime: Long = System.currentTimeMillis(),
    var text: String = "",
    var lastLoggedText: String = "",
    var changes: Int = 0,
) {
    fun reset() {
        startTime = System.currentTimeMillis()
        lastChangeTime = System.currentTimeMillis()
        lastLoggedText = text
        changes = 0
    }
}
