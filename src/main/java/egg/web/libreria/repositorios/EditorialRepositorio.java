 
package egg.web.libreria.repositorios;

import java.util.List;
import egg.web.libreria.entidades.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, String> {

    
   // @Query ("SELECT e FROM Editorial e WHERE e.alta = TRUE")
  //  List<Editorial> buscarEditoriales();    
    
}

 

 