import axios from "axios";
import { formatDateTime, formatTime } from "./formatDateTime";



export const handleTimeJumpEvents = (triggeredEvents) => {
    const alertContainer = document.getElementById('alert-container');

    triggeredEvents.forEach(event => {
        const message = `${formatTime(event.startTime)} - ${formatTime(event.endTime)}`;

        const alertDiv = document.createElement('div');
        alertDiv.classList.add('alert', 'alert-primary', 'alert-dismissible', 'fade', 'show');
        alertDiv.innerHTML = `<strong>Impact Event:</strong> ${message}
                              <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`;

        // attach event listener to close button
        const closeButton = alertDiv.querySelector('.btn-close');
        closeButton.addEventListener('click', () => {
            // remove the alert from the DOM
            alertDiv.remove();
        });

        alertContainer.appendChild(alertDiv);
    })
};