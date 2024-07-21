package io.github.riverbytheocean.plugins.riverkeys.network;

import net.kyori.adventure.key.Key;
import net.minecraft.network.FriendlyByteBuf;
import org.bukkit.entity.Player;

public interface Packet<T extends Packet> {

    Key getID();

    T fromBytes(FriendlyByteBuf buf);

    void toBytes(FriendlyByteBuf buf);

    default void onPacket(Player player) {



    }

}
