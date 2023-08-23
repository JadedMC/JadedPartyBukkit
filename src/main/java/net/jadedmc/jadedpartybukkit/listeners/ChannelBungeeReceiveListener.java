package net.jadedmc.jadedpartybukkit.listeners;

import net.jadedmc.jadedchat.features.channels.events.ChannelBungeeReceiveEvent;
import net.jadedmc.jadedpartybukkit.JadedPartyBukkit;
import net.jadedmc.jadedpartybukkit.party.Party;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class ChannelBungeeReceiveListener implements Listener {
    private final JadedPartyBukkit plugin;

    public ChannelBungeeReceiveListener(JadedPartyBukkit plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBungeeMessageReceive(ChannelBungeeReceiveEvent event) {

        // Make sure the channel is the party chat channel.
        if(!event.getChannel().name().equalsIgnoreCase("PARTY")) {
            return;
        }

        Party party = plugin.partyManager().getParty(UUID.fromString(event.getData()));

        if(party == null) {
            event.setCancelled(true);
            return;
        }

        Collection<Player> viewers = new ArrayList<>();

        for(UUID uuid : party.getPlayers()) {
            Player player = Bukkit.getPlayer(uuid);

            if(player != null) {
                viewers.add(player);
            }
        }

        event.setViewers(viewers);
    }
}
