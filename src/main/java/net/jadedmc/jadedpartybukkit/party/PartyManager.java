package net.jadedmc.jadedpartybukkit.party;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.jadedmc.jadedchat.JadedChat;
import net.jadedmc.jadedpartybukkit.JadedPartyBukkit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PartyManager {
    private final JadedPartyBukkit plugin;
    private final Map<UUID, Party> parties = new HashMap<>();

    public PartyManager(JadedPartyBukkit plugin) {
        this.plugin = plugin;
    }

    /**
     * Syncs a party from bungeecord.
     * @param message Bungeecord message.
     */
    public void syncParty(String message) {
        parties.put(UUID.fromString(message.split("~")[0]), new Party(message));
    }

    /**
     * Marks a party as disbanded.
     * @param uuid UUID of the party that is disbanded.
     */
    public void disbandParty(UUID uuid) {
        Party party = parties.get(uuid);

        if(party != null) {
            for(UUID playerUUID : party.getPlayers()) {
                Player player = Bukkit.getPlayer(playerUUID);

                if(player == null) {
                    continue;
                }

                JadedChat.setChannel(player, JadedChat.getDefaultChannel());
            }
        }

        parties.remove(uuid);
    }

    /**
     * Get the party a player is in.
     * Returns null if not in a party.
     * @param player Player to get party of.
     * @return Party the player is in.
     */
    public Party getParty(Player player) {
        for(Party party : parties.values()) {
            if(party.getPlayers().contains(player.getUniqueId())) {
                return party;
            }
        }

        return null;
    }

    public Party getParty(UUID uuid) {
        if(parties.containsKey(uuid)) {
            return parties.get(uuid);
        }

        return null;
    }

    public void summonParty(Party party) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("summon");
        out.writeUTF(party.getUUID().toString());
        Iterables.getFirst(Bukkit.getOnlinePlayers(), null).sendPluginMessage(plugin, "jadedmc:party", out.toByteArray());
    }
}