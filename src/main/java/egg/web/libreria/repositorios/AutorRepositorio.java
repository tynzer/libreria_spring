 package egg.web.libreria.repositorios;

import egg.web.libreria.entidades.Autor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository

public interface AutorRepositorio extends JpaRepository<Autor, String> {
    
 //   @Query ("SELECT a FROM Autor a WHERE a.alta = TRUE")
 //   List<Autor> buscarAutores();
}
