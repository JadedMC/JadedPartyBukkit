package net.jadedmc.jadedpartybukkit.listeners;

import net.jadedmc.jadedchat.features.channels.events.ChannelBungeeSendEvent;
import net.jadedmc.jadedpartybukkit.JadedPartyBukkit;
import net.jadedmc.jadedpartybukkit.party.Party;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChannelBungeeSendListener implements Listener {
    private final JadedPartyBukkit plugin;

    public ChannelBungeeSendListener(JadedPartyBukkit plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBungeeMessageSend(ChannelBungeeSendEvent event) {

        // Make sure the channel is the party chat channel.
        if(!event.getChannel().name().equalsIgnoreCase("PARTY")) {
            return;
        }

        Party party = plugin.partyManager().getParty(event.getPlayer());
        if(party == null) {
            event.setCancelled(true);
            System.out.println("Party Null");
            return;
        }

        event.setData(party.getUUID().toString());
    }
}
