package com.dt.manager.filter;

import com.dt.manager.utils.LogUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
public class CorrelationIdFilter implements Filter {
    private static final String CORRELATION_ID_HEADER_NAME = "X-correlation-Id";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String correlationId = ((HttpServletRequest) request).getHeader(CORRELATION_ID_HEADER_NAME);
        if (correlationId == null || correlationId.trim().isEmpty()) {
            correlationId = UUID.randomUUID().toString();
        }
        LogUtils.setCorrelationId(correlationId);
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader(CORRELATION_ID_HEADER_NAME, correlationId);
        try {
            log.info("Processing request with CORRELATION ID: {}", correlationId);
            chain.doFilter(request, response);
        } finally {
            LogUtils.removeCorrelationId();
            log.info("Finished request with CORRELATION ID: {}", correlationId);
        }
    }
}
