package api.converter;

import com.daniels.usuarios.business.converter.UsuarioUpdateConverter;
import com.daniels.usuarios.business.dto.EnderecoDTO;
import com.daniels.usuarios.business.dto.TelefoneDTO;
import com.daniels.usuarios.business.dto.UsuarioDTO;
import com.daniels.usuarios.infrastructure.entity.Endereco;
import com.daniels.usuarios.infrastructure.entity.Telefone;
import com.daniels.usuarios.infrastructure.entity.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsuarioUpdateMapperTest {


    UsuarioUpdateConverter usuarioUpdateConverter;

    Usuario usuario;

    Usuario usuarioEsperado;

    Endereco endereco;

    Telefone telefone;

    UsuarioDTO usuarioDTO;

    EnderecoDTO enderecoDTO;

    TelefoneDTO telefoneDTO;

    @BeforeEach
    public void setup(){

        usuarioUpdateConverter = Mappers.getMapper(UsuarioUpdateConverter.class);

        endereco = Endereco.builder()
                .rua("Rua Eurico Hummig")
                .numero(577L)
                .complemento("Apto 401")
                .cidade("Londrina")
                .estado("PR")
                .cep("86050464")
                .build();
        List<Endereco> enderecoList = List.of(endereco);

        telefone = Telefone.builder()
                .numero("991368486")
                .ddd("043")
                .build();

        List<Telefone> telefonesList = List.of(telefone);

        usuario =  Usuario.builder()
                .nome("Usuario")
                .email("usuario.email@gmail.com")
                .senha("minhaSenha")
                .enderecos(enderecoList)
                .telefones(telefonesList)
                .build();


        enderecoDTO = EnderecoDTO.builder()
                .rua("Rua Eurico Hummig")
                .numero(577L)
                .complemento("Apto 401")
                .cidade("Londrina")
                .estado("PR")
                .cep("86050464")
                .build();
        List<EnderecoDTO> enderecoDTOList = List.of(enderecoDTO);


        telefoneDTO = TelefoneDTO.builder()
                .numero("991368486")
                .ddd("043")
                .build();

        List<TelefoneDTO> telefonesDTOList = List.of(telefoneDTO);


        usuarioDTO = UsuarioDTO.builder()
                .nome("Usuario teste")
                .email("usuario.email@gmail.com")
                .senha("minhaSenha")
                .enderecos(enderecoDTOList)
                .telefones(telefonesDTOList)
                .build();

        usuarioEsperado = Usuario.builder()
                .nome("Usuario teste")
                .email("usuario.email@gmail.com")
                .senha("minhaSenha")
                .enderecos(enderecoList)
                .telefones(telefonesList)
                .build();
    }

    @Test
    void deveAtualizarUsuario (){
        Usuario entity = usuarioUpdateConverter.updateUsuario(usuarioDTO, usuario);

        assertEquals(usuarioEsperado, entity);
    }




}
