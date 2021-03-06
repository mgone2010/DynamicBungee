package com.mcprohosting.plugins.dynamicbungee.server;

import com.mcprohosting.plugins.dynamicbungee.DynamicBungee;
import com.mcprohosting.plugins.dynamicbungee.data.NetTaskSubscribe;
import com.mcprohosting.plugins.dynamicbungee.entities.User;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;

public class BaseReceiver {
    @NetTaskSubscribe(args = {"player", "server"}, name = "send")
    public void onSend(HashMap<String, Object> args) {
        Object p = args.get("player");
        Object s = args.get("server");

        if (!(p instanceof String) || !(s instanceof String)) {
            return;
        }

        String player = (String) p;
        String server = (String) s;
        ProxiedPlayer proxiedPlayer = ProxyServer.getInstance().getPlayer(player);

        if (player == null) {
            return;
        }

        ServerInfo serverInfo = ProxyServer.getInstance().getServerInfo(server);

        if (serverInfo == null) {
            proxiedPlayer.sendMessage(ChatColor.RED + "This server is offline, please try again later!");
            return;
        }

        if (proxiedPlayer == null) {
            return;
        }

        if (User.getUser(player).getDestinationServer().equalsIgnoreCase(server)) {
            return;
        }

        User.getUser(player).setDestinationServer(server);
        proxiedPlayer.connect(serverInfo);
    }
}
