package api.converter;


import com.daniels.usuarios.business.converter.UsuarioConverter;
import com.daniels.usuarios.business.dto.EnderecoDTO;
import com.daniels.usuarios.business.dto.TelefoneDTO;
import com.daniels.usuarios.business.dto.UsuarioDTO;
import com.daniels.usuarios.infrastructure.entity.Endereco;
import com.daniels.usuarios.infrastructure.entity.Telefone;
import com.daniels.usuarios.infrastructure.entity.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UsuarioConverterTest {

    @InjectMocks
    UsuarioConverter usuarioConverter;

    @Mock
    Usuario usuario;

    @Mock
    Endereco endereco;

    @Mock
    Telefone telefone;

    @Mock
    UsuarioDTO usuarioDTO;

    @Mock
    EnderecoDTO enderecoDTO;

    @Mock
    TelefoneDTO telefoneDTO;



    @BeforeEach
    public void setup(){
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
                .nome("Usuario")
                .email("usuario.email@gmail.com")
                .senha("minhaSenha")
                .enderecos(enderecoDTOList)
                .telefones(telefonesDTOList)
                .build();
    }


    @Test
    void deveConverterParaUsuarioEntity (){
       Usuario entity = usuarioConverter.paraUsuario(usuarioDTO);

       assertEquals(usuario, entity);
    }
}
