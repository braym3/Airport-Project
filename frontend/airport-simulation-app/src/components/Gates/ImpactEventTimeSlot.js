import React, {useEffect, useState} from "react";
import axios from "axios";
import {formatTime} from "../../utils/formatDateTime";

const ImpactEventTimeSlot = ({impactEventType, startTime, endTime}) => {
    return(
        <>
            <p style={{color: "red"}}><b>{impactEventType}</b></p>
            <p><small>Start: <i>{formatTime(startTime)}</i></small></p>
            <p><small>End: <i>{formatTime(endTime)}</i></small></p>
        </>
    );
}

export default ImpactEventTimeSlot;