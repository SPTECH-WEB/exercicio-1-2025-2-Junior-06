package school.sptech.prova_ac1;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
    Optional<Usuario> findById(Integer id);
    void deleteById(Integer id);

    Optional<Object> findByEmail(String email);
    Optional<Object> findByCpf(String cpf);
    List<Usuario> findByDataNascimento(LocalDate dataNascimento);
}
