package io.github.riverbytheocean.plugins.riverkeys.network.packets;

import io.github.riverbytheocean.plugins.riverkeys.network.KeyChannels;
import io.github.riverbytheocean.plugins.riverkeys.network.Packet;
import lombok.Getter;
import net.kyori.adventure.key.Key;
import net.minecraft.network.FriendlyByteBuf;

@Getter
public class AddKeyPacket implements Packet<AddKeyPacket> {

    private String namespace;
    private String key;
    private int defKey;
    private String name;
    private String category;

    public AddKeyPacket() {}

    public AddKeyPacket(String namespace, String key, int defKey, String name, String category) {

        this.namespace = namespace;
        this.key = key;
        this.defKey = defKey;
        this.name = name;
        this.category = category;

    }

    @Override
    public Key getID() {
        return Key.key(KeyChannels.ADD_KEY);
    }

    @Override
    public AddKeyPacket fromBytes(FriendlyByteBuf buf) {

        namespace = buf.readUtf(Short.MAX_VALUE);
        key = buf.readUtf(Short.MAX_VALUE);
        defKey = buf.readInt();
        name = buf.readUtf(Short.MAX_VALUE);
        category = buf.readUtf(Short.MAX_VALUE);
        return this;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(getNamespace(), Short.MAX_VALUE);
        buf.writeUtf(getKey(), Short.MAX_VALUE);
        buf.writeInt(defKey);
        buf.writeUtf(getName(), Short.MAX_VALUE);
        buf.writeUtf(getCategory(), Short.MAX_VALUE);
    }
}
