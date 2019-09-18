/**
 * Copyright 2003-2017 Monitise Group Limited. All Rights Reserved.
 * Save to the extent permitted by law, you may not use, copy, modify,
 * distribute or create derivative works of this material or any part
 * of it without the prior written consent of Monitise Group Limited.
 * Any reproduction of this material must contain this notice.
 */

package com.monitise.prometheus_demo.service;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RemoteService {

    private static Random random = new Random();

    public void someRemoteCall() {
        final int MAX_SLEEP_TIME = 2_000;
        int timeToSleep = random.nextInt(MAX_SLEEP_TIME);
        try {
            Thread.sleep(timeToSleep);
        } catch (InterruptedException e) {
            //do nothing
        }
        throw new IllegalArgumentException(" zero val");
    }
}
