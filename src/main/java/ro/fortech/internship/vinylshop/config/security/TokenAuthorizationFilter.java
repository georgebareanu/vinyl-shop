package ro.fortech.internship.vinylshop.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import ro.fortech.internship.vinylshop.user.service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
public class TokenAuthorizationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER_PREFIX = "Bearer ";

    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain)
            throws ServletException, IOException {
        if (isUnsecuredRequest(httpServletRequest)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        String token = getTokenFromRequest(httpServletRequest);
        if (token == null) {
            writeResponse(httpServletResponse, "Authorization header is missing!");
        } else {
            authenticateUser(httpServletRequest, httpServletResponse, token, filterChain);
        }
    }

    private void authenticateUser(
            HttpServletRequest request, HttpServletResponse response, String token, FilterChain chain)
            throws IOException, ServletException {
        String username;
        try {
            username = jwtTokenUtil.getUsername(token);
        } catch (ExpiredJwtException e) {
            writeResponse(response, "Your token is invalid!");
            return;
        }
        UserDetails userDetails = userService.loadUserByUsername(username);
        boolean isValid = jwtTokenUtil.isValid(token, userDetails);
        if (!isValid) {
            writeResponse(response, "Your token is invalid!");
            return;
        }
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private boolean isUnsecuredRequest(HttpServletRequest request) {
        AntPathMatcher matcher = new AntPathMatcher();
        return matcher.match("/api/users/login", request.getRequestURI())
                || matcher.match("/api/vinyls", request.getRequestURI())
                || matcher.match("/api/users", request.getRequestURI())
                || matcher.match("/v2/api-docs", request.getRequestURI())
                || matcher.match("/swagger-resources/**", request.getRequestURI())
                || matcher.match("/webjars/**", request.getRequestURI())
                || matcher.match("/swagger-ui.html/**", request.getRequestURI());
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null
                && authorizationHeader.startsWith(AUTHORIZATION_HEADER_PREFIX)) {
            return authorizationHeader.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
        }
        return null;
    }

    private void writeResponse(HttpServletResponse response, String message) throws IOException {
        response.getWriter().write(new ObjectMapper().writeValueAsString(message));
        response.addHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}
