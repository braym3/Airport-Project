import React from "react";
import {handleTimeJumpEvents} from "../../utils/timeJumpUtils";
import axios from "axios";
import {Navbar, Nav, Button} from 'react-bootstrap';
import './navbar.css'


const NavigationBar = ({onTimeJump}) => {
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
                            <Nav.Link href="/gates" className="p-2">
                                Gates
                            </Nav.Link>
                            <Nav.Link href="/gate-calendar" className="p-2">
                                Gate Schedule
                            </Nav.Link>
                        </Nav>
                    </Navbar.Collapse>
                    <Button id="time-jump-button" variant="primary" className="ml-auto" onClick={handleTimeJumpButton}>Trigger Events</Button>
                </div>
            </Navbar>
        </>
    )
}

export default NavigationBar;