"use strict";

const address = "";
const toggleSwitch = document.getElementById('toggle-switch');
const tableHeading = document.getElementById('table-heading');
const gateHeading = document.getElementById('gate-header');
const timeHeading = document.getElementById('time-header');
toggleSwitch.checked = false;

fetchDepartures();
toggleTable();
function toggleTable() {
    if (toggleSwitch.checked) {
        tableHeading.textContent = 'Arrivals';
        gateHeading.textContent = 'Arrival Gate';
        timeHeading.textContent = 'Arrival Time';
        fetchArrivals();
    } else {
        tableHeading.textContent = 'Departures';
        gateHeading.textContent = 'Departure Gate';
        timeHeading.textContent = 'Departure Time';
        fetchDepartures();
    }
}

function fetchArrivals(){
    // fetch arrivals from api
    axios.get(`${address}/api/flights/arrivals/MAN`)
        .then(response => {
            const arrivals = response.data;
            displayFlights(arrivals);
        })
        .catch(error => {
            console.error('Error fetching arrivals data:', error);
        })
}

function fetchDepartures(){
    // fetch departures from api
    axios.get(`${address}/api/flights/departures/MAN`)
        .then(response => {
            const departures = response.data;
            displayFlights(departures);
        })
        .catch(error => {
            console.error('Error fetching departures data:', error);
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

function displayFlights(flights){
    const tableBody = document.getElementById('table-body');
    // clear the table body
    tableBody.innerHTML = '';

    flights.forEach((flight, index) => {
        const row = `<tr>` +
            `<th scope="row">${index + 1}</th>` +
            `<td>${flight.flightIata}</td>` +
            `<td>${flight.depIata}</td>` +
            `<td>${flight.arrIata}</td>` +
            `<td>${flight.gate.number}</td>` +
            `<td>${toggleSwitch.checked? formatDateTime(flight.arrTime) : formatDateTime(flight.depTime)}</td>` +
            `<td>${flight.status}</td>` +
            `</tr>`;
        tableBody.insertAdjacentHTML('beforeend', row);
    });
}