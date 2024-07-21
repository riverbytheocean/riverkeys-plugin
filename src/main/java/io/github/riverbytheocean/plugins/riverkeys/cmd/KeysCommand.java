package io.github.riverbytheocean.plugins.riverkeys.cmd;

import io.github.riverbytheocean.plugins.riverkeys.RiverKeys;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class KeysCommand implements CommandExecutor, TabCompleter {

    private static final List<String> COMMANDS = Arrays.asList("reload", "info");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {

        if (args.length < 1) return false;

        String cmd = args[0];
        if (cmd.equals("reload")) {

            RiverKeys.getPlugin().reload();
            sender.sendMessage(Component.text("so uhh riverkeys has been reloaded! lol!"));
            return true;

        }

        if (cmd.equals("info")) {

            sender.sendMessage(Component.text("yeah uhh cool keybind plugin! yeah!"));
            return true;

        }

        return false;

    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {

        final List<String> completions = new ArrayList<>();
        StringUtil.copyPartialMatches(args[0], COMMANDS, completions);
        Collections.sort(completions);
        return completions;

    }
}
