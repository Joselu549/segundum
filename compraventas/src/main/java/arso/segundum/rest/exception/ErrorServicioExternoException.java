package arso.segundum.rest.exception;

public class ErrorServicioExternoException extends RuntimeException {
    public ErrorServicioExternoException(String mensaje) {
        super(mensaje);
    }

    public ErrorServicioExternoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
