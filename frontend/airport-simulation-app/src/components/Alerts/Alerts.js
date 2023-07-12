import React, { useState, useEffect } from "react";
import {formatTime} from "../../utils/formatDateTime";
import {Alert} from "react-bootstrap";

const Alerts = () => {
    const [alerts, setAlerts] = useState([]);


    useEffect(() => {
        const storedAlerts = JSON.parse(localStorage.getItem("timeJumpAlerts"));
        if(storedAlerts){
            setAlerts(storedAlerts);
        }
        console.log(alerts);
    }, [localStorage.getItem("timeJumpAlerts")]);

    const removeAlert = (index) => {
        const updatedAlerts = [...alerts];
        updatedAlerts.splice(index, 1);
        setAlerts(updatedAlerts);
        localStorage.setItem("timeJumpAlerts", JSON.stringify(updatedAlerts));
    }


    const displayAlerts  = alerts.map((event, index) =>
        <Alert key={index} variant={"primary"} onClose={() => removeAlert(index)} dismissible>
            <strong>{event.impactEvent.type}:</strong> {'gate' in event ?
            (`T${event.gate.terminal.number} gate ${event.gate.number}`) : (`runway ${event.runway.number}`)}
            <p>{formatTime(event.startTime)} - {formatTime(event.endTime)}</p>
        </Alert>
    );

    return(
        <>
            <div id="alert-container">
                {displayAlerts}
            </div>
        </>
    );
};

export default Alerts;