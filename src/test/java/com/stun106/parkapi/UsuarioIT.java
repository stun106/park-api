package com.stun106.parkapi;

import com.stun106.parkapi.web.dto.UsuarioCreateDTO;
import lombok.AllArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;


@AllArgsConstructor
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "sql/usuarios/usuario-insert.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "sql/usuarios/usuario-delete.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
public class UsuarioIT {
    private final WebTestClient testClient;

    @Test
    public void createUsuariosComSenhaEPasswordValidosRetornarUsuarioCreateStatus201(){
        UsuarioCreateDTO responseBody = testClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDTO("stun106@exanple.com", "197319"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UsuarioCreateDTO.class)
                .returnResult().getResponseBody();
        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getUsername()).isEqualTo("stun106@exanple.com");

    }

}
