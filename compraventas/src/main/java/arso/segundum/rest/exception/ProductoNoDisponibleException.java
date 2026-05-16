package arso.segundum.rest.exception;

public class ProductoNoDisponibleException extends RuntimeException {
    public ProductoNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}
