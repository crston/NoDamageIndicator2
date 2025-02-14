package com.gmail.bobason01;

import java.util.logging.Logger;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

public class NodamageIndicator extends JavaPlugin {
    public final Logger MainLogger = this.getLogger();
    private Object particleHandler;

    @Override
    public void onEnable() {
        this.MainLogger.info("Starting on " + this.getServer().getVersion());

        // Determine Minecraft version and load appropriate handler
        String version = this.getServer().getVersion();
        if (version.contains("1.8")) {
            particleHandler = new ParticleHandler();
        } else if (version.contains("1.13")) {
            particleHandler = new ParticleHandler1_13();
        } else {
            this.MainLogger.severe("Unsupported Minecraft version: " + version);
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getServer().getPluginManager().registerEvents((Listener) particleHandler, this);

        ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        manager.addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, Server.WORLD_PARTICLES) {
            @Override
            public void onPacketSending(PacketEvent event) {
                if (particleHandler instanceof ParticleHandler) {
                    ((ParticleHandler) particleHandler).handlePacket(event);
                } else if (particleHandler instanceof com.gmail.bobason01.ParticleHandler1_13) {
                    ((ParticleHandler1_13) particleHandler).handlePacket(event);
                }
            }
        });
    }

    @Override
    public void onDisable() {
        this.MainLogger.info("Disabled!");
    }
}