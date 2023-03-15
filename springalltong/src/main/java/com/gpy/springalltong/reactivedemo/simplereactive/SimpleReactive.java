package com.gpy.springalltong.reactivedemo.simplereactive;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * @ClassName SimpleReactive
 * @Description
 * @Author guopy
 * @Date 2023/3/14 10:51
 */
@Slf4j
public class SimpleReactive {
    public static void main(String[] args) throws InterruptedException {
        Flux.range(1,6)
                .doOnRequest(n -> log.info("request {} number", n))
                .doOnComplete(() -> log.info("publisher complete 1"))
                .map(i -> {
                    log.info("publish {}, {}", Thread.currentThread(), i);
                    return i;
                })
                .doOnComplete(() -> log.info("publisher complete 2"))
                .subscribe(i -> log.info("subscribe {}:{}", Thread.currentThread(), i),
                        e -> log.error("error {}", e.toString()),
                        () -> log.info("subscribe complete"),
                        s -> s.request(4)
                );
        Thread.sleep(2000);
    }
}
