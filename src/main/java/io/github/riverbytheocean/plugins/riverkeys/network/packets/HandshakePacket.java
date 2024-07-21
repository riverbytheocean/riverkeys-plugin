package io.github.riverbytheocean.plugins.riverkeys.network.packets;

import io.github.riverbytheocean.plugins.riverkeys.network.KeyChannels;
import io.github.riverbytheocean.plugins.riverkeys.network.KeyNetwork;
import io.github.riverbytheocean.plugins.riverkeys.network.Packet;
import net.kyori.adventure.key.Key;
import net.minecraft.network.FriendlyByteBuf;
import org.bukkit.entity.Player;

public class HandshakePacket implements Packet<HandshakePacket> {

    public HandshakePacket() {}

    @Override
    public Key getID() {
        return Key.key(KeyChannels.HANDSHAKE);
    }

    @Override
    public HandshakePacket fromBytes(FriendlyByteBuf buf) {
        return this;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {}

    @Override
    public void onPacket(Player player) {
        KeyNetwork.receiveGreeting(player);
    }
}
