package com.stun106.parkapi.web.dto.mapper;

import com.stun106.parkapi.entity.Usuario;
import com.stun106.parkapi.web.dto.UsuarioCreateDTO;
import com.stun106.parkapi.web.dto.UsuarioResponseDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

public class UsuarioMapper {
    public static Usuario toUsuario(UsuarioCreateDTO usuarioCreateDTO){
        return new ModelMapper().map(usuarioCreateDTO,Usuario.class);
    }
    public static UsuarioResponseDTO toDTO(Usuario usuario){
        String role = usuario.getRole().name().substring("ROLE_".length());
        PropertyMap<Usuario,UsuarioResponseDTO> props = new PropertyMap<Usuario, UsuarioResponseDTO>() {
            @Override
            protected void configure() {
                map().setRole(role);
            }
        };
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(props);
        return modelMapper.map(usuario, UsuarioResponseDTO.class);
    }

    public static List<UsuarioResponseDTO> toListDTO(List<Usuario> usuariosList){
        return usuariosList.stream().map(UsuarioMapper::toDTO).collect(Collectors.toList());
    }

}
