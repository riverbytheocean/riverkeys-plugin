package io.github.riverbytheocean.plugins.riverkeys.config;

import io.github.riverbytheocean.plugins.riverkeys.RiverKeys;
import lombok.Getter;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

@Getter
public class KeysConfig {

    private final Map<NamespacedKey, KeyInfo> keyInfoList = new HashMap<>();

    private boolean eventOnCommand;

    public void reload(FileConfiguration config) {

        eventOnCommand = config.getBoolean("run_event_on_command");

        keyInfoList.clear();

        ConfigurationSection keys = config.getConfigurationSection("Keys");

        if (keys == null) return;

        for (String key : keys.getKeys(false)) {

            if (keys.contains(key, true)) {

                ConfigurationSection section = keys.getConfigurationSection(key);

                if (section == null) {

                    error(key);
                    return;

                }

                KeyInfo info = KeyInfo.from(section);
                if (info == null) {

                    error(key);
                    return;

                }

                keyInfoList.put(info.getId(), info);
            }

        }

    }

    private void error(String name) {
        RiverKeys.getPlugin().getLogger().severe("yep!");
    }

}
