package sjsu.edu.flightreservation.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import sjsu.edu.flightreservation.entities.Role;

public interface RoleRepositoy extends JpaRepository<Role, Long> {

}
