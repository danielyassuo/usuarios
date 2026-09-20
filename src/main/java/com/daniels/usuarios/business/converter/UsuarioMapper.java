package com.daniels.usuarios.business.converter;


import com.daniels.usuarios.business.dto.EnderecoDTO;
import com.daniels.usuarios.business.dto.TelefoneDTO;
import com.daniels.usuarios.business.dto.UsuarioDTO;
import com.daniels.usuarios.infrastructure.entity.Endereco;
import com.daniels.usuarios.infrastructure.entity.Telefone;
import com.daniels.usuarios.infrastructure.entity.Usuario;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario paraUsuario (UsuarioDTO usuarioDTO);

    List<Endereco> paraListaEndereco(List<EnderecoDTO> enderecoDTOS);

    Endereco paraEndereco(EnderecoDTO enderecoDTO);

    List<Telefone> paraListaTelefone (List<TelefoneDTO> telefoneDTOS);

    Telefone paraTelefone (TelefoneDTO telefoneDTO);

    UsuarioDTO paraUsuarioDTO (Usuario usuario);

    List<EnderecoDTO> paraListaEnderecoDTO (List<Endereco> enderecos);

    EnderecoDTO paraEnderecoDTO(Endereco endereco);

    List<TelefoneDTO> paraListaTelefoneDTO (List<Telefone> telefones);

    TelefoneDTO paraTelefoneDTO(Telefone telefone);





}
