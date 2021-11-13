package Controller;

import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import model.Etiqueta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import repositories.EtiquetaRepository;

@RestController
@RequestMapping("/etiqueta")
public class EtiquetaController {

    @Autowired
    private EtiquetaRepository etiquetaRepository;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_ETIQUETA') and #oauth2.hasScope('read')")
    public List<Etiqueta> listar(){
        return etiquetaRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_ETIQUETA') and #oauth2.hasScope('write')")
    public Etiqueta criar(@Valid @RequestBody Etiqueta etiqueta, HttpServletResponse response) {
        return etiquetaRepository.save(etiqueta);
    }

    @GetMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_ETIQUETA') and #oauth2.hasScope('read')")
    public ResponseEntity<Etiqueta> buscarPeloCodigo(@PathVariable Long codigo){
        Optional<Etiqueta> etiqueta= etiquetaRepository.findById(codigo);
        if(etiqueta.isPresent()) {
            return ResponseEntity.ok(etiqueta.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ROLE_REMOVER_ETIQUETA') and #oauth2.hasScope('write')")
    public void remover(@PathVariable Long codigo) {
        etiquetaRepository.deleteById(codigo);
    }

}
