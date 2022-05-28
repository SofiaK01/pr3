package pack.pr231.config.handler;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException {


        System.out.println("АХУЕТЬ МЫ В ХЭНДЛЕРЕ");

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains("ROLE_ADMIN")) {
            System.out.println("АХУЕТЬ МЫ АДМИН");
            httpServletResponse.setStatus(HttpStatus.OK.value());
            httpServletResponse.sendRedirect("/admin/all-users");

        } else if (roles.contains("ROLE_USER")) {
            System.out.println("АХУЕТЬ МЫ НЕ АДМИН");
            httpServletResponse.setStatus(HttpStatus.OK.value());
            httpServletResponse.sendRedirect("/for-all");
        }
    }
}
