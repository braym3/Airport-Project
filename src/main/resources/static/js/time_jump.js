"use strict";

const alertContainer = document.getElementById('alert-container');

const timeJumpButton = document.getElementById('time-jump-button');
timeJumpButton.addEventListener('click', async () => {
    try{
        const response = await axios.get(`${address}/api/impactEvents/timeJump`);
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
});