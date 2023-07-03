package io.github.mooy1.infinitylib.commands;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.command.CommandSender;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.common.ChatColors;

@ParametersAreNonnullByDefault
final class InfoCommand extends SubCommand {

    private final String[] message;

    InfoCommand(SlimefunAddon addon) {
        super("info", "提供本附加的 Slimefun 版本與 Discord 連結");
        Slimefun slimefun = Slimefun.instance();
        message = new String[] {
                "",
                ChatColors.color("&b" + addon.getName() + " 資訊"),
                ChatColors.color("&bSlimefun 版本：&7" + (slimefun == null ? "null" : slimefun.getPluginVersion())),
                ChatColors.color("&bSlimefun Discord：&7Discord.gg/slimefun"),
                ChatColors.color("&b&3Slimefun 繁體中文版 Discord：&7Discord.gg/GF4CwjFXT9"),
                ChatColors.color("&b附加版本：&7" + addon.getPluginVersion()),
                ChatColors.color("&b官方附加社群：&7Discord.gg/SqD3gg5SAU"),
                ChatColors.color("&bGithub：&7" + addon.getBugTrackerURL()),
                ChatColors.color("&4&l此為非官方版&f｜&4&lThis is not Official Version"),
                ""
        };
    }

    @Override
    protected void execute(CommandSender sender, String[] args) {
        sender.sendMessage(message);
    }

    @Override
    protected void complete(CommandSender sender, String[] args, List<String> tabs) {

    }

}
