package com.duhan.restclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StopWatch;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@SpringBootApplication
public class RestclientApplication {

    @Autowired
    WebClient.Builder webClientBuilder;

    public static void main(String[] args) {
        SpringApplication.run(RestclientApplication.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {

            StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            WebClient webClient = webClientBuilder.baseUrl("https://api.github.com").build();

            //==========Mono==========// 배열을 한뭉탱이로 받겠다.
            Mono<GithubRepository[]> reposMono = webClient.get().uri("/users/duhanmo/repos").retrieve().bodyToMono(GithubRepository[].class);
            Mono<GithubCommit[]> commitsMono = webClient.get().uri("/repos/duhanmo/algorithm/commits").retrieve().bodyToMono(GithubCommit[].class);

            commitsMono.doOnSuccess(ca -> {
                Arrays.stream(ca).forEach(c -> System.out.println("commit: " + c.getSha()));
            }).subscribe();
            reposMono.doOnSuccess(ra -> {
                Arrays.stream(ra).forEach(r -> System.out.println("repo: " + r.getUrl()));
            }).subscribe();

            // =========Flux=============// 건바이건으로 받겠다.
            Flux<GithubRepository> repoFlux = webClient.get().uri("/users/duhanmo/repos").retrieve().bodyToFlux(GithubRepository.class);
            Flux<GithubCommit> commitFlux = webClient.get().uri("/repos/duhanmo/algorithm/commits").retrieve().bodyToFlux(GithubCommit.class);
            // 한건 한건당 doOnNext함수를 탈 것이다.
            repoFlux.doOnNext(r -> {
                System.out.println("repo: " + r.getUrl());
            }).subscribe();
            commitFlux.doOnNext(c -> {
                System.out.println("commit: " + c.getUrl());
            }).subscribe();

            stopWatch.stop();
            System.out.println(stopWatch.prettyPrint());
        };
    }
}
