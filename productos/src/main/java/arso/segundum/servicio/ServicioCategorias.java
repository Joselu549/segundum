package arso.segundum.servicio;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import arso.segundum.modelo.Categoria;
import arso.segundum.repositorio.CategoriaRepository;

@Service
@Transactional
public class ServicioCategorias implements IServicioCategorias {

    @Autowired
    private CategoriaRepository repositorioCategorias;

    @Override
    public void cargarJerarquiaCategorias(String rutaFichero) {
        try {
            JAXBContext context = JAXBContext.newInstance(Categoria.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            File file = new File(rutaFichero);
            Categoria categoria = (Categoria) unmarshaller.unmarshal(file);

            // Si la categoría ya existe, no cargar
            if (repositorioCategorias.existsById(categoria.getId())) {
                System.out.println("La categoría principal " + categoria.getId() + " ya existe. No se cargará.");
                return;
            }

            configurarRelacionesPadreHijo(categoria, null);
            repositorioCategorias.save(categoria);

        } catch (JAXBException e) {
            throw new RuntimeException("Error al parsear el fichero XML: " + e.getMessage(), e);
        }
    }

    private void configurarRelacionesPadreHijo(Categoria categoria, Categoria padre) {
        categoria.setCategoriaPadre(padre);

        if (categoria.getSubCategorias() != null) {
            for (Categoria subcategoria : categoria.getSubCategorias()) {
                configurarRelacionesPadreHijo(subcategoria, categoria);
            }
        }
    }

    @Override
    public void modificarDescripcionCategoria(String id, String nuevaDescripcion) {
        Categoria categoria = repositorioCategorias.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id: " + id));
        categoria.setDescripcion(nuevaDescripcion);
        repositorioCategorias.save(categoria);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Categoria> obtenerCategoriasRaiz() {
        return repositorioCategorias.findCategoriasRaiz();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Categoria> obtenerSubCategorias(String id) {
        return repositorioCategorias.findSubcategorias(id);
    }
}
