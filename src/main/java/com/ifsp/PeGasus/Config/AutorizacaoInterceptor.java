package com.ifsp.PeGasus.Config;

import com.ifsp.PeGasus.Model.User;
import com.ifsp.PeGasus.Enum.Tipo;
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
        
        User user = (User) request.getSession().getAttribute("usuarioLogado");
        if (user != null) {
            if (isManagementPath(uri) && user.getTipo() != Tipo.ADMIN) {
                response.sendRedirect("/dashboard");
                return false;
            }
            return true;
        }
        
        response.sendRedirect("/user/logar");
        return false;
    }

    private boolean isManagementPath(String uri) {
        if (uri.startsWith("/admin")) {
            return true;
        }
        if (uri.startsWith("/categoria")) {
            return true;
        }
        if (uri.startsWith("/cupom")) {
            return true;
        }
        if (uri.startsWith("/produto")) {
            if (uri.equals("/produto/lista") || 
                uri.equals("/produto/formulario") || 
                uri.equals("/produto/cadastro") || 
                uri.equals("/produto/atualizar") || 
                uri.endsWith("/editar") || 
                uri.endsWith("/excluirProduto")) {
                return true;
            }
        }
        return false;
    }
}
