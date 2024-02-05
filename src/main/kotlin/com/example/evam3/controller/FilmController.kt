import com.example.Pract__Book.service.FilmService
import com.example.evam3.entity.Film
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/film")
class FilmController {

    @Autowired
    lateinit var filmService: FilmService
    @GetMapping
    fun list ():List <Film>{
        return filmService.list()
    }

    @GetMapping("/{id}")
    fun listOne (@PathVariable id: Long) :Optional<Film>{
        return filmService.listOne(id)
    }

    //Peticiones post - Clase controller
    @PostMapping
    fun save (@RequestBody film: Film):ResponseEntity<Film>{
        return ResponseEntity(filmService.save(film), HttpStatus.OK)
    }

    //clase controller - Petición Put
    @PutMapping
    fun update (@RequestBody film: Film):ResponseEntity<Film>{
        return ResponseEntity(filmService.update(film), HttpStatus.OK)
    }

    //clase  controller-Petiicon Patch
    @PatchMapping
    fun updateName (@RequestBody film: Film):ResponseEntity<Film>{
        return ResponseEntity(filmService.update(film), HttpStatus.OK)
    }

    //clase  controller - Petición Delete
    @DeleteMapping("/delete/{id}")
    fun delete (@PathVariable("id") id: Long):Boolean?{
        return filmService.delete(id)
    }
}
