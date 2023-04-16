package tripagramex.global.security.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import tripagramex.global.security.authentication.Principal;
import tripagramex.global.security.jwt.JwtProcessor;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtProcessor jwtProcessor;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtProcessor jwtProcessor) {
        super(authenticationManager);
        this.jwtProcessor = jwtProcessor;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String jwtHeader = request.getHeader(jwtProcessor.getHeader());

        if (jwtHeader == null || !jwtHeader.startsWith(jwtProcessor.getPrefix())) {
            chain.doFilter(request, response);
            return;
        }

        String jwtToken = jwtProcessor.extractJwtToken(jwtHeader);
        Claims claims = jwtProcessor.verifyJwtToken(jwtToken);

        Long id = Long.valueOf(claims.getSubject());
        String email = (String) claims.get("email");

        List<String> roleList = (List<String>) claims.get("role");
        List<SimpleGrantedAuthority> authorities = roleList.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        Principal principal = new Principal(id, email);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(principal, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}
