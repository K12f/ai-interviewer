package io.github.k12f.aiinterview.infra.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.SneakyThrows;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * @author k12
 */
@Component
@Order(-1)
public class ExceptionFilter implements Filter {

    @Override
    @SneakyThrows
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            request.setAttribute("filter.exception", e);
            request.getRequestDispatcher("/exception/reThrow").forward(request, response);
        }
    }
}
