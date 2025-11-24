package com.Rootale.config;

import com.Rootale.security.service.CustomOAuth2UserService;
import com.Rootale.security.service.OAuth2FailureHandler;
import com.Rootale.security.service.OAuth2SuccessHandler;
import com.Rootale.security.jwt.JwtAuthFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.*;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity(debug = true)
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler successHandler;
    private final OAuth2FailureHandler failureHandler;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final ClientRegistrationRepository clientRegistrationRepository;

    @Bean
    @Order(1)
    SecurityFilterChain oauthChain(HttpSecurity http) throws Exception {
        var def = new DefaultOAuth2AuthorizationRequestResolver(clientRegistrationRepository,
                "/oauth2/authorization");

        OAuth2AuthorizationRequestResolver resolver = new OAuth2AuthorizationRequestResolver() {
            @Override
            public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
                OAuth2AuthorizationRequest r = def.resolve(request);
                return customize(r);
            }
            @Override
            public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
                OAuth2AuthorizationRequest r = def.resolve(request, clientRegistrationId);
                return customize(r);
            }
            private OAuth2AuthorizationRequest customize(OAuth2AuthorizationRequest r) {
                if (r == null) return null;
                var extra = new java.util.HashMap<String, Object>(r.getAdditionalParameters());
                extra.put("prompt", "select_account");
                return OAuth2AuthorizationRequest.from(r)
                        .additionalParameters(extra)
                        .build();
            }
        };

        http
            .csrf(csrf -> csrf.disable())
            // ⭐ OAuth2 관련 경로만 매칭
            .securityMatcher("/login", "/logout", "/oauth2/**", "/login/oauth2/**", "/", "/error")
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/error", "/oauth2/**", "/login/oauth2/**", "/login").permitAll()
                .anyRequest().authenticated()
            )
            .exceptionHandling(e -> e.authenticationEntryPoint(
                new org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint("/oauth2/authorization/google")
            ))
            .sessionManagement(sm -> sm
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
            )
            .oauth2Login(oauth -> oauth
                .loginPage("/oauth2/authorization/google")
                .authorizationEndpoint(a -> a.authorizationRequestResolver(resolver))
                .userInfoEndpoint(u -> u.userService(customOAuth2UserService))
                .successHandler(successHandler)
                .failureHandler(failureHandler)
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "ACCESS_TOKEN", "REFRESH_TOKEN")
                .addLogoutHandler((req, res, auth) -> {
                    // Authorization 헤더 처리
                    String authzHeader = req.getHeader("Authorization");
                    if (authzHeader != null && authzHeader.startsWith("Bearer ")) {
                        String accessToken = authzHeader.substring(7);
                        System.out.println("🔴 Access token invalidated: " + accessToken.substring(0, Math.min(20, accessToken.length())) + "...");
                    }

                    // 세션 정리
                    jakarta.servlet.http.HttpSession session = req.getSession(false);
                    if (session != null) {
                        try {
                            session.removeAttribute("SPRING_SECURITY_CONTEXT");
                            session.removeAttribute(
                                "org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizedClientRepository.AUTHORIZED_CLIENTS"
                            );
                            session.invalidate();
                            System.out.println("🔴 Session invalidated and OAuth2 clients removed");
                        } catch (Exception e) {
                            System.err.println("⚠️ Session cleanup failed: " + e.getMessage());
                        }
                    }

                    // 쿠키 수동 삭제
                    addExpiredCookie(res, "ACCESS_TOKEN");
                    addExpiredCookie(res, "REFRESH_TOKEN");
                    addExpiredCookie(res, "JSESSIONID");

                    SecurityContextHolder.clearContext();
                    System.out.println("🔴 Logout completed");
                })
                .logoutSuccessHandler((req, res, auth) -> {
                    res.setStatus(200);
                    res.setContentType("application/json;charset=UTF-8");
                    String googleLogoutUrl = "https://accounts.google.com/Logout";
                    String jsonResponse = String.format(
                        "{\"message\":\"logged out\",\"idpLogoutUrl\":\"%s\"}", 
                        googleLogoutUrl
                    );
                    res.getWriter().write(jsonResponse);
                })
            )
            .httpBasic(b -> b.disable())
            .formLogin(f -> f.disable());
    return http.build();
}

// ⭐ 쿠키 삭제 헬퍼 메서드
private void addExpiredCookie(jakarta.servlet.http.HttpServletResponse res, String name) {
    Cookie cookie = new Cookie(name, null);
    cookie.setPath("/");
    cookie.setMaxAge(0);
    cookie.setHttpOnly(true);
    res.addCookie(cookie);
}

    @Bean
    @Order(2)
    SecurityFilterChain api(HttpSecurity http) throws Exception {
        RequestMatcher apiMatcher = apiRequestMatcher(http);
        http
                .securityMatcher(apiMatcher)
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {}) // 필요 시 CORS
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                auth.requestMatchers(
                        // Swagger & OpenAPI (springdoc)
                        "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/v3/api-docs.yaml",
                        "/swagger-resources/**", "/webjars/**", "/actuator/health", "/actuator/info",
                        "/user/**", "/health"
                ).permitAll()
                //cors preflight
                .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, org.springframework.security.web.authentication.logout.LogoutFilter.class)
                .httpBasic(b -> b.disable())
                .formLogin(f -> f.disable())
                .exceptionHandling(e -> e
                        // API는 401/403을 JSON으로 주는 엔트리포인트/핸들러를 사용
                        .authenticationEntryPoint(new org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint())
                        .accessDeniedHandler(new org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler())
                );
        return http.build();
    }

    @Bean
    @Order(3)
    SecurityFilterChain catchAll(HttpSecurity http) throws Exception {

        // 1) API/XHR 요청 판별 매처 (JSON accept, Ajax 헤더 등)
        var jsonAccept = new org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher(
                "Accept", "application/json");
        var ajax = new org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher(
                "X-Requested-With", "XMLHttpRequest");
        var apiLike = new org.springframework.security.web.util.matcher.OrRequestMatcher(jsonAccept, ajax);

        // 2) 엔트리포인트 분기: API → 401(Bearer), 그 외 → OAuth 로그인으로 302
        var mappings = new java.util.LinkedHashMap<org.springframework.security.web.util.matcher.RequestMatcher,
                org.springframework.security.web.AuthenticationEntryPoint>();
        mappings.put(apiLike, new org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint());

        var delegatingEntryPoint =
                new org.springframework.security.web.authentication.DelegatingAuthenticationEntryPoint(mappings);
        delegatingEntryPoint.setDefaultEntryPoint(
                new org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint("/oauth2/authorization/google")
        );

        http
                // 캐치올
                .securityMatcher("/**")
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))

                // 화이트리스트 전역 허용(정적/스웨거 등). 나머지는 모두 인증 필요
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
//                                "/", "/error", "/login", "/logout", "/oauth2/**",
                                "/swagger-ui.html", "/swagger-ui/**",
                                "/v3/api-docs/**", "/v3/api-docs.yaml",
                                "/swagger-resources/**", "/webjars/**",
                                "/actuator/health", "/actuator/info",
                                "/css/**", "/js/**", "/images/**", "/assets/**", "/static/**", "/favicon.ico"
                        ).permitAll()
                        .anyRequest().authenticated()
                )

                // JWT 먼저 시도 (Bearer가 있으면 여기서 인증 완료)
                .addFilterBefore(jwtAuthFilter, org.springframework.security.web.authentication.logout.LogoutFilter.class)

                // 브라우저 케이스에선 fe baseUrl/login(OAuth2 로그인 플로우)로 보낼 수 있게 활성화
                .oauth2Login(oauth -> {
                    // 필요하면 resolver로 prompt=select_account 유지
                })

                // 인증 안 된 접근 시 "요청 성격"에 따라 분기
                .exceptionHandling(e -> e.authenticationEntryPoint(delegatingEntryPoint))

                .httpBasic(b -> b.disable())
                .formLogin(f -> f.disable());

        return http.build();
    }


    private RequestMatcher apiRequestMatcher(HttpSecurity http) {
        // Accept/Content-Type 로 JSON 요청 판별
        var cns = http.getSharedObject(org.springframework.web.accept.ContentNegotiationStrategy.class);
        var jsonByAccept = new org.springframework.security.web.util.matcher.MediaTypeRequestMatcher(
                cns, org.springframework.http.MediaType.APPLICATION_JSON);
        jsonByAccept.setUseEquals(true);      // JSON 만 정확히 매칭
        jsonByAccept.setIgnoredMediaTypes(Set.of(org.springframework.http.MediaType.ALL));

        // Authorization: Bearer ...
        RequestMatcher bearerAuth = request -> {
            String auth = request.getHeader("Authorization");
            return auth != null && auth.startsWith("Bearer ");
        };

        // AJAX 또는 커스텀 헤더
        RequestMatcher ajax = new org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher(
                "X-Requested-With", "XMLHttpRequest");
        RequestMatcher customApiHeader = new org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher(
                "X-API-Request", "true");

        // Content-Type: application/json (POST/PUT/PATCH 등)
        RequestMatcher jsonByContentType = request -> {
            String ct = request.getContentType();
            return ct != null && ct.startsWith(org.springframework.http.MediaType.APPLICATION_JSON_VALUE);
        };

        return new OrRequestMatcher(
                jsonByAccept, jsonByContentType, bearerAuth, ajax, customApiHeader
        );
    }

    @Bean
    public LogoutSuccessHandler oidcLogoutSuccessHandler() {
        var handler = new org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler(
                clientRegistrationRepository
        );
        handler.setPostLogoutRedirectUri("{baseUrl}/"); // 로그아웃 후 돌아올 URL
        return handler;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
