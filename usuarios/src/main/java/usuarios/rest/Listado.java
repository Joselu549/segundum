package usuarios.rest;

import java.util.List;

import usuarios.servicio.UsuarioResumen;

public class Listado {
    public static class UsuarioResumenExtendido {
        private String url;
        private UsuarioResumen resumen;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public UsuarioResumen getResumen() {
            return resumen;
        }

        public void setResumen(UsuarioResumen resumen) {
            this.resumen = resumen;
        }
    }

    private List<UsuarioResumenExtendido> usuarios;

    public List<UsuarioResumenExtendido> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UsuarioResumenExtendido> usuarios) {
        this.usuarios = usuarios;
    }
}
