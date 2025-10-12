package com.alexpauv.pokemon;

import com.alexpauv.pokemon.config.FrontendProperties;
import com.alexpauv.pokemon.service.auth.SecurityProperties;
import com.alexpauv.pokemon.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({RsaKeyProperties.class, SecurityProperties.class, FrontendProperties.class})
@SpringBootApplication
public class PokemonApplication {

	public static void main(String[] args) {
		SpringApplication.run(PokemonApplication.class, args);
	}

}
