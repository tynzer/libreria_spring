
package egg.web.libreria.servicios;

 
import egg.web.libreria.entidades.Editorial;
import egg.web.libreria.repositorios.EditorialRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialServicio {
    
   
    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearEditorial(String nombre) {
        Editorial editorial = new Editorial();

        editorial.setNombre(nombre);
        editorial.setAlta(true);        

        editorialRepositorio.save(editorial);
    }

    @Transactional(readOnly = true)
    public List<Editorial> obtenerEditoriales() {        
        return editorialRepositorio.findAll();
    }
    
      @Transactional(readOnly = true)
    public Editorial buscarPorId(String id) {
        Optional<Editorial> editorialOptional = editorialRepositorio.findById(id);
        return editorialOptional.orElse(null);
    }
    
    
     public void modificarEditorial(String id ,String nombre) throws Exception{
        validarNombre(nombre);
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if(respuesta.isPresent()){
            Editorial editorial = respuesta.get();
            editorial.setNombre(nombre);
            editorialRepositorio.save(editorial);
        }else{
            throw new Exception ("No se encontró el ID");
        }
        
    }
     
     public void bajaEditorial(String id) throws Exception{
      Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if(respuesta.isPresent()){
            Editorial editorial = respuesta.get();
            editorial.setAlta(false);
            editorialRepositorio.save(editorial);
        }else{
            throw new Exception ("No se encontró el ID");
        }         
     }
     
      public void altaEditorial(String id) throws Exception{
      Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if(respuesta.isPresent()){
            Editorial editorial = respuesta.get();
            editorial.setAlta(true);
            editorialRepositorio.save(editorial);
        }else{
            throw new Exception ("No se encontró el ID");
        }
         
     }

     public static boolean validarNombre(String str) {   
     String expression = "^([A-Z][a-z]+([ ]?[a-z]?['-]?[A-Z][a-z]+)*)$"; 
    return str.matches(expression);       
}

     
}
