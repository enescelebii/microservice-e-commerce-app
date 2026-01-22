package com.vena.gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;


@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/eureka/**").permitAll()
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults()));
        return http.build();
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        //
        String jwkSetUri = "http://keycloak:8080/realms/ecommerce/protocol/openid-connect/certs";

        NimbusReactiveJwtDecoder jwtDecoder = NimbusReactiveJwtDecoder.withJwkSetUri(jwkSetUri).build();

        /* 2 günümü aldı bu problemi çözmek ->
        * mikroservisler dockerda konteinerde çalışırken
        * biz postman den istek atınca Split-Horizon adlı bölünmüş ağ problemi ile karşılaştık bunun nedeni ise postman local cihaz olarak istek atıyor docker ise farklı bir yöndeki ağlara bakıyor bunu farklı kanal olarak düşünebiliriz oyuzden gelen issurer uri yi kabul etmiyor bunu aşmak için hardcoded url verdik öteyandan bu bir güvenlik açığı değil hala keycloak çalışıyor ve dışarıdan token sızması muhtemel değil başka alternatif çözü yolu ise bilgisayardaki hosts dosyasında 127.0.0.1 <isim> ve keycloak'ı KC_HOSTNAME=<isim> şeklinde ayağa kaldırırdsak http://<isim>:8080 şeklinde issurer uri alabilecektik dediğim gibi 2 günde anladım daha öncesinde bir eğitimde bunu yapmıştım ama bu soruna çağre oldugunu bilmiyordum onun için aklıma gelmedi ayrıca o kubernetes podunda çalışıyordu işler fakrlıydı hoş bir bilgi oldu bunu okuyan varsa da kanka ben yazarak öğreniyorum da oyuzden yazdım aklımda kalsın diye sen neden okudun buraları :D*/
        jwtDecoder.setJwtValidator(JwtValidators.createDefaultWithIssuer("http://localhost:9099/realms/ecommerce"));

        return jwtDecoder;
    }
}
