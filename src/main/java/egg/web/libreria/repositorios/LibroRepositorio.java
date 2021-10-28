
package egg.web.libreria.repositorios;

import egg.web.libreria.entidades.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String>{
    
}

