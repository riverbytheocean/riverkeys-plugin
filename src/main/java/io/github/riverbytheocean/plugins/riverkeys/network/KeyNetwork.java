package io.github.riverbytheocean.plugins.riverkeys.network;

import io.github.riverbytheocean.plugins.riverkeys.RiverKeys;
import io.github.riverbytheocean.plugins.riverkeys.api.KeyPressEvent;
import io.github.riverbytheocean.plugins.riverkeys.api.KeyReleaseEvent;
import io.github.riverbytheocean.plugins.riverkeys.config.KeyInfo;
import io.github.riverbytheocean.plugins.riverkeys.network.packets.AddKeyPacket;
import io.github.riverbytheocean.plugins.riverkeys.network.packets.LoadKeysPacket;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

public class KeyNetwork {

    public static void receiveKeyPress(Player player, String namespace, String key, boolean release) {
        // Read the key press ID then call the AriKeyPress event.
        boolean firstPress = !release;
        NamespacedKey id = NamespacedKey.fromString(namespace + ":" + key);

        if (RiverKeys.getPlugin().getConf().getKeyInfoList().containsKey(id)) {
            KeyInfo info = RiverKeys.getPlugin().getConf().getKeyInfoList().get(id);
            boolean eventCmd = RiverKeys.getPlugin().getConf().isEventOnCommand();

            if (firstPress) {
                if (!info.runCommand(player) || eventCmd) Bukkit.getPluginManager().callEvent(new KeyPressEvent(player, id, true));
                return;
            }

            if (!info.hasCommand() || eventCmd) Bukkit.getPluginManager().callEvent(new KeyReleaseEvent(player, id, true));
        } else {
            Bukkit.getPluginManager()
                    .callEvent(firstPress ? new KeyPressEvent(player, id, false) : new KeyReleaseEvent(player, id, false));
        }
    }

    public static void receiveGreeting(Player player) {
		/* Send this server's specified keybindings to the
		 client. This is delayed to make sure the client is properly
		 connected before attempting to send any data over. */
        Bukkit.getScheduler().runTaskLater(RiverKeys.getPlugin(), () -> {
            for (KeyInfo info : RiverKeys.getPlugin().getConf().getKeyInfoList().values())
                sendKeyInformation(player, info.getId(), info.getDef(), info.getName(), info.getCategory());

			/* Send the "load" packet after sending every keybinding packet, to tell
			 the client to load all the user-specific keybinds saved on their machine. */

            PacketManager.sendToClient(player, new LoadKeysPacket());

        }, 20);
    }

    // Simply send over the information in an add key packet.
    public static void sendKeyInformation(Player player, NamespacedKey id, int def, String name, String category) {

        PacketManager.sendToClient(player, new AddKeyPacket(id.getNamespace(), id.getKey(), def, name, category));

    }

	/* <!!! Important Note !!!>
	 The next following methods are rewritten from Minecrafts "PacketByteBuf" class.
	 These are neccessary for reading the String data that's being sent from the client.
	 version 2.1: New methods added for reading int arrays */

//    private static int readVarInt(ByteBuf buf) {
//        byte b0;
//        int i = 0;
//        int j = 0;
//        //noinspection InfiniteLoopStatement
//        do {
//            b0 = buf.readByte();
//            i |= (b0 & 0x7F) << j++ * 7;
//            if (j <= 5) continue;
//            throw new RuntimeException("VarInt too big");
//        } while ((b0 & 0x80) == 128);
//        return i;
//    }


//    private static void writeVarInt(ByteBuf buf, int value) {
//        while ((value & -128) != 0) {
//            buf.writeByte(value & 127 | 128);
//            value >>>= 7;
//        }
//
//        buf.writeByte(value);
//    }

	/* I may never need to read an int array on this side, but I'm keeping it just in case...

	public static int[] readIntArray(ByteBuf buf) {
		return readIntArray(buf, buf.readableBytes());
	}

	public static int[] readIntArray(ByteBuf buf, int maxSize) {
		int i = readVarInt(buf);
		if (i > maxSize) {
			throw new DecoderException("VarIntArray with size " + i + " is bigger than allowed " + maxSize);
		} else {
			int[] is = new int[i];

			for (int j = 0; j < is.length; ++j)
				is[j] = readVarInt(buf);

			return is;
		}
	}*/

//    public static void writeIntArray(ByteBuf buf, int[] array) {
//        writeVarInt(buf, array.length);
//
//        for (int i : array)
//            writeVarInt(buf, i);
//    }

}
