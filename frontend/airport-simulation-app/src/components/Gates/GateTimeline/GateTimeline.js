import React, {useState, useEffect} from "react";
import axios from "axios";
import ImpactEventTimeSlot from "../GatesOverview/ImpactEventTimeSlot";
import FlightTimeSlot from "../GatesOverview/FlightTimeSlot";
import './runways.css';

const GateTimeline = ({timeJumpTriggered}) => {
    const [gates, setGates] = useState([]);
    const [expandedGates, setExpandedGates] = useState({});

    useEffect(() => {
        fetchGates();
    }, [timeJumpTriggered])

    const fetchGates = () => {
        axios.get(
            `${process.env.REACT_APP_API_URL}/api/gates/`
        ).then(response => {
            const gatesData = response.data;
            setGates(gatesData);
        }).catch(error => {
            console.error('Error fetching gate data:', error);
        });
    };

    const calculateTimeSlotWidth = (startTime, endTime) => {
        const startDate = new Date(startTime);
        const endDate = new Date(endTime);

        const startHour = startDate.getHours();
        const startMinutes = startDate.getMinutes();
        const endHour = endDate.getHours();
        const endMinutes = endDate.getMinutes();

        const startTotalMinutes = startHour * 60 + startMinutes;
        const endTotalMinutes = endHour * 60 + endMinutes;

        const totalMinutes = endTotalMinutes - startTotalMinutes;
        // for a 24-hour day
        const widthPercentage = (totalMinutes / 1440) * 100

        return `${widthPercentage}%`;
    };

    const toggleGateExpansion = (gateId) => {
        setExpandedGates(prevExpandedGates => ({
            ...prevExpandedGates, [gateId]: !prevExpandedGates[gateId]
        }));
    };

    const displayGates = gates.map(gate => (
            <div key={gate.id} className='gate-card'>
                <h5 className='gate-header' onClick={() => toggleGateExpansion(gate.id)}>{`T${gate.terminal.number} gate ${gate.number}`}</h5>
                {expandedGates[gate.id] && (
                    <>
                        <h6 className='schedule-header'>Schedule:</h6>

                        <div className={"time-slots-grid"}>
                            {gate.schedule?.map(timeSlot => {
                                return(
                                    <div key={timeSlot.id} className={"time-slot-card"} style={{width: calculateTimeSlotWidth(timeSlot.startTime, timeSlot.endTime)}}>
                                        {'flight' in timeSlot ? (
                                            <FlightTimeSlot flightCode={timeSlot.flight.flightIata} startTime={timeSlot.startTime} endTime={timeSlot.endTime}/>
                                        ) : (
                                            <ImpactEventTimeSlot impactEventType={timeSlot.impactEvent.type} startTime={timeSlot.startTime} endTime={timeSlot.endTime}/>
                                        )}
                                    </div>
                                )
                            })}
                        </div>
                    </>
                )}

            </div>
        )
    )



    return(
        <>
            <header style={{paddingTop: '50px', paddingBottom: '20px'}} >
                <h4>Gates</h4>
            </header>
            <div id='gates-container'>
                {displayGates}
            </div>
        </>
    )
}

export default GateTimeline;