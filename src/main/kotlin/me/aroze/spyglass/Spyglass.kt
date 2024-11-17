package me.aroze.spyglass

import me.aroze.spyglass.command.SpyCommand
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import org.incendo.cloud.SenderMapper
import org.incendo.cloud.annotations.AnnotationParser
import org.incendo.cloud.execution.ExecutionCoordinator
import org.incendo.cloud.kotlin.coroutines.annotations.installCoroutineSupport
import org.incendo.cloud.paper.LegacyPaperCommandManager

class Spyglass : JavaPlugin() {

    override fun onEnable() {
        registerCommands()
    }

    override fun onDisable() {
        // miaow ;3
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
