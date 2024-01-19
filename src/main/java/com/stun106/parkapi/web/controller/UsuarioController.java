package com.stun106.parkapi.web.controller;

import com.stun106.parkapi.entity.Usuario;
import com.stun106.parkapi.service.UsuarioService;
import com.stun106.parkapi.web.dto.UsuarioCreateDTO;
import com.stun106.parkapi.web.dto.UsuarioResponseDTO;
import com.stun106.parkapi.web.dto.UsuarioSenhaDTO;
import com.stun106.parkapi.web.dto.mapper.UsuarioMapper;
import com.stun106.parkapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Tag(name = "Usuários", description = "Contem todas as informações relativa para cadastro, edição e leitura de um usuário.")
@RestController
@RequestMapping("api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;
    @Operation(summary = "Cria um novo recurso.", description = "Recurso para criar um novo usuario.",
            responses = {@ApiResponse(responseCode = "201", description = "recurso criado com sucesso",
            content = @Content(mediaType = "application/json",schema = @Schema(implementation = UsuarioResponseDTO.class))),
                    @ApiResponse(responseCode = "409",description = "Usuário e-mail ja cadastrado no sistema.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422",description = "Recursos não processados por dados de entrada inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> create (@Valid  @RequestBody UsuarioCreateDTO usuarioDTO){
        Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(usuarioDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDTO(user));

    }
    @Operation(summary = "Recuperar um usuario pelo id.", description = "Recuperar um usuario pelo id.",
            responses = {@ApiResponse(responseCode = "200", description = "recurso criado com sucesso",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = UsuarioResponseDTO.class))),
                    @ApiResponse(responseCode = "404",description = "Recurso não encontrado.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> getById (@PathVariable Long id){
        Usuario user = usuarioService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(UsuarioMapper.toDTO(user));
    }
    @Operation(summary = "Recuperar todos os recursos.", description = "Recuperar todos os recursos.",
            responses = {@ApiResponse(responseCode = "200", description = "todos os recursos recuperados com sucesso",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UsuarioResponseDTO.class)))),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar esse recurso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> getAll (){
        List<Usuario> user = usuarioService.bustarTodos();
        return ResponseEntity.status(HttpStatus.OK).body(UsuarioMapper.toListDTO(user));
    }
    @Operation(summary = "Atualizar senha.", description = "Atualizar senha.",
            responses = {@ApiResponse(responseCode = "200", description = "senha alterada com sucesso.",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = void.class))),
                    @ApiResponse(responseCode = "404",description = "Recurso não encontrado.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "400",description = "Senha incorreta.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword (@PathVariable Long id,@Valid @RequestBody UsuarioSenhaDTO dto){
        usuarioService.mudarPassword(id,dto.getSenhaAtual(),dto.getNovaSenha(),dto.getConfirmaSenha());
        return ResponseEntity.noContent().build();
    }
}
