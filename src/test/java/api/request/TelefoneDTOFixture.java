package api.request;

import com.daniels.usuarios.business.dto.TelefoneDTO;

public class TelefoneDTOFixture {

    public static TelefoneDTO build (Long id, String numero, String ddd){
        return new TelefoneDTO(id, numero, ddd);
    }
}
