package net.jadedmc.jadedpartybukkit.events;

import net.jadedmc.jadedpartybukkit.party.Party;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.checkerframework.checker.nullness.qual.NonNull;

public class PartySyncEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Party party;

    public PartySyncEvent(final Party party) {
        this.party = party;
    }

    public Party getParty() {
        return party;
    }

    public @NonNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}