package io.github.riverbytheocean.plugins.riverkeys;

import io.github.riverbytheocean.plugins.riverkeys.cmd.KeysCommand;
import io.github.riverbytheocean.plugins.riverkeys.config.KeysConfig;
import io.github.riverbytheocean.plugins.riverkeys.network.PacketManager;
import lombok.Getter;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class RiverKeys extends JavaPlugin {
    @Getter
    private static RiverKeys plugin;

    @Getter
    private final KeysConfig conf = new KeysConfig();

    public static PacketManager packetManager;

    @Override
    public void onEnable() {

        plugin = this;

        packetManager = new PacketManager();
        packetManager.enable();

        KeysCommand cmd = new KeysCommand();
        PluginCommand command = getCommand("riverkeys");
        if (command != null) {

            command.setExecutor(cmd);
            command.setTabCompleter(cmd);

        }

        saveDefaultConfig();
        reload();

    }

    @Override
    public void onDisable() {

        if (packetManager != null) {
            packetManager.disable();
        }

        plugin = null;

    }

    public void reload() {

        reloadConfig();
        conf.reload(getConfig());

    }
}
