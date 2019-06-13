package fr.slickteam.springkeycloakback.config

import org.keycloak.adapters.KeycloakConfigResolver
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper
import org.springframework.security.web.authentication.logout.LogoutFilter
import org.springframework.security.web.authentication.preauth.x509.X509AuthenticationFilter
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class OwnKeycloakSecurityConfiguration {
    @Configuration
    @EnableWebSecurity
    @ConditionalOnProperty(name = ["keycloak.enabled"], havingValue = "true", matchIfMissing = true)
    @ComponentScan(basePackageClasses = [KeycloakSecurityComponents::class])
    class KeycloakConfigurationAdapter : KeycloakWebSecurityConfigurerAdapter() {
        /**
         * Defines the session authentication strategy.
         */
        @Bean
        override fun sessionAuthenticationStrategy(): SessionAuthenticationStrategy {
            // required for bearer-only applications.
            return NullAuthenticatedSessionStrategy()
        }

        /**
         * Registers the KeycloakAuthenticationProvider with the authentication manager.
         */
        @Autowired
        @Throws(Exception::class)
        fun configureGlobal(auth: AuthenticationManagerBuilder) {
            val keycloakAuthenticationProvider = keycloakAuthenticationProvider()
            // simple Authority Mapper to avoid ROLE_
            keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(SimpleAuthorityMapper())
            auth.authenticationProvider(keycloakAuthenticationProvider)
        }

        /**
         * Required to handle spring boot configurations
         *
         * @return
         */
        @Bean
        fun KeycloakConfigResolver(): KeycloakConfigResolver {
            return KeycloakSpringBootConfigResolver()
        }

        @Throws(Exception::class)
        override fun configure(http: HttpSecurity) {
            http
                    .sessionManagement()
                    // use previously declared bean
                    .sessionAuthenticationStrategy(sessionAuthenticationStrategy())


                    // keycloak filters for securisation
                    .and()
                    .addFilterBefore(keycloakPreAuthActionsFilter(), LogoutFilter::class.java)
                    .addFilterBefore(keycloakAuthenticationProcessingFilter(), X509AuthenticationFilter::class.java)
                    .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())

                    // add cors options
                    .and().cors()
                    // delegate logout endpoint to spring security

                    .and()
                    .logout()
                    .addLogoutHandler(keycloakLogoutHandler())
                    .logoutUrl("/logout").logoutSuccessHandler { request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication -> response.status = HttpServletResponse.SC_OK }
                    .and().apply(CommonSpringKeycloakSecuritAdapter())


        }


        @Bean
        fun corsConfigurationSource(): CorsConfigurationSource {
            val configuration = CorsConfiguration()
            configuration.allowedOrigins = Arrays.asList("*")
            configuration.allowedMethods = Arrays.asList(HttpMethod.OPTIONS.name, "GET", "POST")
            configuration.allowedHeaders = Arrays.asList("Access-Control-Allow-Headers", "Access-Control-Allow-Origin", "Authorization")
            val source = UrlBasedCorsConfigurationSource()
            source.registerCorsConfiguration("/**", configuration)
            return source
        }
    }

    class CommonSpringKeycloakSecuritAdapter : AbstractHttpConfigurer<CommonSpringKeycloakSecuritAdapter, HttpSecurity>() {

        @Throws(Exception::class)
        override fun init(http: HttpSecurity) {
            // any method that adds another configurer
            // must be done in the init method
            http
                    // disable csrf because of API mode
                    .csrf().disable()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                    .and()
                    // manage routes securisation here
                    .authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll()

                    // manage routes securisation here
                    .and()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.OPTIONS).permitAll()


                    .antMatchers("/logout", "/", "/unsecured").permitAll()
                    .antMatchers("/api/**").permitAll()
                    .antMatchers("/test").authenticated()
                    .antMatchers("/user").hasRole("user")
                    .antMatchers("/admin").hasRole("admin")
                    .anyRequest().denyAll()

        }

    }
}