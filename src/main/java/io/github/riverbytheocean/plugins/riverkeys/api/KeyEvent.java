package io.github.riverbytheocean.plugins.riverkeys.api;

import lombok.Getter;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

@Getter
public class KeyEvent extends PlayerEvent {

    private static final HandlerList handlers = new HandlerList();

    private final NamespacedKey id;
    private final boolean registered;

    public KeyEvent(Player who, NamespacedKey id, boolean registered) {
        super(who);
        this.id = id;
        this.registered = registered;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

}
