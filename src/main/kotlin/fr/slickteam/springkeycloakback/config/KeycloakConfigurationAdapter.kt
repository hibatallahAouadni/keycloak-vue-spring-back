package fr.slickteam.springkeycloakback.config

// TODO
//import org.keycloak.adapters.KeycloakConfigResolver
//import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver
//import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.ComponentScan
//import org.springframework.context.annotation.Configuration
//import org.springframework.http.HttpMethod
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.http.SessionCreationPolicy
//import org.springframework.security.core.Authentication
//import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper
//import org.springframework.security.web.authentication.logout.LogoutFilter
//import org.springframework.security.web.authentication.preauth.x509.X509AuthenticationFilter
//import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy
//import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy
//import org.springframework.web.cors.CorsConfiguration
//import org.springframework.web.cors.CorsConfigurationSource
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource
//import javax.servlet.http.HttpServletRequest
//import javax.servlet.http.HttpServletResponse
//
//@Configuration
//@EnableWebSecurity
//@ConditionalOnProperty(name = ["keycloak.enabled"], havingValue = "true", matchIfMissing = true)
//@ComponentScan(basePackageClasses = [KeycloakSecurityComponents::class])
//class KeycloakConfigurationAdapter : KeycloakWebSecurityConfigurerAdapter() {
//
//    @Bean
//    override fun sessionAuthenticationStrategy(): SessionAuthenticationStrategy {
//        // required for bearer-only applications.
//        return NullAuthenticatedSessionStrategy()
//    }
//
//    @Bean
//    fun KeycloakConfigResolver(): KeycloakConfigResolver {
//        return KeycloakSpringBootConfigResolver()
//    }
//
//    @Autowired
//    @Throws(Exception::class)
//    fun configureGlobal(auth: AuthenticationManagerBuilder) {
//        val keycloakAuthenticationProvider = keycloakAuthenticationProvider()
//        // simple Authority Mapper to avoid ROLE_
//        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(SimpleAuthorityMapper())
//        auth.authenticationProvider(keycloakAuthenticationProvider)
//    }
//
//    @Throws(Exception::class)
//    override fun configure(http: HttpSecurity) {
//        http
//                .sessionManagement()
//                // use previously declared bean
//                .sessionAuthenticationStrategy(sessionAuthenticationStrategy())
//                // keycloak filters for securisation
//                .and()
//                .addFilterBefore(keycloakPreAuthActionsFilter(), LogoutFilter::class.java)
//                .addFilterBefore(keycloakAuthenticationProcessingFilter(), X509AuthenticationFilter::class.java)
//                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
//
//                // add cors options
//                .and().cors()
//                // delegate logout endpoint to spring security
//                .and().logout().addLogoutHandler(keycloakLogoutHandler())
//                .logoutUrl("/logout").logoutSuccessHandler { _: HttpServletRequest, response: HttpServletResponse, _: Authentication -> response.status = HttpServletResponse.SC_OK }
//                // disable csrf because of API mode
//                .and().csrf().disable()
//                // active Stateless service option
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                // manage routes securisation here
//                .authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll()
//                // manage routes securisation here
//                .and().authorizeRequests()
//                .antMatchers(HttpMethod.OPTIONS).permitAll()
//                .antMatchers("/logout", "/", "/public").permitAll()
//                .antMatchers("/private", "/user", "/admin").authenticated()
//                .antMatchers("/user").hasAnyRole("USER, ADMIN")
//                .antMatchers("/admin").hasRole("ADMIN")
//                .anyRequest().denyAll()
//    }
//
//    @Bean
//    fun corsConfigurationSource(): CorsConfigurationSource {
//        val configuration = CorsConfiguration()
//        configuration.allowedOrigins = listOf("*")
//        configuration.allowedMethods = listOf(HttpMethod.OPTIONS.name, "GET", "POST")
//        configuration.allowedHeaders = listOf("Access-Control-Allow-Headers", "Access-Control-Allow-Origin", "Authorization")
//        val source = UrlBasedCorsConfigurationSource()
//        source.registerCorsConfiguration("/**", configuration)
//        return source
//    }
//}