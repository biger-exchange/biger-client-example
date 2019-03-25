package com.biger.client.examples;

import com.biger.client.ws.react.BigerMarketDataWebsocketClient;
import reactor.core.scheduler.Schedulers;

import java.net.URI;


public class MarketData {
    public static void main(String[] args) throws Throwable {
        try (BigerMarketDataWebsocketClient client = BigerMarketDataWebsocketClient.newBuilder().uri(new URI("wss://biger.in/ws")).build()) {
            client.start().await();
            System.out.println("BTCUSDT quote - " + client.querySymbolPrice("BTCUSDT").block());
            client.subDeals("BTCUSDT")
                    .subscribeOn(Schedulers.single())
                    .subscribe(System.out::println);
            client.subSymbolPrice("BTCUSDT")
                    .subscribeOn(Schedulers.single())
                    .subscribe(System.out::println);
            client.subMarketDepth("BTCUSDT", 3, "0")
                    .subscribeOn(Schedulers.single())
                    .subscribe(System.out::println);
            Thread.sleep(300000);
        }
    }

}
