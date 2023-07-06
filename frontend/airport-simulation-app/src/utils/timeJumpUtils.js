import axios from "axios";
import { formatDateTime } from "./formatDateTime";



export const handleTimeJump = async () => {
    const alertContainer = document.getElementById('alert-container');
    try{
        const response = await axios.get(`http://localhost:8080/api/impactEvents/timeJump`);
        const triggeredEvents = response.data;
        console.log(`Pressed`);

        triggeredEvents.forEach(event => {
            const message = `${formatDateTime(event.startTime)} - ${formatDateTime(event.endTime)}`;

            const alertDiv = document.createElement('div');
            alertDiv.classList.add('alert', 'alert-primary', 'alert-dismissible', 'fade', 'show');
            alertDiv.innerHTML = `<strong>Impact Event:</strong> ${message}
                                  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`;
            alertContainer.appendChild(alertDiv);
        })
    }catch (error){
        console.error('Error triggering impact events:', error);
    }
};