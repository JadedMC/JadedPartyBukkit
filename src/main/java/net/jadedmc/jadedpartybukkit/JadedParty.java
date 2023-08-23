package net.jadedmc.jadedpartybukkit;

import net.jadedmc.jadedpartybukkit.party.PartyManager;

public class JadedParty {
    private static JadedPartyBukkit plugin;

    static void setPlugin(JadedPartyBukkit pl) {
        plugin = pl;
    }

    public static PartyManager partyManager() {
        return plugin.partyManager();
    }

}
