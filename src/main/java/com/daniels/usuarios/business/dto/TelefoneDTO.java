package com.daniels.usuarios.business.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class TelefoneDTO {

    private Long id;
    private String numero;
    private String ddd;
}
