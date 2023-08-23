package net.jadedmc.jadedpartybukkit.party;

import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Party {
    private final UUID uuid;
    private final UUID leader;
    private final List<UUID> members = new ArrayList<>();

    public Party(String message) {
        String[] args = message.split("~");

        uuid = UUID.fromString(args[0]);
        leader = UUID.fromString(args[1]);

        // Makes sure the party has members before trying to add the members.
        if(args.length != 3) {
            return;
        }

        // Adds all the members to the party.
        for(String member : args[2].split(":")) {
            members.add(UUID.fromString(member));
        }
    }

    public UUID getLeader() {
        return leader;
    }

    public List<UUID> getMembers() {
        return members;
    }

    /**
     * Gets the number of party players who are on the spigot server.
     * @return NUmber of players on this spigot instance.
     */
    public int getOnlineCount() {
        int online = 0;

        for(UUID uuid : getPlayers()) {
            if(Bukkit.getPlayer(uuid) != null) {
                online++;
            }
        }

        return online;
    }

    public List<UUID> getPlayers() {
        List<UUID> players = new ArrayList<>(members);
        players.add(leader);

        return players;
    }

    // Gets the UUID of the party.
    public UUID getUUID() {
        return uuid;
    }
}