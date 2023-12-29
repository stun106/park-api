package com.stun106.parkapi.service;

import com.stun106.parkapi.entity.Usuario;
import com.stun106.parkapi.service.exception.EntityNotfoundExption;
import com.stun106.parkapi.repository.UsuarioRepository;
import com.stun106.parkapi.service.exception.PasswordInvalidException;
import com.stun106.parkapi.service.exception.UsernameUniqueViolationExeception;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    @Transactional
    public Usuario salvar(Usuario usuario) {
        try{
        return usuarioRepository.save(usuario);
        }catch (DataIntegrityViolationException ex){
            throw new UsernameUniqueViolationExeception(String.format("Username {%s} já cadastrado", usuario.getUsername()));
        }
    }
    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id){
        return usuarioRepository.findById(id).orElseThrow(() ->
                new EntityNotfoundExption(String.format("Usuário id:%s não encontrado.",id)));
    }
    @Transactional(readOnly = true)
    public List<Usuario> bustarTodos(){
        return usuarioRepository.findAll();
    }

    @Transactional
    public Usuario mudarPassword(Long id, String senhaAtual, String novaSenha, String confirmaSenha){
        if (!novaSenha.equals(confirmaSenha)){
            throw new PasswordInvalidException("Nova senha não confere com confirmação de senha.");
        }
        Usuario user = buscarPorId(id);
        if (!user.getPassword().equals(senhaAtual)){
            throw new PasswordInvalidException("Sua senha não confere.");
        }
        user.setPassword(novaSenha);
        return user;
    }
}
