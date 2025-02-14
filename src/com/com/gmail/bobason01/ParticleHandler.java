package com.gmail.bobason01;

import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedParticle;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.EventHandler;

import java.util.Objects;

public class ParticleHandler implements Listener {
    private boolean damageOccurred = false;

    public void setDamageOccurred(boolean damageOccurred) {
        this.damageOccurred = damageOccurred;
    }

    public void handlePacket(PacketEvent event) {
        if (damageOccurred && event.getPacketType() == Server.WORLD_PARTICLES) {
            WrappedParticle<?> particle = event.getPacket().getNewParticles().read(0);
            if (Objects.requireNonNull(particle.getParticle()).toString().equals(EnumWrappers.Particle.CRIT.toString())) {
                event.setCancelled(true);
                setDamageOccurred(false); // Reset the flag after handling the packet
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        setDamageOccurred(true);
    }
}