import React, {useState, useEffect} from "react";
import axios from "axios";
import ImpactEventTimeSlot from "./ImpactEventTimeSlot";
import FlightTimeSlot from "./FlightTimeSlot";
import './runways.css';

const Runways = ({timeJumpTriggered}) => {
    const [runways, setRunways] = useState([]);
    const [expandedRunways, setExpandedRunways] = useState({});

    useEffect(() => {
        fetchGates();
    }, [timeJumpTriggered])

    const fetchGates = () => {
        axios.get(
            `${process.env.REACT_APP_API_URL}/api/runways/`
        ).then(response => {
            const runwaysData = response.data;
            setRunways(runwaysData);
        }).catch(error => {
            console.error('Error fetching runway overview data:', error);
        });
    };

    const toggleRunwayExpansion = (runwayId) => {
        setExpandedRunways(prevExpandedRunways => ({
            ...prevExpandedRunways, [runwayId]: !prevExpandedRunways[runwayId]
        }));
    };

    const displayRunways = runways.map(runway => (
            <div key={runway.id} className='runway-card'>
                <h5 className='runway-header' onClick={() => toggleRunwayExpansion(runway.id)}>{`Runway ${runway.number}`}</h5>
                {expandedRunways[runway.id] && (
                    <>
                        <h6 className='schedule-header'>Schedule:</h6>

                        <div className={"time-slots-grid"}>
                            {runway.schedule?.map(timeSlot => {
                                return(
                                    <div key={timeSlot.id} className={"time-slot-card"}>
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
                <h4>Runways</h4>
            </header>
            <div id='runways-container'>
                {displayRunways}
            </div>
        </>
    )
}

export default Runways;