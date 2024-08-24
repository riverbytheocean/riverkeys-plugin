# riverkeys-plugin
 the arikeys plugin, ported over to 1.21, missing modifiers

Plugins that require this need to listen to the following plugin channels (Click on them to go to their respective classes):
- [Handshake](https://github.com/riverbytheocean/riverkeys-plugin/blob/main/src/main/java/io/github/riverbytheocean/plugins/riverkeys/network/packets/HandshakePacket.java) (riverkeys:greeting) - Allows you to get the players currently using the mod
- [Key Press](https://github.com/riverbytheocean/riverkeys-plugin/blob/main/src/main/java/io/github/riverbytheocean/plugins/riverkeys/network/packets/KeyPressPacket.java) (riverkeys:keybind) - Get the keybind namespace and id and whether or not the player has it held down
