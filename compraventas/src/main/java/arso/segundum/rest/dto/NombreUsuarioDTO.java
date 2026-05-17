package arso.segundum.rest.dto;

import javax.validation.constraints.NotBlank;

public class NombreUsuarioDTO {
    @NotBlank(message = "El nombre del usuario no puede estar vacío")
    private String fullName;

    public NombreUsuarioDTO() {
    }

    public NombreUsuarioDTO(String fullName) {
        this.fullName = fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return this.fullName;
    }
}