package io.github.riverbytheocean.plugins.riverkeys.network.packets;

import io.github.riverbytheocean.plugins.riverkeys.network.KeyChannels;
import io.github.riverbytheocean.plugins.riverkeys.network.KeyNetwork;
import io.github.riverbytheocean.plugins.riverkeys.network.Packet;
import lombok.Getter;
import net.kyori.adventure.key.Key;
import net.minecraft.network.FriendlyByteBuf;
import org.bukkit.entity.Player;

@Getter
public class KeyPressPacket implements Packet<KeyPressPacket> {

    private String namespace;
    private String key;
    private boolean release;

    public KeyPressPacket() {}

    public KeyPressPacket(String namespace, String key, boolean release) {

        this.namespace = namespace;
        this.key = key;
        this.release = release;

    }

    @Override
    public Key getID() {
        return Key.key(KeyChannels.KEY_PRESS);
    }

    @Override
    public KeyPressPacket fromBytes(FriendlyByteBuf buf) {
        namespace = buf.readUtf(Short.MAX_VALUE);
        key = buf.readUtf(Short.MAX_VALUE);
        release = buf.readBoolean();
        return this;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {

        buf.writeUtf(getNamespace(), Short.MAX_VALUE);
        buf.writeUtf(getKey(), Short.MAX_VALUE);
        buf.writeBoolean(release);

    }

    @Override
    public void onPacket(Player player) {
        KeyNetwork.receiveKeyPress(player, getNamespace(), getKey(), getRelease());
    }

    public boolean getRelease() {

        return release;

    }

}
