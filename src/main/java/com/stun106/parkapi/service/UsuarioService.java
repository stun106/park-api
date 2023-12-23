package com.stun106.parkapi.service;

import com.stun106.parkapi.entity.Usuario;
import com.stun106.parkapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    @Transactional
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id){
        return usuarioRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Usuário não encontrado."));
    }
    @Transactional(readOnly = true)
    public List<Usuario> bustarTodos(){
        return usuarioRepository.findAll();
    }

    @Transactional
    public Usuario mudarPassword(Long id, String senhaAtual, String novaSenha, String confirmaSenha){
        if (!novaSenha.equals(confirmaSenha)){
            throw new RuntimeException("Nova senha não confere com confirmação de senha.");
        }
        Usuario user = buscarPorId(id);
        if (!user.getPassword().equals(senhaAtual)){
            throw new RuntimeException("Sua senha não confere.");
        }
        user.setPassword(novaSenha);
        return user;
    }
}
