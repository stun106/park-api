package com.stun106.parkapi.web.controller;

import com.stun106.parkapi.entity.Usuario;
import com.stun106.parkapi.service.UsuarioService;
import com.stun106.parkapi.web.dto.UsuarioCreateDTO;
import com.stun106.parkapi.web.dto.UsuarioResponseDTO;
import com.stun106.parkapi.web.dto.UsuarioSenhaDTO;
import com.stun106.parkapi.web.dto.mapper.UsuarioMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> create (@Valid  @RequestBody UsuarioCreateDTO usuarioDTO){
        Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(usuarioDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDTO(user));

    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> getById (@PathVariable Long id){
        Usuario user = usuarioService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(UsuarioMapper.toDTO(user));
    }
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> getAll (){
        List<Usuario> user = usuarioService.bustarTodos();
        return ResponseEntity.status(HttpStatus.OK).body(UsuarioMapper.toListDTO(user));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword (@PathVariable Long id,@Valid @RequestBody UsuarioSenhaDTO dto){
        usuarioService.mudarPassword(id,dto.getSenhaAtual(),dto.getNovaSenha(),dto.getConfirmaSenha());
        return ResponseEntity.noContent().build();
    }
}
