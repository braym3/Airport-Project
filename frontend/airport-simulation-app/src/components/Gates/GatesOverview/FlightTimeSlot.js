import React from "react";
import {formatTime} from "../../../utils/formatDateTime";

const FlightTimeSlot = ({flightCode, startTime, endTime}) => {

    return(
        <>
            <p><small><b>Flight <i>{flightCode}</i></b></small></p>
            <p><small>Start: <i>{formatTime(startTime)}</i></small></p>
            <p><small>End: <i>{formatTime(endTime)}</i></small></p>
        </>
    );
}

export default FlightTimeSlot;