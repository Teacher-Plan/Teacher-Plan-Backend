package com.github.ioloolo.teacher_plan.common.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoggingConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(@NotNull InterceptorRegistry registry) {
        registry.addInterceptor(new LoggingInterceptor());
    }

    @Component
    @Slf4j
    private static final class LoggingInterceptor implements HandlerInterceptor {

        @Override
        public boolean preHandle(HttpServletRequest request,
                                 @NotNull HttpServletResponse response,
                                 @NotNull Object handler) {

            log.info("[Request] {} {}{} {}",
                    request.getMethod(),
                    request.getRequestURI(), (request.getQueryString() == null ? "" : "?%s".formatted(request.getQueryString())),
                    request.getRemoteAddr());

            request.setAttribute("startTime", System.currentTimeMillis());

            return true;
        }

        @Override
        public void afterCompletion(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull Object handler,
                                    Exception exception) {

            if (exception != null) {
                log.error("[Response] {}", exception.getMessage());

                return;
            }

            long startTime = (long) request.getAttribute("startTime");
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;

            log.info("[Response] {} {}{} {} {}ms {}",
                    request.getMethod(),
                    request.getRequestURI(), (request.getQueryString() == null ? "" : "?%s".formatted(request.getQueryString())),
                    request.getRemoteAddr(),
                    elapsedTime,
                    response.getStatus());
        }
    }
}
