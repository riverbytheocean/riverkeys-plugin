package io.github.riverbytheocean.plugins.riverkeys.api;

import lombok.Getter;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class KeyPressEvent extends KeyEvent {

    @Getter
    private static final HandlerList handlers = new HandlerList();

    public KeyPressEvent(Player who, NamespacedKey id, boolean registered) {
        super(who, id, registered);
    }

    @Override
    public @NotNull HandlerList getHandlers() { return handlers; }
}
