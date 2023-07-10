import React, {useState, useEffect} from "react";
import axios from "axios";
import ImpactEventTimeSlot from "./ImpactEventTimeSlot";
import FlightTimeSlot from "./FlightTimeSlot";
import './gates.css';
const Gates = ({timeJumpTriggered}) => {
    const [gates, setGates] = useState([]);

    useEffect(() => {
        fetchGates();
    }, [timeJumpTriggered])

    const fetchGates = () => {
        axios.get(
            `http://localhost:8080/api/gates/`
        ).then(response => {
            const gatesData = response.data;
            setGates(gatesData);
        }).catch(error => {
            console.error('Error fetching gate data:', error);
        });
    };


    return(
        <>
            <header style={{paddingTop: '50px', paddingBottom: '20px'}} >
                <h4>Gates</h4>
            </header>
            <div id='gates-container'>
                {gates.map(gate => (
                    <div key={gate.id} className='gate-card'>
                        <h5 className='gate-header'>{`T${gate.terminal.number} gate ${gate.number}`}</h5>
                        <h6 className='schedule-header'>Schedule:</h6>

                        <div className='time-slots-grid'>
                            {gate.schedule?.map(timeSlot => (
                                <div key={timeSlot.id} className='time-slot-card'>
                                    {'flight' in timeSlot ? (
                                        <FlightTimeSlot flightId={timeSlot.flight.id} startTime={timeSlot.startTime} endTime={timeSlot.endTime}/>
                                    ) : (
                                        <ImpactEventTimeSlot impactEventId={timeSlot.impactEvent.id} startTime={timeSlot.startTime} endTime={timeSlot.endTime}/>
                                    )}
                                </div>
                            ))}
                        </div>
                    </div>
                    )
                )}
            </div>
        </>
    )
}

export default Gates;