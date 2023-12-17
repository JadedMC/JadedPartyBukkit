/*
 * This file is part of JadedPartyBukkit, licensed under the MIT License.
 *
 *  Copyright (c) JadedMC
 *  Copyright (c) contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */
package net.jadedmc.jadedpartybukkit.listeners;

import net.jadedmc.jadedchat.JadedChat;
import net.jadedmc.jadedchat.features.channels.events.ChannelMessageSendEvent;
import net.jadedmc.jadedchat.utils.ChatUtils;
import net.jadedmc.jadedpartybukkit.JadedPartyBukkit;
import net.jadedmc.jadedpartybukkit.party.Party;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Listens to the ChannelMessageSendEvent, which is called every time a player sends a message in a chat channel.
 */
public class ChannelMessageSendListener implements Listener {
    private final JadedPartyBukkit plugin;

    /**
     * Creates the Listener.
     * @param plugin Instance of the plugin.
     */
    public ChannelMessageSendListener(final JadedPartyBukkit plugin) {
        this.plugin = plugin;
    }

    /**
     * Runs when the ChannelMessageSendEvent is called.
     * @param event ChannelMessageSendEvent.
     */
    @EventHandler
    public void onMessageSend(ChannelMessageSendEvent event) {
        if(!event.getChannel().equals(JadedChat.getChannel("PARTY"))) {
            return;
        }

        Player player = event.getPlayer();
        Party party = plugin.partyManager().getParty(player);

        if(party == null) {
            ChatUtils.chat(player, "&cYou are not in a party! You can go back to global with /chat global");
            event.setCancelled(true);
            return;
        }

        List<Player> viewers = new ArrayList<>();

        for(UUID uuid : party.getPlayers()) {
            Player viewer = Bukkit.getPlayer(uuid);

            if(viewer != null) {
                viewers.add(viewer);
            }
        }

        event.setViewers(viewers);
    }
}