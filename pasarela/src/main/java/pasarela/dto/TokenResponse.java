package pasarela.dto;

public class TokenResponse {

    private String token;
    private String userId;
    private String nombreCompleto;
    private String rol;

    public TokenResponse(String token, String userId, String nombreCompleto, String rol) {
        this.token = token;
        this.userId = userId;
        this.nombreCompleto = nombreCompleto;
        this.rol = rol;
    }

    public String getToken() { return token; }
    public String getUserId() { return userId; }
    public String getNombreCompleto() { return nombreCompleto; }
    public String getRol() { return rol; }
}
