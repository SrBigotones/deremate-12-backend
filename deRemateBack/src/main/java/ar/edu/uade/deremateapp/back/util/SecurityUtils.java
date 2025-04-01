package ar.edu.uade.deremateapp.back.util;

import ar.edu.uade.deremateapp.back.model.Usuario;
import ar.edu.uade.deremateapp.back.security.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import ar.edu.uade.deremateapp.back.security.CustomUserDetails;

public class SecurityUtils {
    public static Usuario getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return ((CustomUserDetails) authentication.getPrincipal()).getUser();
        }
        return null;
    }
}