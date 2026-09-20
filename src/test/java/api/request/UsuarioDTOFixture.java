package api.request;

import com.daniels.usuarios.business.dto.EnderecoDTO;
import com.daniels.usuarios.business.dto.TelefoneDTO;
import com.daniels.usuarios.business.dto.UsuarioDTO;

import java.util.List;

public class UsuarioDTOFixture {

    public static UsuarioDTO build (String nome, String email, String senha, List<EnderecoDTO> enderecos,
     List<TelefoneDTO> telefones){
        return new UsuarioDTO(nome, email, senha, enderecos, telefones);
    }
}
