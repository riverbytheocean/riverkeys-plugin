package io.github.riverbytheocean.plugins.riverkeys.config;

import io.github.riverbytheocean.plugins.riverkeys.ModifierKey;
import io.github.riverbytheocean.plugins.riverkeys.RiverKeys;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

@Getter
public class KeyInfo {

    private KeyInfo(NamespacedKey id, String name, String category, int def, Set<ModifierKey> modifiers, String command, String mythicPress, String mythicRelease) {

        this.id = id;
        this.name = name;
        this.category = category;
        this.def = def;
        this.modifiers = modifiers;
        this.command = command;
        this.mythicPress = mythicPress;
        this.mythicRelease = mythicRelease;

    }

    private final NamespacedKey id;
    private final String name, category, command, mythicPress, mythicRelease;
    private final int def;
    private final Set<ModifierKey> modifiers;

    public static @Nullable KeyInfo from(ConfigurationSection config) {

        if (config.contains("Id")) {

            NamespacedKey key = NamespacedKey.fromString(config.getString("Id", ""), RiverKeys.getPlugin());
            if (key == null) return null;

            String name;
            String category;
            int defaultKey;

            if (config.contains("Name") && config.contains("DefaultKey") && config.contains("Category")) {

                name = config.getString("Name");
                category = config.getString("Category");
                defaultKey = config.getInt("DefaultKey");

            } else if (key.getNamespace().equals("minecraft")) {

                name = "name";
                category = "category";
                defaultKey = 0;

            } else {

                return null;

            }

            Set<ModifierKey> modifiers = new HashSet<>();

            for (String modifier : config.getStringList("Modifiers")) {

                ModifierKey modKey = ModifierKey.fromString(modifier);
                if (modKey != ModifierKey.NONE) modifiers.add(modKey);

            }

            return new KeyInfo(key, name, category, defaultKey, modifiers, config.getString("RunCommand", ""),
                    config.getString("SkillPress", ""), config.getString("SkillRelease", ""));

        }

        return null;

    }

    public boolean runCommand(Player player) {

        if (command == null || command.isEmpty()) return false;

        final boolean isAdmin = command.startsWith("!");
        String cmd = (isAdmin ? command.substring(1) : command).replace("%player%", player.getName());

        if (isAdmin) Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
        else Bukkit.dispatchCommand(player, cmd);
        return true;

    }

    public boolean hasCommand() { return checkStr(command); }

    public boolean hasMM(boolean press) { return press ? checkStr(mythicPress) : checkStr(mythicRelease); }

    private boolean checkStr(String str) { return str != null && !str.isEmpty(); }

}
