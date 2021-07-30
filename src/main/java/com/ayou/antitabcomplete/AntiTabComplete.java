package com.ayou.antitabcomplete;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.reflect.FieldAccessException;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class AntiTabComplete extends JavaPlugin {
    @Override
    public void onEnable() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, new PacketType[] { PacketType.Play.Client.TAB_COMPLETE }) {
            public void onPacketReceiving(final PacketEvent event) {
                if (event.getPacketType() == PacketType.Play.Client.TAB_COMPLETE) {
                    if (event.getPlayer().isOp())return;
                    try {
                        final PacketContainer packet = event.getPacket();
                        final String message = (packet.getSpecificModifier(String.class).read(0)).toLowerCase();
                        if (message.startsWith("/") && !message.contains("  ")) {
                            event.setCancelled(true);
                        }
                    }
                    catch (FieldAccessException e) {
                        AntiTabComplete.this.getLogger().log(Level.SEVERE, "无法访问字段.", e);
                    }
                }
            }
        });
        getLogger().info("成功加载禁止玩家Tab补全指令插件 Powered by Ayou");
    }

    @Override
    public void onDisable() {

    }
}
