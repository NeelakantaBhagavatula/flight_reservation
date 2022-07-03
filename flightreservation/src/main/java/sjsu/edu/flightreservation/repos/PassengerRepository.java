package sjsu.edu.flightreservation.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import sjsu.edu.flightreservation.entities.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {

}
