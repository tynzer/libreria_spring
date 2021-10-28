package egg.web.libreria.controladores;

 
import egg.web.libreria.entidades.Editorial;
import egg.web.libreria.servicios.EditorialServicio;
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
@RequestMapping("/editoriales")
public class EditorialControlador {
     

    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping
    public ModelAndView mostrarEditorial() {
        ModelAndView mav = new ModelAndView("editoriales-lista");
        List<Editorial> editoriales = editorialServicio.obtenerEditoriales();
        mav.addObject("editoriales", editoriales);
        return mav;
    }
    
 
    
      @GetMapping("/crear")
    public ModelAndView crearEditorial() {
        ModelAndView mav = new ModelAndView("editorial-formulario");
        mav.addObject("editorial", new Editorial());       
        mav.addObject("title", "Crear Editorial");
        mav.addObject("action", "guardar");
        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarEditorial(@PathVariable String id) {
        ModelAndView mav = new ModelAndView("editorial-formulario");
        mav.addObject("editorial", editorialServicio.buscarPorId(id));       
        mav.addObject("editoriales", editorialServicio.obtenerEditoriales());
        mav.addObject("title", "Editar Editorial");
        mav.addObject("action", "modificar");
        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam String nombre) {
        editorialServicio.crearEditorial(nombre);
        return new RedirectView("/editoriales");
    }

    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam String id, @RequestParam String nombre) throws Exception {
        editorialServicio.modificarEditorial(id, nombre);
        return new RedirectView("/editoriales");
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable String id) throws Exception {
        editorialServicio.bajaEditorial(id);
        return new RedirectView("/editoriales");
    }
    
     @PostMapping("/alta/{id}")
    public RedirectView alta(@PathVariable String id) throws Exception {
        editorialServicio.altaEditorial(id);
        return new RedirectView("/editoriales");
    }
}
