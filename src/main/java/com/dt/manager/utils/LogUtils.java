package com.dt.manager.utils;

import org.slf4j.MDC;

public class LogUtils {
    private LogUtils() {
    }

    private static final String CORRELATION_ID_KEY = "correlationId";

    public static String getCorrelationId() {
        return MDC.get(CORRELATION_ID_KEY);
    }

    public static void setCorrelationId(String correlationId) {
        MDC.put(CORRELATION_ID_KEY, correlationId);
    }

    public static void removeCorrelationId() {
        MDC.remove(CORRELATION_ID_KEY);
    }
}
