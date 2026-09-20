package api;


import com.daniels.usuarios.business.UsuarioService;
import com.daniels.usuarios.business.controller.GlobalExceptionHandler;
import com.daniels.usuarios.business.controller.UsuarioController;
import com.daniels.usuarios.business.dto.EnderecoDTO;
import com.daniels.usuarios.business.dto.TelefoneDTO;
import com.daniels.usuarios.business.dto.UsuarioDTO;
import com.daniels.usuarios.infrastructure.Repository.EnderecoRepository;
import com.daniels.usuarios.infrastructure.Repository.TelefoneRepository;
import com.daniels.usuarios.infrastructure.entity.Endereco;
import com.daniels.usuarios.infrastructure.entity.Telefone;
import com.daniels.usuarios.infrastructure.entity.Usuario;
import com.daniels.usuarios.infrastructure.exceptions.UnauthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UsuarioControllerTest {
    @InjectMocks
    UsuarioController usuarioController;

    @Mock
    UsuarioService usuarioService;

    private UnauthorizedException exception =
            new UnauthorizedException("credenciais inválidas");

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private TelefoneRepository telefoneRepository;



    Usuario usuario;

    Endereco endereco;

    Telefone telefone;

    UsuarioDTO usuarioDTO;

    EnderecoDTO enderecoDTO;

    TelefoneDTO telefoneDTO;

    private String url;

    private String json;

    private String enderecoJson;

    private String telefoneJson;


    @BeforeEach
    void setup () {
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).setControllerAdvice(new GlobalExceptionHandler()).alwaysDo(print()).build();
        url = "/usuario";




        endereco = Endereco.builder()
                .id(1L)
                .rua("Rua Eurico Hummig")
                .numero(577L)
                .complemento("Apto 401")
                .cidade("Londrina")
                .estado("PR")
                .cep("86050464")
                .build();
        List<Endereco> enderecoList = List.of(endereco);

        telefone = Telefone.builder()
                .id(1L)
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
                .id(1L)
                .rua("Rua Eurico Hummig")
                .numero(577L)
                .complemento("Apto 401")
                .cidade("Londrina")
                .estado("PR")
                .cep("86050464")
                .build();
        List<EnderecoDTO> enderecoDTOList = List.of(enderecoDTO);


        telefoneDTO = TelefoneDTO.builder()
                .id(1L)
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

        json = objectMapper.writeValueAsString(usuarioDTO);
        enderecoJson = objectMapper.writeValueAsString(enderecoDTO);
        telefoneJson = objectMapper.writeValueAsString(telefoneDTO);
    }


    @Test
    void deveSalvarUsuarioComSucesso () throws Exception {
        when(usuarioService.salvaUsuario(usuarioDTO)).thenReturn(usuarioDTO);

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isOk());

        verify(usuarioService).salvaUsuario(usuarioDTO);
        verifyNoMoreInteractions(usuarioService);
    }

    @Test
    void naoDeveSalvarUsuarioCasoJsonNullo () throws Exception {
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
        verifyNoInteractions(usuarioService);
    }

    @Test
    void deveFazerLoginDoUsuario () throws Exception {
        when(usuarioService.autenticarUsuario(usuarioDTO)).thenReturn("Bearer tokenGerado");

        mockMvc.perform(post(url+"/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isOk()).andExpect(content().string("Bearer tokenGerado"));
        verify(usuarioService).autenticarUsuario(usuarioDTO);
        verifyNoMoreInteractions(usuarioService);

    }

    @Test
    void naoDeveFazerLoginDoUsuario () throws Exception {
        when(usuarioService.autenticarUsuario(usuarioDTO)).thenThrow(exception);

        mockMvc.perform(post(url+"/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isUnauthorized()).andExpect(content().string("credenciais inválidas"));

        verify(usuarioService).autenticarUsuario(usuarioDTO);
        verifyNoMoreInteractions(usuarioService);
    }

    @Test
    void deveBuscarUsuarioPorEmail () throws Exception {
        when(usuarioService.buscarUsuarioPorEmail(usuarioDTO.getEmail())).thenReturn(usuarioDTO);

        mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("email", usuarioDTO.getEmail())

        ).andExpect(status().isOk());
        verify(usuarioService).buscarUsuarioPorEmail(usuarioDTO.getEmail());
        verifyNoMoreInteractions(usuarioService);
    }

    @Test
    void deveDeletarUsuarioPorEmail () throws Exception {
        doNothing().when(usuarioService).deletaUsuarioPorEmail(usuarioDTO.getEmail());
        mockMvc.perform(delete(url+"/"+usuarioDTO.getEmail())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        verify(usuarioService).deletaUsuarioPorEmail(usuarioDTO.getEmail());
        verifyNoMoreInteractions(usuarioService);
    }

    @Test
    void deveAtualizarDadosDoUsuario () throws Exception {
        when(usuarioService.atualizaDadosUsuario("Bearer tokenGerado", usuarioDTO)).thenReturn(usuarioDTO);
        mockMvc.perform(put(url)
                .header("Authorization", "Bearer tokenGerado")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isOk());

        verify(usuarioService).atualizaDadosUsuario("Bearer tokenGerado", usuarioDTO);
        verifyNoMoreInteractions(usuarioService);
    }

    @Test
    void deveAtualizarEndereco () throws Exception {
        when(usuarioService.atualizaEndereco(enderecoDTO.getId(), enderecoDTO)).thenReturn(enderecoDTO);
        mockMvc.perform(put(url+"/endereco")
                .param("id", String.valueOf(enderecoDTO.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(enderecoJson)
        ).andExpect(status().isOk());
        verify(usuarioService).atualizaEndereco(enderecoDTO.getId(), enderecoDTO);
        verifyNoMoreInteractions(usuarioService);
    }

    @Test
    void deveAtualizarTelefone () throws Exception {
        when(usuarioService.atualizaTelefone(telefoneDTO.getId(), telefoneDTO)).thenReturn(telefoneDTO);

        mockMvc.perform(put(url+"/telefone")
                .param("id", String.valueOf(telefoneDTO.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(telefoneJson)
        ).andExpect(status().isOk());
        verify(usuarioService).atualizaTelefone(telefoneDTO.getId(), telefoneDTO );
        verifyNoMoreInteractions(usuarioService);
    }

    @Test
    void deveCadastrarNovoEnderecoParaUsuarioCadastrado() throws Exception {
        when(usuarioService.cadastraEndereco("Bearer tokenGerado", enderecoDTO)).thenReturn(enderecoDTO);

        mockMvc.perform(post(url+"/endereco")
                .header("Authorization", "Bearer tokenGerado")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(enderecoJson)
        ).andExpect(status().isOk());

        verify(usuarioService).cadastraEndereco("Bearer tokenGerado", enderecoDTO);

        verifyNoMoreInteractions(usuarioService);
    }

    @Test
    void deveCadastrarNovoTelefoneParaUsuarioCadastrado() throws Exception {
        when(usuarioService.cadastraTelefone("Bearer tokenGerado", telefoneDTO)).thenReturn(telefoneDTO);

        mockMvc.perform(post(url+"/telefone")
                .header("Authorization", "Bearer tokenGerado")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(telefoneJson)
        ).andExpect(status().isOk());
        verify(usuarioService).cadastraTelefone("Bearer tokenGerado", telefoneDTO);
        verifyNoMoreInteractions(usuarioService);
    }






    }







