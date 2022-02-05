package moviereview.moviereview.repository;

import moviereview.moviereview.models.ERole;
import moviereview.moviereview.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByRole(ERole role);
}
