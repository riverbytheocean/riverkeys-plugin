package io.github.riverbytheocean.plugins.riverkeys.network.packets;

import io.github.riverbytheocean.plugins.riverkeys.network.KeyChannels;
import io.github.riverbytheocean.plugins.riverkeys.network.Packet;
import net.kyori.adventure.key.Key;
import net.minecraft.network.FriendlyByteBuf;

public class LoadKeysPacket implements Packet<LoadKeysPacket> {

    public LoadKeysPacket() {}

    @Override
    public Key getID() {
        return Key.key(KeyChannels.LOAD_KEYS);
    }

    @Override
    public LoadKeysPacket fromBytes(FriendlyByteBuf buf) {
        return this;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {}

}
