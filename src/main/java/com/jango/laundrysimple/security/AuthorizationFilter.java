package com.jango.laundrysimple.security;

import com.jango.laundrysimple.SpringApplicationContext;
import com.jango.laundrysimple.config.SecurityConstants;
import com.jango.laundrysimple.model.Roles;
import com.jango.laundrysimple.model.User;
import com.jango.laundrysimple.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class AuthorizationFilter extends BasicAuthenticationFilter {

    public AuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
        log.info("Authorization filter constructor");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String header = req.getHeader(SecurityConstants.HEADER_STRING);

        if (header == null) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(SecurityConstants.HEADER_STRING);
        if (authorizationHeader == null) {
            return null;
        }

        String token = authorizationHeader.replace(SecurityConstants.TOKEN_PREFIX, "");

        String userId = Jwts.parser().setSigningKey(SecurityConstants.getSecret()).parseClaimsJws(token).getBody()
                .getSubject();

        if (userId == null) {
            return null;
        }
        UserRepository userLoginRepo = (UserRepository) SpringApplicationContext.getBean("userRepository");
        User user = userLoginRepo.findByEmail(userId).get();
        List<Roles> roles = user.getRolesList();
        List<GrantedAuthority> grantedAuthorities = roles.stream().map(r -> {
            return new SimpleGrantedAuthority(r.getName());
        }).collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(userId, null,grantedAuthorities);


    }

}
