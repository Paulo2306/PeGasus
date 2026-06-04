package com.ifsp.PeGasus.Config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AutorizacaoInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        
        if (uri.equals("/user/logar") || uri.equals("/user/registrar") || uri.startsWith("/css/") || uri.startsWith("/js/") || uri.startsWith("/img/")) {
            return true;
        }
        
        if (request.getSession().getAttribute("usuarioLogado") != null) {
            return true;
        }
        
        response.sendRedirect("/user/logar");
        return false;
    }
}
