package egg.web.libreria.servicios;

import egg.web.libreria.MiExcepcion.MiExcepcion;
import egg.web.libreria.entidades.Autor;
import egg.web.libreria.entidades.Editorial;
import egg.web.libreria.entidades.Libro;
import egg.web.libreria.repositorios.AutorRepositorio;
import egg.web.libreria.repositorios.EditorialRepositorio;
import egg.web.libreria.repositorios.LibroRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private AutorRepositorio autorRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearLibro(Long isbn, String titulo, Integer anio, Integer ejemplares, String idAutor, String idEditorial) throws MiExcepcion {
       
        Libro libro = new Libro();
        //se valida que exista el autor y la editorial y setea en libro
           setAutorYEditorial(idAutor, idEditorial, libro);
       
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setAlta(true);  
libroRepositorio.save(libro);

    }

//    metdo libro , id autor y editorial 
    @Transactional(readOnly = true)
    public List<Libro> obtenerLibros() {
        return libroRepositorio.findAll();
    }

    @Transactional(readOnly = true)
    public Libro buscarPorId(String id) {
        Optional<Libro> libroOptional = libroRepositorio.findById(id);
        return libroOptional.orElse(null);
    }

    public void modificarLibro(String id, Long isbn, String titulo, Integer anio, Integer ejemplares, String idAutor, String idEditorial) throws Exception {
        try {

            if (!validarNombre(titulo)) {
                throw new MiExcepcion("No es valido el nombre");
            }
            String isbnString = String.valueOf(isbn);
//            if (!validarISBN(isbnString)) {
//                throw new MiExcepcion("No es valido el ISBN de la editorial");
//            }

            Optional<Libro> respuesta = libroRepositorio.findById(id);
            if (respuesta.isPresent()) {

                Libro libro = respuesta.get();
                libro.setTitulo(titulo);
                libro.setIsbn(isbn);
                libro.setAnio(anio);
                //falta validar anio y cant ejemplares
                libro.setEjemplares(ejemplares);
                 
                setAutorYEditorial(idAutor, idEditorial, libro);

                libroRepositorio.save(libro);
            } else {
                throw new MiExcepcion("No se encontró el ID del libro");
            }

        } catch (MiExcepcion e) {
            throw new MiExcepcion("No se pudo guardar libro");

        }
    }

    public void bajaLibro(String id) throws MiExcepcion {
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setAlta(false);
            libroRepositorio.save(libro);
        } else {
            throw new MiExcepcion("No se encontró el ID del libro");

        }

    }

    public void altaLibro(String id) throws MiExcepcion {
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setAlta(true);
            libroRepositorio.save(libro);
        } else {
            throw new MiExcepcion("No se encontró el ID del libro");

        }

    }

    public static boolean validarNombre(String str) throws MiExcepcion {
        try {
            String expression = "([A-ZÀ-ÿ][-,a-z. ']+[ ]*)+";
            return str.matches(expression);
        } catch (Exception e) {
            throw new MiExcepcion("No se pudo validar nombre");
        }

    }

    public static boolean validarISBN(String isbn) throws MiExcepcion {
        try {
            String expression = "((?:[\\dX]{13})|(?:[\\d\\-X]{17})|(?:[\\dX]{10})|(?:[\\d\\-X]{13}))\n";
            return isbn.matches(expression);
        } catch (Exception e) {
            throw new MiExcepcion("No se puedo validar editorial");
        }
    }

    public void setAutorYEditorial(String idAutor, String idEditorial, Libro libro) throws MiExcepcion {
        Autor autor = null;
        Editorial editorial = null;
        Optional<Autor> autorOptional = autorRepositorio.findById(idAutor);

        if (autorOptional.isPresent()) {
            autor = autorOptional.get();
        } else {
            throw new MiExcepcion("No se encontro el autor solicitado");
        }

        Optional<Editorial> editorialOptional = editorialRepositorio.findById(idEditorial);
        if (editorialOptional.isPresent()) {
            editorial = editorialOptional.get();
        } else {
            throw new MiExcepcion("No se encontro la editorial solicitada");
        }
        libro.setAutor(autor);
        libro.setEditorial(editorial);
    }

}
