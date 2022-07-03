package sjsu.edu.flightreservation.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import sjsu.edu.flightreservation.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);
}
