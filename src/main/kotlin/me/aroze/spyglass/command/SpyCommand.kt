package me.aroze.spyglass.command

import org.bukkit.entity.Player
import org.incendo.cloud.annotations.Command
import org.incendo.cloud.annotations.Permission

object SpyCommand {

    @Command("spy")
    @Permission("spyglass.command.spy")
    fun spy(player: Player) {
        player.sendMessage("silly")
    }

}
