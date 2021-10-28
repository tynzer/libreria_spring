 
package egg.web.libreria.controladores;

import egg.web.libreria.entidades.Autor;
import egg.web.libreria.servicios.AutorServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

 @Controller
@RequestMapping("/autores")
public class AutorControlador {
     

    @Autowired
    private AutorServicio autorServicio;

    @GetMapping
    public ModelAndView mostrarAutor() {
        ModelAndView mav = new ModelAndView("autores-lista");
        List<Autor> autores = autorServicio.obtenerAutores();
        mav.addObject("autores", autores);
        return mav;
    }
    
 
    
      @GetMapping("/crear")
    public ModelAndView crearAutor() {
        ModelAndView mav = new ModelAndView("autor-formulario");
        mav.addObject("autor", new Autor());       
        mav.addObject("title", "Crear Autor");
        mav.addObject("action", "guardar");
        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarAutor(@PathVariable String id) {
        ModelAndView mav = new ModelAndView("autor-formulario");
        mav.addObject("autor", autorServicio.buscarPorId(id));       
        mav.addObject("autores", autorServicio.obtenerAutores());
        mav.addObject("title", "Editar Autor");
        mav.addObject("action", "modificar");
        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam String nombre) {
        autorServicio.crearAutor(nombre);
        return new RedirectView("/autores");
    }

    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam String id, @RequestParam String nombre) throws Exception {
        autorServicio.modificarAutor(id, nombre);
        return new RedirectView("/autores");
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable String id) throws Exception {
        autorServicio.bajaAutor(id);
        return new RedirectView("/autores");
    }
    
     @PostMapping("/alta/{id}")
    public RedirectView alta(@PathVariable String id) throws Exception {
        autorServicio.altaAutor(id);
        return new RedirectView("/autores");
    }
}
