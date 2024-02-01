package com.gmail.bobason01;
import java.util.logging.Logger;

import org.bukkit.Particle;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;

public final class NoDamageIndicator extends JavaPlugin {
    public final Logger MainLogger = this.getLogger();

    public void onEnable() {
        this.MainLogger.info("Starting on " + this.getServer().getVersion());
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        manager.addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, Server.WORLD_PARTICLES) {
            public void onPacketSending(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                if (event.getPacketType() == Server.WORLD_PARTICLES) {
                    if (packet.getNewParticles().read(0).getParticle() == Particle.DAMAGE_INDICATOR) {
                        event.setCancelled(true);
                    }

                }
            }
        });
    }
    public void onDisable() {
        this.MainLogger.info("Disabled!");
    }
}