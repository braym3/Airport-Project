package com.example.airportproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No flight found with that id")
public class FlightNotFoundException extends RuntimeException{
}
