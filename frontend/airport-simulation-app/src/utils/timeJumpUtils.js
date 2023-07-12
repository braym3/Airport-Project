import { formatTime } from "./formatDateTime";
import * as events from "events";

export const handleTimeJumpEvents = (triggeredEvents) => {

    const storedAlerts = JSON.parse(localStorage.getItem("timeJumpAlerts"));

    let updatedAlerts = [...triggeredEvents];
    if(storedAlerts){
        updatedAlerts = [...storedAlerts, ...triggeredEvents];
    }

    //store the alerts in local storage
    localStorage.setItem("timeJumpAlerts", JSON.stringify(updatedAlerts));
};