# Flight Scheduling Simulator
## Overview
Welcome to the Flight Scheduling Simulator, a dynamic application for simulating and visualizing flight schedules at airports. This project provides an abstracted and simplified view of flight scheduling, offering a basic simulation of airport activities. 

It consists of a Java Spring Boot backend that interfaces with the FlightLabs 'flight' API to retrieve real-world flight data and extensively models and manipulates this data to create a dynamic flight schedule. The backend is designed to respond dynamically to impact events, such as closures, weather conditions, and delays, reshuffling the schedule to simulate real-world challenges. 

The project also includes a React frontend, offering an interactive web application for users to explore and analyze the dynamically generated flight schedules.

## Features
**Dynamic Flight Scheduling:** The backend processes real-world flight data from the FlightLabs 'flight' API, mapping it into a detailed flight schedule based on the airport's terminal, gate, and runway structure. The application is currently configured for Manchester Airport but can easily be adapted to other airports via the properties file.

**Interactive Timetable:** The frontend provides an interactive timetable using the PlanBy open-source schedule component, offering a comprehensive daily schedule view for both departure and arrival flights.

**Gate-Specific Views:** Users can explore individual gates and their scheduled flights, allowing for a detailed understanding of gate activities.

**Departure and Arrival Tables:** Easily accessible tables provide clear views of departure and arrival schedules, offering a quick reference for specific flights.

**Time Simulation:** The application allows users to fast-forward time in hourly increments, triggering random 'impact events' (e.g., gate/terminal closures, weather conditions, flight delays, etc.). These events simulate real-world disruptions to the flight schedule. In response, the backend dynamically reshuffles the schedule, attempting to reassign affected flights to the closest available time slot to their initial schedule, aiming for a more realistic simulation of adaptive scheduling.

**Impact Event Visualizations:** The frontend displays 'impact events' and provides statistics and visualizations about the types and frequency of these events, as well as information on flight cancellations and delays.

## Technical Details
### Backend (Java Spring Boot)
**Flight Data Retrieval:** Utilizes the FlightLabs 'flight' API to retrieve real-world flight data.

**Dynamic Scheduling:** Processes the retrieved data to generate a dynamic flight schedule, considering airport infrastructure like terminals, gates, and runways.

**Event Simulation:** Implements a time simulation feature that introduces random 'impact events' affecting the schedule, triggering dynamic reshuffling.

### Frontend (React)
**Interactive Views:** Utilizes React to create an interactive web application with multiple dynamic views for exploring flight schedules.

**PlanBy Integration:** Incorporates the PlanBy open-source schedule component for an interactive and comprehensive timetable view.

**Impact Event Visualization:** Displays impact events on the schedule and provides visualizations and statistics regarding event types, flight cancellations, and delays.


## Acknowledgments
[FlightLabs](https://www.goflightlabs.com/) for providing the 'flight' API.

[PlanBy](https://github.com/karolkozer/planby) for the open-source schedule component.
