package usuarios.rest.dto;

public class NombreUsuarioDTO {
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