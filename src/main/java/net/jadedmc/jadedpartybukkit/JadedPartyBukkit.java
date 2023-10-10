package net.jadedmc.jadedpartybukkit;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import net.jadedmc.jadedchat.JadedChat;
import net.jadedmc.jadedchat.features.channels.channel.ChatChannel;
import net.jadedmc.jadedchat.features.channels.channel.ChatChannelBuilder;
import net.jadedmc.jadedchat.features.channels.fomat.ChatFormatBuilder;
import net.jadedmc.jadedpartybukkit.listeners.ChannelBungeeReceiveListener;
import net.jadedmc.jadedpartybukkit.listeners.ChannelBungeeSendListener;
import net.jadedmc.jadedpartybukkit.party.PartyManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public final class JadedPartyBukkit extends JavaPlugin implements PluginMessageListener {
    private PartyManager partyManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        JadedParty.setPlugin(this);
        partyManager = new PartyManager(this);

        // Creates the party chat channel if it does not exist.
        if(!JadedChat.channelExists("PARTY")) {
            ChatChannel partyChat = new ChatChannelBuilder("PARTY")
                    .setDisplayName("<green>PARTY</green>")
                    .addAliases("P", "PC")
                    .useBungeecord(true)
                    .addChatFormat(new ChatFormatBuilder("default")
                            .addSection("channel", "<gray>[<green>Party<gray> <white>")
                            .addSection("player", "%player_name%")
                            .addSection("separator", "<dark_gray> \\xbb ")
                            .addSection("message", "<green><message>")
                            .build())
                    .build();
            partyChat.saveToFile("party.yml");
            JadedChat.loadChannel(partyChat);
        }

        getServer().getPluginManager().registerEvents(new ChannelBungeeReceiveListener(this), this);
        getServer().getPluginManager().registerEvents(new ChannelBungeeSendListener(this), this);

        getServer().getMessenger().registerIncomingPluginChannel(this, "jadedmc:party", this);
        getServer().getMessenger().registerOutgoingPluginChannel(this, "jadedmc:party");
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public void onPluginMessageReceived(String channel, @NotNull Player player, byte[] bytes) {
        if (!channel.equalsIgnoreCase( "jadedmc:party")) {
            return;
        }

        ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
        String subChannel = in.readUTF();

        switch (subChannel.toLowerCase()) {
            case "sync" -> {
                String message = in.readUTF();
                System.out.println("[Party Sync] " + message);
                partyManager.syncParty(message);
            }

            case "disband" -> {
                String message = in.readUTF();
                System.out.println("[Party Disband] " + message);
                partyManager.disbandParty(UUID.fromString(message));
            }
            default -> System.out.println(channel);
        }
    }

    public PartyManager partyManager() {
        return partyManager;
    }
}