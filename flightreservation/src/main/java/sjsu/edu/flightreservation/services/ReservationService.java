package sjsu.edu.flightreservation.services;

import sjsu.edu.flightreservation.dto.ReservationRequest;
import sjsu.edu.flightreservation.entities.Reservation;

public interface ReservationService {

	public Reservation bookFlight(ReservationRequest request);
}
