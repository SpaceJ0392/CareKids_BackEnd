package com.aivle.carekids.domain.user.general.interceptor;


import com.aivle.carekids.domain.user.models.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;


/**
 * 권한에 따른 처리를 하기 위한 Interceptor
 * @EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) 를 선언하고
 * Controller 에서 @PreAuthorize 를 사용해도 되지만, Controller 로 가기 전, Interceptor 에서 처리를 하는 것이 낫다고 생각함
 */

public class AccessPermissionCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (request.getRequestURI().startsWith("/admin")) {
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.getRole()))) {
                return true;
            }
        } else {
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority(Role.USER.getRole()))) {
                return true;
            }
        }

        return false;
    }
}