import React, {useEffect, useState} from "react";
import axios from "axios";
import {formatTime} from "../../utils/formatDateTime";

const FlightTimeSlot = ({flightId, startTime, endTime}) => {
    const [flightCode, setFlightCode] = useState('');

    useEffect(() => {
        fetchFlightData();
    }, [])

    const fetchFlightData = () => {
        // fetch ordered flights from API
        axios
            .get(`http://localhost:8080/api/flights/${flightId}`)
            .then((response) => {
                const flightCode = response.data.flightIata;
                setFlightCode(flightCode);
            })
            .catch((error) => {
                console.error("Error fetching flight data:", error);
            });
    }

    return(
        <>
            <p><small><b>Flight <i>{flightCode}</i></b></small></p>
            <p><small>Start: <i>{formatTime(startTime)}</i></small></p>
            <p><small>End: <i>{formatTime(endTime)}</i></small></p>
        </>
    );
}

export default FlightTimeSlot;