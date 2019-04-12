package com.github.nijian.jkeel.report.lb;

import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.Server;

import java.net.InetAddress;
import java.net.Socket;

public final class ServerPing implements IPing {

    public boolean isAlive(Server server) {
        try {
            InetAddress address = InetAddress.getByName(server.getHost());
            new Socket(address, server.getPort());
        } catch (Exception e) {
            //log something if you want
            return false;
        }
        return true;
    }
}
