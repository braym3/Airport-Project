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

async function fetchFlight(flightId){
    // fetch flight from api using id
    axios.get(`${address}/api/flights/${flightId}`)
        .then(response => {
            return response.data;
        })
        .catch(error => {
            console.error('Error fetching flight data:', error);
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


        // add terminal and gate number to card body
        const gateCardBody = document.createElement('h5');
        gateCardBody.innerText = 'T' + gate.terminal.number + ' gate ' + gate.number;
        gateCard.appendChild(gateCardBody);

        // create container for gate cards
        const scheduleCardsContainer = document.createElement('div');
        scheduleCardsContainer.classList.add('schedule-cards-container');

        // iterate over schedule & create sub cards
        gate.schedule.forEach(function (timeSlot) {
            // create time slot card
            const timeSlotCard = document.createElement('div');
            timeSlotCard.classList.add('time-slot-card');

            const flightId = document.createElement("p");
            // flightId.innerText = `Flight: ${fetchFlight(timeSlot.flightId).id}`;
            // flightId.classList.add("card-text");
            // timeSlotCard.appendChild(flightId);
            console.log(`Flight: ${fetchFlight(timeSlot.flightId)}`)

            const startTime = document.createElement("p");
            startTime.innerText = `Occupied starting: ${formatDateTime(timeSlot.startTime)}`;
            startTime.classList.add("card-text");
            timeSlotCard.appendChild(startTime);

            const endTime = document.createElement("p");
            endTime.innerText = `Occupied until: ${formatDateTime(timeSlot.endTime)}`;
            endTime.classList.add("card-text");
            timeSlotCard.appendChild(endTime);

            // add time slot card to container
            scheduleCardsContainer.appendChild(timeSlotCard);
        });

        // add time slot cards container to gate
        gateCard.appendChild(scheduleCardsContainer);
        // add gate card to gates container
        gatesContainer.appendChild(gateCard);
    })

    function createGateCard(gate){
        // create gate card
        const gateCard = document.createElement('div');
        gateCard.classList.add('gate-card');
    }
}