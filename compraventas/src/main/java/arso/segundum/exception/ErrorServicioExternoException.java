package arso.segundum.exception;

public class ErrorServicioExternoException extends RuntimeException {
    public ErrorServicioExternoException(String mensaje) {
        super(mensaje);
    }

    public ErrorServicioExternoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
