package com.micronaut.infrastructure.config;

import com.micronaut.core.FraudScoreRepository;
import com.micronaut.core.FraudScoreService;
import com.micronaut.infrastructure.fraudster.FraudsterClient;
import com.micronaut.infrastructure.fraudster.FraudsterScoreProvider;
import com.micronaut.infrastructure.steerclear.SteerClearClient;
import com.micronaut.infrastructure.steerclear.SteerClearScoreProvider;
import lombok.AllArgsConstructor;

import static java.rmi.Naming.bind;

@AllArgsConstructor
public class FraudScoreModule {

    FraudScoreRepository fraudScoreRepository;
    FraudsterClient fraudsterClient;
    SteerClearClient steerClearClient;

    protected void configure() {
//        bind(new FraudScoreService(
//                fraudScoreRepository,
//                new FraudsterScoreProvider(fraudsterClient),
//                new SteerClearScoreProvider(steerClearClient)))
//            .to(FraudScoreService.class);
    }
}
