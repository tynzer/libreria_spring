package egg.web.libreria.servicios;

import egg.web.libreria.entidades.Autor;
import egg.web.libreria.repositorios.AutorRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorServicio {
    
    @Autowired
    private AutorRepositorio autorRepositorio;

    @Transactional
    public void crearAutor(String nombre) {
        Autor autor = new Autor();

        autor.setNombre(nombre);
        autor.setAlta(true);        

        autorRepositorio.save(autor);
    }

    @Transactional(readOnly = true)
    public List<Autor> obtenerAutores() {
        return autorRepositorio.findAll();
    }
    
     @Transactional(readOnly = true)
    public Autor buscarPorId(String id) {
        Optional<Autor> autorOptional = autorRepositorio.findById(id);
        return autorOptional.orElse(null);
    }
    
     public void modificarAutor(String id ,String nombre) throws Exception{
        validarNombre(nombre);
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if(respuesta.isPresent()){
            Autor autor = respuesta.get();
            autor.setNombre(nombre);
            autorRepositorio.save(autor);
        }else{
            throw new Exception ("No se encontró el ID");
        }
        
    }
     
     public void bajaAutor(String id) throws Exception{
      Optional<Autor> respuesta = autorRepositorio.findById(id);
        if(respuesta.isPresent()){
            Autor autor = respuesta.get();
            autor.setAlta(false);
            autorRepositorio.save(autor);
        }else{
            throw new Exception ("No se encontró el ID");
        }
         
     }
     
     public void altaAutor(String id) throws Exception{
      Optional<Autor> respuesta = autorRepositorio.findById(id);
        if(respuesta.isPresent()){
            Autor autor = respuesta.get();
            autor.setAlta(true);
            autorRepositorio.save(autor);
        }else{
            throw new Exception ("No se encontró el ID");
        }
         
     }
     
     
     public static boolean validarNombre(String str) {   
     String expression = "^([A-Z][a-z]+([ ]?[a-z]?['-]?[A-Z][a-z]+)*)$"; 
    return str.matches(expression);       
}
   
     
}
