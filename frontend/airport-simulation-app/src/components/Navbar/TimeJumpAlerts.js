import React, { useState } from "react";
import axios from "axios";
import {formatDateTime} from "../../utils/formatDateTime";

const TimeJump = () => {
    const [triggeredEvents, setTriggeredEvents] = useState([]);

    const handleTimeJump = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/api/impactEvents/timeJump`);
            const triggeredEvents = response.data;
            console.log("Pressed");

            setTriggeredEvents(triggeredEvents);
        } catch (error) {
            console.error("Error triggering impact events:", error);
        }
    };

    return (
        <>
            <button id="time-jump-button" onClick={handleTimeJump}>
                Time Jump
            </button>

            <div id="alert-container">
                {triggeredEvents.map((event, index) => (
                    <div
                        key={index}
                        className="alert alert-primary alert-dismissible fade show"
                        role="alert"
                    >
                        <strong>Impact Event:</strong> {`${formatDateTime(event.startTime)} - ${formatDateTime(event.endTime)}`}
                        <button
                            type="button"
                            className="btn-close"
                            data-bs-dismiss="alert"
                            aria-label="Close"
                        ></button>
                    </div>
                ))}
            </div>
        </>
    );
};

export default TimeJump;