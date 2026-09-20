package com.daniels.usuarios.business.converter;

import com.daniels.usuarios.business.dto.EnderecoDTO;
import com.daniels.usuarios.business.dto.TelefoneDTO;
import com.daniels.usuarios.business.dto.UsuarioDTO;
import com.daniels.usuarios.infrastructure.entity.Endereco;
import com.daniels.usuarios.infrastructure.entity.Telefone;
import com.daniels.usuarios.infrastructure.entity.Usuario;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring" , nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE)
public interface UsuarioUpdateConverter {



    Usuario updateUsuario(UsuarioDTO usuarioDTO, @MappingTarget Usuario entity);

    Endereco updateEndereco (EnderecoDTO enderecoDTO, @MappingTarget Endereco endereco);

    Telefone updateTelefone (TelefoneDTO telefoneDTO, @MappingTarget Telefone telefone);

}
