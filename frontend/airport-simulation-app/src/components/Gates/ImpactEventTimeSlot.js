import React, {useEffect, useState} from "react";
import axios from "axios";
import {formatDateTime} from "../../utils/formatDateTime";

const ImpactEventTimeSlot = ({impactEventId, startTime, endTime}) => {
    const [impactEventType, setImpactEventType] = useState('');

    useEffect(() => {
        fetchImpactEventData();
    }, [])

    const fetchImpactEventData = () => {
        // fetch impact event from API
        axios
            .get(`http://localhost:8080/api/impactEvents/${impactEventId}`)
            .then((response) => {
                const impactEventType = response.data.type;
                setImpactEventType(impactEventType);
            })
            .catch((error) => {
                console.error("Error fetching impact event data:", error);
            });
    }

    return(
        <>
            <p style={{color: "red"}}><b>{impactEventType}</b></p>
            <p><small>Start: <i>{formatDateTime(startTime)}</i></small></p>
            <p><small>End: <i>{formatDateTime(endTime)}</i></small></p>
        </>
    );
}

export default ImpactEventTimeSlot;