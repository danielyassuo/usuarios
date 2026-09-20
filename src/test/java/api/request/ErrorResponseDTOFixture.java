package api.request;

import com.daniels.usuarios.business.dto.ErrorResponseDTO;

import java.time.LocalDateTime;

public class ErrorResponseDTOFixture {

    public static ErrorResponseDTO build (LocalDateTime timeStamp, int status, String erro, String message, String path){
        return new ErrorResponseDTO(timeStamp, status, erro, message, path);
    }
}
