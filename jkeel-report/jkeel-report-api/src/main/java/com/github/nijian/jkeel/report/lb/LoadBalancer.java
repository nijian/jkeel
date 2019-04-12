package com.github.nijian.jkeel.report.lb;

import com.netflix.client.DefaultLoadBalancerRetryHandler;
import com.netflix.client.RetryHandler;
import com.netflix.loadbalancer.*;
import com.netflix.loadbalancer.reactive.LoadBalancerCommand;
import com.netflix.loadbalancer.reactive.ServerOperation;
import rx.Observable;

import java.util.List;

/**
 * Client side load balancer.
 */
public final class LoadBalancer {

    private final ILoadBalancer loadBalancer;

    // retry handler that does not retry on same server, but on a different server
    private final RetryHandler retryHandler = new DefaultLoadBalancerRetryHandler(0, 1, true);

    public LoadBalancer(List<Server> serverList) {
        loadBalancer = LoadBalancerBuilder.newBuilder().withPing(new ServerPing()).buildFixedServerListLoadBalancer(serverList);
    }

    public String findServer() {
        return LoadBalancerCommand.<String>builder()
                .withLoadBalancer(loadBalancer)
                .withRetryHandler(retryHandler)
                .build()
                .submit(new ServerOperation<String>() {
                    @Override
                    public Observable<String> call(Server server) {
                        try {
                            return Observable.just(server.getHost() + ":" + server.getPort());
                        } catch (Exception e) {
                            return Observable.error(e);
                        }
                    }
                }).toBlocking().first();
    }

    public LoadBalancerStats getLoadBalancerStats() {
        return ((BaseLoadBalancer) loadBalancer).getLoadBalancerStats();
    }
}
