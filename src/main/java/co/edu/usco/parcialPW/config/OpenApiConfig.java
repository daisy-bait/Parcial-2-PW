
package co.edu.usco.parcialPW.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                .title("PARCIAL 2, DOCUMENTACIÓN DE CRUD")
                .version("SNAPSHOT-0.0.1")
                .description("Documentación del sistema de parcial de parqueadero elaborado para el segundo parcial de la asignatura de programación web"));
    }
    
}
