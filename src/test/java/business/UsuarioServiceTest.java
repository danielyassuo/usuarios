package business;


import com.daniels.usuarios.business.UsuarioService;
import com.daniels.usuarios.business.converter.UsuarioConverter;
import com.daniels.usuarios.business.dto.EnderecoDTO;
import com.daniels.usuarios.business.dto.TelefoneDTO;
import com.daniels.usuarios.business.dto.UsuarioDTO;
import com.daniels.usuarios.infrastructure.Repository.EnderecoRepository;
import com.daniels.usuarios.infrastructure.Repository.TelefoneRepository;
import com.daniels.usuarios.infrastructure.Repository.UsuarioRepository;
import com.daniels.usuarios.infrastructure.entity.Endereco;
import com.daniels.usuarios.infrastructure.entity.Telefone;
import com.daniels.usuarios.infrastructure.entity.Usuario;
import com.daniels.usuarios.infrastructure.exceptions.ConflictException;
import com.daniels.usuarios.infrastructure.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {


    @InjectMocks
    UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Authentication authentication;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private ConflictException conflictException;

    @Spy
    private UsuarioConverter usuarioConverter = new UsuarioConverter();

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

    @BeforeEach
    public void setup(){
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
    }

    @Test
    void deveSalvarUsuarioComSucesso(){
        when(usuarioRepository.existsByEmail(usuarioDTO.getEmail())).thenReturn(false);
        when(passwordEncoder.encode("minhaSenha")).thenReturn("minhaSenha");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        UsuarioDTO dtoEsperado = usuarioConverter.paraUsuarioDTO(usuario);

        UsuarioDTO resultado = usuarioService.salvaUsuario(usuarioDTO);

        assertEquals(dtoEsperado, resultado);
    }


    @Test
    void naoDeveSalvarUsuarioCasoEmailTrue () {
        when(usuarioRepository.existsByEmail(usuarioDTO.getEmail())).thenReturn(true);

        assertThrows(conflictException.getClass(), () -> usuarioService.salvaUsuario(usuarioDTO));

    }

    @Test
    void deveAtualizarDadosUsuario () {
        String token = "Bearer tokenGerado";

        when(jwtUtil.extraitEmailToken(token.substring(7))).thenReturn(usuarioDTO.getEmail());
        when(passwordEncoder.encode("minhaSenha")).thenReturn("minhaSenha");
        when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(Optional.of(usuario));
        usuarioDTO.setNome("danizin");
        Usuario usuarioAtualizado = usuarioConverter.updateUsuario(usuarioDTO, usuario);
        when(usuarioConverter.updateUsuario(usuarioDTO, usuario)).thenReturn(usuarioAtualizado);
        when(usuarioRepository.save(usuarioAtualizado)).thenReturn(usuarioAtualizado);

        UsuarioDTO resultado = usuarioService.atualizaDadosUsuario(token, usuarioDTO);

        assertEquals(usuarioDTO, resultado);


    }


    @Test
    void deveAutenticarUsuario () {
        when(authentication.getName()).thenReturn(usuarioDTO.getEmail());
        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(), usuarioDTO.getSenha()))).thenReturn(authentication);
        when(jwtUtil.generateToken(authentication.getName())).thenReturn("tokenGerado");

        String resultado = usuarioService.autenticarUsuario(usuarioDTO);

        assertEquals("Bearer tokenGerado", resultado);
    }

    @Test
    void deveAcharUsuarioPorEmail () {
        when(usuarioRepository.findByEmail(usuarioDTO.getEmail())).thenReturn(Optional.of(usuario));
        when(usuarioConverter.paraUsuarioDTO(usuario)).thenReturn(usuarioDTO);
        UsuarioDTO resultado = usuarioService.buscarUsuarioPorEmail(usuario.getEmail());

        assertEquals(usuarioDTO, resultado);
    }

    @Test
    void deveDeletarUsuarioPorEmail () {
        usuarioService.deletaUsuarioPorEmail(usuario.getEmail());
        verify(usuarioRepository).deleteByEmail(usuario.getEmail());

    }

    @Test
    void deveAtualizarEndereco () {
        when(enderecoRepository.findById(enderecoDTO.getId())).thenReturn(Optional.of(endereco));
        when(usuarioConverter.updateEndereco(enderecoDTO, endereco)).thenReturn(endereco);
        when(enderecoRepository.save(endereco)).thenReturn(endereco);
        when(usuarioConverter.paraEnderecoDTO(endereco)).thenReturn(enderecoDTO);
        EnderecoDTO resultado = usuarioService.atualizaEndereco(enderecoDTO.getId(), enderecoDTO);
        assertEquals(resultado, enderecoDTO);

    }

    @Test
    void deveAtualizarTelefone () {
        when(telefoneRepository.findById(telefoneDTO.getId())).thenReturn(Optional.of(telefone));
        when(usuarioConverter.updateTelefone(telefoneDTO, telefone)).thenReturn(telefone);
        when(telefoneRepository.save(telefone)).thenReturn(telefone);
        when(usuarioConverter.paraTelefoneDTO(telefone)).thenReturn(telefoneDTO);
        TelefoneDTO resultado = usuarioService.atualizaTelefone(telefoneDTO.getId(), telefoneDTO);

        assertEquals(resultado, telefoneDTO);
    }

    @Test
    void deveCadastrarEnderecoParaUsuarioCadastrado () {
        String token = "Bearer tokenGerado";

        when(jwtUtil.extraitEmailToken(token.substring(7))).thenReturn(usuarioDTO.getEmail());
        when(usuarioRepository.findByEmail(usuarioDTO.getEmail())).thenReturn(Optional.of(usuario));
        when(usuarioConverter.paraEnderecoEntity(enderecoDTO, usuario.getId())).thenReturn(endereco);
        when(enderecoRepository.save(endereco)).thenReturn(endereco);
        when(usuarioConverter.paraEnderecoDTO(endereco)).thenReturn(enderecoDTO);

        EnderecoDTO resultado = usuarioService.cadastraEndereco(token, enderecoDTO);

        assertEquals(resultado, enderecoDTO);

    }

    @Test
    void deveCadastrarTelefoneParaUsuarioCadastrado () {
        String token = "Bearer tokenGerado";

        when(jwtUtil.extraitEmailToken(token.substring(7))).thenReturn(usuarioDTO.getEmail());
        when(usuarioRepository.findByEmail(usuarioDTO.getEmail())).thenReturn(Optional.of(usuario));
        when(usuarioConverter.paraTelefoneEntity(telefoneDTO, usuario.getId())).thenReturn(telefone);
        when(telefoneRepository.save(telefone)).thenReturn(telefone);
        when(usuarioConverter.paraTelefoneDTO(telefone)).thenReturn(telefoneDTO);
        TelefoneDTO resultado = usuarioService.cadastraTelefone(token, telefoneDTO);

        assertEquals(resultado, telefoneDTO);
    }
































}
