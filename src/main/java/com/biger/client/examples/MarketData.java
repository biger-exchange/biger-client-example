package com.biger.client.examples;

import com.biger.client.ws.react.BigerMarketDataWebsocketClient;
import reactor.core.scheduler.Schedulers;

import java.net.URI;
import java.time.Duration;
import java.time.Instant;


public class MarketData {
//    static {
//        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "debug");
//    }
    public static void main(String[] args) throws Throwable {

        try (BigerMarketDataWebsocketClient client =
                BigerMarketDataWebsocketClient.newBuilder()
                    .uri(new URI("wss://biger.in/ws"))
                    .build()
        ) {
            System.out.println("BTCUSDT quote - " + client.querySymbolPrice("BTCUSDT").block());
            System.out.println("BTCUSDT kline - " + client.queryKline("BTCUSDT", Instant.now().minus(Duration.ofMinutes(30)), Instant.now(), Duration.ofMinutes(1)).block().toString());
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
