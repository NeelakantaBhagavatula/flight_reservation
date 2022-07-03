package sjsu.edu.flightreservation.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import sjsu.edu.flightreservation.dto.ReservationRequest;
import sjsu.edu.flightreservation.entities.Flight;
import sjsu.edu.flightreservation.entities.Reservation;
import sjsu.edu.flightreservation.repos.FlightRepository;
import sjsu.edu.flightreservation.services.ReservationService;

@Controller
public class ReservationController {

	@Autowired
	private FlightRepository flightRepository;

	@Autowired
	ReservationService reservationService;

	private static final Logger LOGGER = LoggerFactory.getLogger(ReservationController.class);

	@RequestMapping("/showCompleteReservation")
	public String showCompleteReservation(@RequestParam("flightId") Long flightId, ModelMap modelMap) {
		LOGGER.info("Executing showCompleteReservation() << {}", flightId);

		Flight flight = flightRepository.findById(flightId).get();
		modelMap.addAttribute("flight", flight);

		LOGGER.info("Exiting showCompleteReservation() >> {}", flightId);
		return "completeReservation";
	}

	@RequestMapping(value = "/completeReservation", method = RequestMethod.POST)
	public String completeReservation(ReservationRequest request, ModelMap modelMap) {
		LOGGER.info("Executing completeReservation() << {}", request.getFlightId());

		Reservation reservation = reservationService.bookFlight(request);
		modelMap.addAttribute("msg", "Reservation created successfully with id : " + reservation.getId());
		
		LOGGER.info("Exiting completeReservation() >> {}", reservation.getFlight().getId());
		return "reservationConfirmation";
	}
}
