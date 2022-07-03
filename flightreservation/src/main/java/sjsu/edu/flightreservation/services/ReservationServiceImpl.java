package sjsu.edu.flightreservation.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sjsu.edu.flightreservation.dto.ReservationRequest;
import sjsu.edu.flightreservation.entities.Flight;
import sjsu.edu.flightreservation.entities.Passenger;
import sjsu.edu.flightreservation.entities.Reservation;
import sjsu.edu.flightreservation.repos.FlightRepository;
import sjsu.edu.flightreservation.repos.PassengerRepository;
import sjsu.edu.flightreservation.repos.ReservationRepository;
import sjsu.edu.flightreservation.util.EmailUtil;
import sjsu.edu.flightreservation.util.PDFGenerator;

@Service
public class ReservationServiceImpl implements ReservationService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReservationServiceImpl.class);
	
	@Value("${flightreservation.itinerary.dir}")
	private String ITINERARY_DIR;

	@Autowired
	FlightRepository flightRepository;
	
	@Autowired
	PassengerRepository passengerRepository;
	
	@Autowired
	ReservationRepository reservationRepository;
	
	@Autowired
	PDFGenerator pdfGenerator;
	
	@Autowired
	EmailUtil emailUtil;
	
	@Override
	@Transactional
	public Reservation bookFlight(ReservationRequest request) {
		LOGGER.info("Executing bookFlight() <<");
		//Make payment
		
		Long flightId = request.getFlightId();
		
		LOGGER.info("Retrieving flight with id : {}", flightId);
		Flight flight = flightRepository.findById(flightId).get();
		
		Passenger passenger = new Passenger();
		passenger.setFirstName(request.getPassengerFirstName());
		passenger.setLastName(request.getPassengerLastName());
		passenger.setEmail(request.getPassengerEmail());
		passenger.setPhone(request.getPassengerPhone());
		
		LOGGER.info("Saving Passenger {}", passenger);
		Passenger savedPassenger = passengerRepository.save(passenger);
		
		Reservation reservation = new Reservation();
		reservation.setFlight(flight);
		reservation.setPassenger(savedPassenger);
		reservation.setCheckedIn(false);
		
		LOGGER.info("Saving Reservation {}", reservation);
		Reservation savedReservation = reservationRepository.save(reservation);
		
		String itineraryPath = ITINERARY_DIR + savedReservation.getId() + ".pdf";
		
		LOGGER.info("Generating Itinerary");
		pdfGenerator.generateItinerary(savedReservation, itineraryPath);
		
		LOGGER.info("Sending Email");
		//emailUtil.sendItinerary(passenger.getEmail(), itineraryPath);
		
		return savedReservation;
	}
}
