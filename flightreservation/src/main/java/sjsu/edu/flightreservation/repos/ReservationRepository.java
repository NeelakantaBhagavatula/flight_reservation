package sjsu.edu.flightreservation.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import sjsu.edu.flightreservation.entities.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
