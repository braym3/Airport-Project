import React from "react";
import {handleTimeJumpEvents} from "../../utils/timeJumpUtils";
import axios from "axios";
import {Navbar, Nav, Button, NavDropdown, Badge} from 'react-bootstrap';
import './navbar.css'
import {formatSimulationTime, formatTime} from "../../utils/formatDateTime";


const NavigationBar = ({onTimeJump, currentTime}) => {
    const handleTimeJumpButton = async () => {
        try{
            const response = await axios.get(`${process.env.REACT_APP_API_URL}/api/impactEvents/timeJump`);
            const triggeredEvents = response.data;

            onTimeJump();
            handleTimeJumpEvents(triggeredEvents);
        }catch (error){
            console.error('Error triggering impact events:', error);
        }
    }
    return(
        <>
            <Navbar bg="light" expand="lg">
                <div className="container">
                    <Navbar.Brand href="/">Airport App</Navbar.Brand>
                    <Navbar.Toggle aria-controls="navbarNav"/>
                    <Navbar.Collapse id="navbarNav">
                        <Nav className="ml-auto">
                            <Nav.Link href="/arrivals-departures" className="p-2">
                                Arrivals/Departures
                            </Nav.Link>
                            <Nav.Link href="/ordered-flights" className="p-2">
                                Ordered Flights
                            </Nav.Link>
                            <Nav.Link href="/terminals" className="p-2">
                                Terminals
                            </Nav.Link>
                            <NavDropdown title="Gates" id="basic-nav-dropdown">
                                <NavDropdown.Item href="/gates">Gates Overview</NavDropdown.Item>
                                <NavDropdown.Item href="/gate-calendar">Gate Schedule</NavDropdown.Item>
                            </NavDropdown>
                            <NavDropdown title="Runways" id="basic-nav-dropdown">
                                <NavDropdown.Item href="/runways">Runways Overview</NavDropdown.Item>
                                <NavDropdown.Item href="/runway-calendar">Runway Schedule</NavDropdown.Item>
                            </NavDropdown>
                            <Nav.Link href="/" className="p-2">
                                Schedule History
                            </Nav.Link>
                        </Nav>
                    </Navbar.Collapse>
                    <h5><Badge bg="secondary" className="ml-auto">{formatSimulationTime(currentTime)}</Badge></h5>
                    <Button id="time-jump-button" variant="primary" className="ml-auto" onClick={handleTimeJumpButton}>+1 Hr</Button>
                </div>
            </Navbar>
        </>
    )
}

export default NavigationBar;