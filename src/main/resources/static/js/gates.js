"use strict";

const address = "";
const gatesContainer = document.getElementById('gates-container');

fetchGates();

function fetchGates(){
    // fetch all gates from api
    axios.get(`${address}/api/gates/`)
        .then(response => {
            const gates = response.data;
            displayGates(gates);
        })
        .catch(error => {
            console.error('Error fetching gate data:', error);
        })
}

// Convert UTC flight time to user device local time, and format it
function formatDateTime(flightTimeUTC){
    const localFlightTime = new Date(flightTimeUTC+'Z');
    const options = {
        year: 'numeric',
        month: 'short',
        day: 'numeric',
        hour: 'numeric',
        minute: 'numeric'
    };
    return new Intl.DateTimeFormat(navigator.language, options).format(localFlightTime);
}

function displayGates(gates){
    // iterate over gates & create cards
    gates.forEach(function (gate) {
        // create gate card
        const gateCard = createGateCard(gate);

        // add gate card to gates container
        gatesContainer.appendChild(gateCard);
        });
}

function createGateCard(gate){
    // create gate card
    const gateCard = document.createElement('div');
    gateCard.classList.add('gate-card');

    // add gate and terminal number to the card
    const gateHeader = document.createElement('h5');
    gateHeader.classList.add('gate-header');
    gateHeader.textContent = `T${gate.terminal.number}: gate ${gate.number}`;
    gateCard.appendChild(gateHeader);

    // add schedule header to the card
    const scheduleHeader = document.createElement('h6');
    scheduleHeader.classList.add('schedule-header');
    scheduleHeader.textContent = `Schedule:`;
    gateCard.appendChild(scheduleHeader);

    // grid layout for gate schedule cards
    const timeSlotsGrid = document.createElement('div');
    timeSlotsGrid.classList.add('time-slots-grid');

    gate.schedule.forEach(timeSlot => {
        const timeSlotCard = createTimeSlotCard(timeSlot);
        timeSlotsGrid.appendChild(timeSlotCard);
    });

    gateCard.appendChild(timeSlotsGrid);
    return gateCard;
}

function createTimeSlotCard(timeSlot){
    // create time slot card
    const timeSlotCard = document.createElement('div');
    timeSlotCard.classList.add('time-slot-card');

    // if time slot is occupied by a flight - fetch flight data from api using flight id
    if(timeSlot.flightId){
        axios.get(`${address}/api/flights/${timeSlot.flightId}`, {
            maxRedirects: 0})
            .then(response => {
                const flightCode = response.data.flightIata;
                const startTime  = timeSlot.startTime;
                const endTime = timeSlot.endTime;

                timeSlotCard.innerHTML = `
            <p><small><b>Flight <i>${flightCode}</i></b></small></p>
            <p><small>Start time: <i>${formatDateTime(startTime)}</i></small></p>
            <p><small>End time: <i>${formatDateTime(endTime)}</i></small></p>`;
            })
            .catch(error => {
                console.error('Error fetching flight data:', error);
            })
    }else if (timeSlot.impactEventId){
        // if time slot is occupied by an impact event - fetch event data from api using impact event id
        axios.get(`${address}/api/impact-events/${timeSlot.impactEventId}`, {
            maxRedirects: 0})
            .then(response => {
                const eventType = response.data.type;
                const startTime  = timeSlot.startTime;
                const endTime = timeSlot.endTime;

                timeSlotCard.innerHTML = `
            <p style="color: red"><small><b>${eventType}</b></small></p>
            <p><small>Start: <i>${formatDateTime(startTime)}</i></small></p>
            <p><small>End: <i>${formatDateTime(endTime)}</i></small></p>`;
            })
            .catch(error => {
                console.error('Error fetching impact event data:', error);
            })
    }


    return timeSlotCard;
}