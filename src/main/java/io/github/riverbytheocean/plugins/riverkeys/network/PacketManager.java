package io.github.riverbytheocean.plugins.riverkeys.network;

import io.github.riverbytheocean.plugins.riverkeys.RiverKeys;
import io.github.riverbytheocean.plugins.riverkeys.network.packets.AddKeyPacket;
import io.github.riverbytheocean.plugins.riverkeys.network.packets.HandshakePacket;
import io.github.riverbytheocean.plugins.riverkeys.network.packets.KeyPressPacket;
import io.github.riverbytheocean.plugins.riverkeys.network.packets.LoadKeysPacket;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PacketManager {

    // henkelmax my savior

    private final Set<String> packets = new HashSet<>();

    public void enable() {

        packets.clear();
        try {

            registerIncomingPacket(HandshakePacket.class);
            registerIncomingPacket(KeyPressPacket.class);

            registerOutgoingPacket(AddKeyPacket.class);
            registerOutgoingPacket(LoadKeysPacket.class);

        } catch (Exception e) {
            throw new IllegalStateException("sooo, where did all the key packets go?");
        }

    }

    public void disable() {

        Bukkit.getMessenger().unregisterIncomingPluginChannel(RiverKeys.getPlugin());
        Bukkit.getMessenger().unregisterOutgoingPluginChannel(RiverKeys.getPlugin());

        packets.clear();

    }

    public <T extends Packet<?>> void registerIncomingPacket(Class<T> packetClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Constructor<T> c = packetClass.getDeclaredConstructor();
        String id = c.newInstance().getID().toString();
        packets.add(id);
        Bukkit.getMessenger().registerIncomingPluginChannel(RiverKeys.getPlugin(), id, (s, player, bytes) -> {

            T packet;
            try {
                packet = c.newInstance();
            } catch (Exception e) {
                RiverKeys.getPlugin().getLogger().severe("oopsies the packet instance wasn't able to be made!");
                return;
            }
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.wrappedBuffer(bytes));
            packet.fromBytes(buf);
            packet.onPacket(player);

        });

    }

    public <T extends Packet<?>> void registerOutgoingPacket(Class<T> packetClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        String id = packetClass.getDeclaredConstructor().newInstance().getID().toString();
        packets.add(id);
        Bukkit.getMessenger().registerOutgoingPluginChannel(RiverKeys.getPlugin(), id);

    }

    public static void sendToClient(Player player, Packet<?> packet) {

        try {

            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            packet.toBytes(buf);
            byte[] bytes = new byte[buf.readableBytes()];
            buf.readBytes(bytes);
            player.sendPluginMessage(RiverKeys.getPlugin(), packet.getID().toString(), bytes);

        } catch (Exception e) {
            RiverKeys.getPlugin().getLogger().severe("oopsies the packet wasn't able to be sent!" + Arrays.toString(e.getStackTrace()));
        }

    }

}
