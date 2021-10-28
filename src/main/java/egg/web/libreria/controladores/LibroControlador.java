/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package egg.web.libreria.controladores;

import egg.web.libreria.MiExcepcion.MiExcepcion;
import egg.web.libreria.entidades.Autor;
import egg.web.libreria.entidades.Editorial;
import egg.web.libreria.entidades.Libro;
import egg.web.libreria.servicios.AutorServicio;
import egg.web.libreria.servicios.EditorialServicio;
import egg.web.libreria.servicios.LibroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/libros")
public class LibroControlador {
     
    @Autowired
    private LibroServicio libroServicio;
      @Autowired
    private EditorialServicio editorialServicio;
      @Autowired
      private AutorServicio autorServicio;

    @GetMapping
    public ModelAndView mostrarLibro() {
        ModelAndView mav = new ModelAndView("libros-lista");
        List<Libro> libros = libroServicio.obtenerLibros();
        mav.addObject("libros", libros);
        return mav;
    }
    
 
    
      @GetMapping("/crear")
    public ModelAndView crearLibro() {
        ModelAndView mav = new ModelAndView("libro-formulario");
        mav.addObject("libro", new Libro());       
        mav.addObject("title", "Crear Libro");
        mav.addObject("action", "guardar");
          List<Editorial> editoriales = editorialServicio.obtenerEditoriales();
        mav.addObject("editoriales", editoriales);
          List<Autor> autores = autorServicio.obtenerAutores();
        mav.addObject("autores", autores);
//        aca tambien deberia mandar la lista de editorial y autores con el id 
        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarLibro(@PathVariable String id) {
        ModelAndView mav = new ModelAndView("libro-formulario");
        mav.addObject("libro", libroServicio.buscarPorId(id));       
        mav.addObject("libros", libroServicio.obtenerLibros());
         List<Editorial> editoriales = editorialServicio.obtenerEditoriales();
        mav.addObject("editoriales", editoriales);
          List<Autor> autores = autorServicio.obtenerAutores();
        mav.addObject("autores", autores);
        mav.addObject("title", "Editar Libro");
        mav.addObject("action", "modificar");
        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam Long isbn, @RequestParam  String titulo, @RequestParam Integer anio,
                                @RequestParam Integer ejemplares,  @RequestParam("autor") String idAutor,  @RequestParam("editorial") String idEditorial ) throws MiExcepcion {
        libroServicio.crearLibro( isbn,titulo, anio, ejemplares, idAutor,idEditorial );
        return new RedirectView("/libros");
    }

    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam String id , @RequestParam Long isbn, @RequestParam  String titulo, @RequestParam Integer anio,
                                @RequestParam Integer ejemplares, @RequestParam("autor") String idAutor, @RequestParam("editorial")  String idEditorial , RedirectAttributes redirectAttributes) throws MiExcepcion, Exception  {
       
        try {
             libroServicio.modificarLibro(id ,isbn,titulo, anio, ejemplares, idAutor,idEditorial );
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute( "error" , e.getMessage() );
            
        }
        
        return new RedirectView("/libros");
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable String id) throws Exception {
        libroServicio.bajaLibro(id);
        return new RedirectView("/libros");
    }
    
        @PostMapping("/alta/{id}")
    public RedirectView alta(@PathVariable String id) throws Exception {
        libroServicio.altaLibro(id);
        return new RedirectView("/libros");
    }
}

