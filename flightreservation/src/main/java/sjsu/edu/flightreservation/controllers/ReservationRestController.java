package sjsu.edu.flightreservation.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sjsu.edu.flightreservation.dto.ReservationUpdateRequest;
import sjsu.edu.flightreservation.entities.Reservation;
import sjsu.edu.flightreservation.repos.ReservationRepository;

@RestController
@CrossOrigin(origins = "*")
public class ReservationRestController {

	@Autowired
	ReservationRepository reservationRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReservationRestController.class);
	
	@RequestMapping(value = "/reservations/{id}")
	public Reservation findReservation(@PathVariable("id") Long id) {
		LOGGER.info("Inside findReservation() {}", id);
		return reservationRepository.findById(id).get();
	}
	
	@RequestMapping(value = "/reservations")
	public Reservation updateReservation(@RequestBody ReservationUpdateRequest request) {
		LOGGER.info("Executing updateReservation() << {}", request.getId());
		
		Reservation reservation = reservationRepository.findById(request.getId()).get();
		reservation.setCheckedIn(request.getCheckedIn());
		reservation.setNumberOfBags(request.getNumberOfBags());
		
		LOGGER.info("Updating Reservation");
		Reservation savedReservation = reservationRepository.save(reservation);
		
		LOGGER.info("Exiting updateReservation() >> {}", reservation.getId());
		return savedReservation;
	}
}
