package school.sptech.prova_ac1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @GetMapping
    public ResponseEntity<List<Usuario>> buscarTodos() {
        if (repository.findAll().isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<Usuario> criar(@RequestBody Usuario usuario) {
        if (repository.findByEmail(usuario.getEmail()).isPresent() ||
                repository.findByCpf(usuario.getCpf()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(usuario));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Integer id) {
        if (repository.findById(id).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(repository.findById(id).get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        Usuario usuario = buscarPorId(id).getBody();

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> buscarPorDataNascimento(LocalDate nascimento) {
        if (nascimento == null) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        }

        List<Usuario> usuarios = repository.findByDataNascimento(nascimento);

        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }

        return ResponseEntity.ok(usuarios); // 200 OK
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar( @PathVariable Integer id,@RequestBody Usuario usuarioAtualizado) {
        Usuario usuario = repository.getById(id);

        if (repository.findByEmail(usuario.getEmail()).isPresent() ||
                repository.findByCpf(usuario.getCpf()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT).build();
        }

        usuario.setNome(usuarioAtualizado.getNome());
        usuario.setEmail(usuarioAtualizado.getEmail());
        usuario.setCpf(usuarioAtualizado.getCpf());
        usuario.setDataNascimento(usuarioAtualizado.getDataNascimento());

        Usuario salvo = repository.save(usuario);
        return ResponseEntity.ok(salvo);
    }
}
