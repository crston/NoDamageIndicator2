package com.gmail.bobason01;

import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedParticle;
import org.bukkit.Particle;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.EventHandler;

public class ParticleHandler1_13 implements Listener {
    private boolean damageOccurred = false;

    public void setDamageOccurred(boolean damageOccurred) {
        this.damageOccurred = damageOccurred;
    }

    public void handlePacket(PacketEvent event) {
        if (damageOccurred && event.getPacketType() == Server.WORLD_PARTICLES) {
            WrappedParticle<?> particle = event.getPacket().getNewParticles().read(0);
            if (particle.getParticle() == Particle.DAMAGE_INDICATOR) {
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