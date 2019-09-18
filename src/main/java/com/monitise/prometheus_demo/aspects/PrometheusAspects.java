/**
 * Copyright 2003-2017 Monitise Group Limited. All Rights Reserved.
 * Save to the extent permitted by law, you may not use, copy, modify,
 * distribute or create derivative works of this material or any part
 * of it without the prior written consent of Monitise Group Limited.
 * Any reproduction of this material must contain this notice.
 */

package com.monitise.prometheus_demo.aspects;

import io.prometheus.client.Counter;
import io.prometheus.client.Summary;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@SuppressWarnings("unused")
public class PrometheusAspects {

    //    `http_requests_total{kubernetes_namespace!="",kubernetes_pod_name!=""}`
    private static final Counter requestCounter = Counter.build()
            .help("Total number of requests in k8s")
            .labelNames(
//                    "kubernetes_namespace", "kubernetes_pod_name",
                    "endpoint", "method")
            .name("num_of_requests")
            .register();

    private static final Summary totalTime = Summary.build()
            .name("mw_processing_time")
            .help("Stores the time the request spent in the MW")
            .register();

    private static final Summary backendTime = Summary.build()
            .name("backend_processing_time")
            .help("Stores the time the request spent in the backend")
            .register();

    private Summary.Timer mwTimer;
    private Summary.Timer backendTimer;

    @Before("@annotation(org.springframework.web.bind.annotation.RequestMapping) && "
            + "args(request,..) && "
            + "within(com.monitise.prometheus_demo..*)")
    private void beforeControllerMethod(HttpServletRequest request) {

//        String namespace = "null";
//        String podname = "null";
//        try {
//            namespace = K8sUtil.getPodNamespace();
//            podname = K8sUtil.getPodName();
//        } catch (Exception e) {
//
//        }

        requestCounter.labels(
//                namespace,
//                podname,
                request.getRequestURI(),
                request.getMethod())
                .inc();
        mwTimer = totalTime.startTimer();
    }

    @After("@annotation(org.springframework.web.bind.annotation.RequestMapping) && "
            + "args(request,..) && "
            + "within(com.monitise.prometheus_demo..*)")
    private void exitControllerMethod(HttpServletRequest request) {
        mwTimer.observeDuration();
    }

    @Before("execution(* com.monitise.prometheus_demo.service.*.*(..))")
    private void startBackendTimer() {
        backendTimer = backendTime.startTimer();
    }

    @After("within(com.monitise.prometheus_demo.service..*)")
    public void observeBackendTimer() {
        backendTimer.observeDuration();
    }
}
