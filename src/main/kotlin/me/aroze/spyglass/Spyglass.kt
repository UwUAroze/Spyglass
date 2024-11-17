package me.aroze.spyglass

import com.github.retrooper.packetevents.PacketEvents
import com.github.retrooper.packetevents.event.PacketListenerPriority
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder
import me.aroze.spyglass.command.SpyCommand
import me.aroze.spyglass.listener.PlayerTabCompleteListener
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandMap
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.incendo.cloud.SenderMapper
import org.incendo.cloud.annotations.AnnotationParser
import org.incendo.cloud.execution.ExecutionCoordinator
import org.incendo.cloud.kotlin.coroutines.annotations.installCoroutineSupport
import org.incendo.cloud.paper.LegacyPaperCommandManager
import java.lang.reflect.Field
import java.util.*
import java.util.stream.Collectors


class Spyglass : JavaPlugin() {

    override fun onLoad() {
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        PacketEvents.getAPI().load();
    }

    override fun onEnable() {
        PacketEvents.getAPI().init();
        registerCommands()
        registerListeners()
    }

    override fun onDisable() {
        // miaow ;3
    }

    private fun registerListeners() {
        PacketEvents.getAPI().eventManager.registerListener(PlayerTabCompleteListener, PacketListenerPriority.NORMAL)
//        server.pluginManager.registerEvents(PlayerTabCompleteListener, this)
    }

    private fun registerCommands() {
        val commandManager =
            LegacyPaperCommandManager(this, ExecutionCoordinator.simpleCoordinator(), SenderMapper.identity())

        val annotationParser =
            AnnotationParser(commandManager, CommandSender::class.java)

        annotationParser.installCoroutineSupport()

        listOf(SpyCommand).forEach {
            annotationParser.parse(it)
        }
    }

}
