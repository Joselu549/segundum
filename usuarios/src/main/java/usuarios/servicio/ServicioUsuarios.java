package usuarios.servicio;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;
import usuarios.eventos.EventoUsuarioCreado;
import usuarios.modelo.Usuario;
import usuarios.puertos.PublicadorEventos;
import usuarios.repositorio.RepositorioUsuariosAdHoc;

public class ServicioUsuarios implements IServicioUsuarios {

    private RepositorioUsuariosAdHoc repositorioUsuarios = FactoriaRepositorios.getRepositorio(Usuario.class);
    private PublicadorEventos publicador = FactoriaServicios.getServicio(PublicadorEventos.class);

    @Override
    public String registrarUsuario(String email, String nombre, String apellidos, String telefono,
            String direccion, LocalDate fechaNacimiento, String password)
            throws IllegalArgumentException, RepositorioException, IOException {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede ser nulo o vacío");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("El formato del email no es válido");
        }

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        }

        if (apellidos == null || apellidos.trim().isEmpty()) {
            throw new IllegalArgumentException("Los apellidos no pueden ser nulos o vacíos");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede ser nula o vacía");
        }
        if (password.length() < 8) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres");
        }

        if (fechaNacimiento == null) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser nula");
        }
        if (fechaNacimiento.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser en el futuro");
        }
        if (fechaNacimiento.isAfter(LocalDate.now().minusYears(18))) {
            throw new IllegalArgumentException("El usuario debe ser mayor de 18 años");
        }

        if (telefono == null || telefono.trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono no puede ser nulo o vacío");
        }
        if (!telefono.matches("^[+]?[0-9]{9,15}$")) {
            throw new IllegalArgumentException("El formato del teléfono no es válido");
        }

        List<Usuario> existentes = repositorioUsuarios.getUsuariosPorEmail(email);
        if (existentes != null && !existentes.isEmpty()) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        Usuario usuario = new Usuario(email, nombre, apellidos, password, fechaNacimiento, telefono, false);
        String id = repositorioUsuarios.add(usuario);
        EventoUsuarioCreado evento = new EventoUsuarioCreado(email, nombre);
        this.publicador.publicarEvento(evento);
        return id;
    }

    @Override
    public void modificarNombre(String id, String nombre) throws EntidadNoEncontrada, RepositorioException {
        Usuario usuario = repositorioUsuarios.getById(id);
        usuario.setNombre(nombre);
        repositorioUsuarios.update(usuario);
    }

    @Override
    public void modificarApellidos(String id, String apellidos) throws EntidadNoEncontrada, RepositorioException {
        Usuario usuario = repositorioUsuarios.getById(id);
        usuario.setApellidos(apellidos);
        repositorioUsuarios.update(usuario);
    }

    @Override
    public void modificarPassword(String id, String password) throws EntidadNoEncontrada, RepositorioException {
        Usuario usuario = repositorioUsuarios.getById(id);
        usuario.setPassword(password);
        repositorioUsuarios.update(usuario);
    }

    @Override
    public void modificarFechaNacimiento(String id, LocalDate fechaNacimiento)
            throws EntidadNoEncontrada, RepositorioException {
        Usuario usuario = repositorioUsuarios.getById(id);
        usuario.setFechaNacimiento(fechaNacimiento);
        repositorioUsuarios.update(usuario);
    }

    @Override
    public void modificarTelefono(String id, String telefono) throws EntidadNoEncontrada, RepositorioException {
        Usuario usuario = repositorioUsuarios.getById(id);
        usuario.setTelefono(telefono);
        repositorioUsuarios.update(usuario);
    }

    @Override
    public Usuario login(String email, String password) throws RepositorioException {
        List<Usuario> usuarios = repositorioUsuarios.getUsuariosPorEmail(email);
        if (usuarios == null || usuarios.isEmpty()) {
            throw new IllegalArgumentException("Email o contraseña incorrectos");
        }
        Usuario usuario = usuarios.get(0);
        if (!usuario.getPassword().equals(password)) {
            throw new IllegalArgumentException("Email o contraseña incorrectos");
        }
        return usuario;
    }

    @Override
    public List<UsuarioResumen> obtenerTodosLosUsuarios() throws RepositorioException {
        return repositorioUsuarios.getAll().stream()
                .map(this::transformToUsuarioResumen)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public Usuario obtenerUsuarioPorId(String id) throws EntidadNoEncontrada, RepositorioException {
        return repositorioUsuarios.getById(id);
    }

    @Override
    public void incrementarContadorCompras(String id) throws EntidadNoEncontrada, RepositorioException {
        Usuario usuario = repositorioUsuarios.getById(id);
        usuario.setContadorCompras(usuario.getContadorCompras() + 1);
        repositorioUsuarios.update(usuario);
    }

    @Override
    public void incrementarContadorVentas(String id) throws EntidadNoEncontrada, RepositorioException {
        Usuario usuario = repositorioUsuarios.getById(id);
        usuario.setContadorVentas(usuario.getContadorVentas() + 1);
        repositorioUsuarios.update(usuario);
    }

    private UsuarioResumen transformToUsuarioResumen(Usuario usuario) {
        UsuarioResumen resumen = new UsuarioResumen();
        resumen.setId(usuario.getId());
        resumen.setEmail(usuario.getEmail());
        resumen.setNombre(usuario.getNombre());
        resumen.setApellidos(usuario.getApellidos());
        return resumen;
    }
}