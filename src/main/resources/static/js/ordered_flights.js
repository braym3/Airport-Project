"use strict";

const address = "";

fetchOrderedFlights();

function fetchOrderedFlights(){
    // fetch arrivals from api
    axios.get(`${address}/api/flights/ordered/MAN`)
        .then(response => {
            const orderedFlights = response.data;
            displayFlights(orderedFlights);
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
            `<td>${flight.gate.terminal.number}</td>` +
            `<td>${flight.gate.number}</td>` +
            `<td>${formatDateTime(flight.depTime)}</td>` +
            `<td>${formatDateTime(flight.arrTime)}</td>` +
            `<td>${flight.status}</td>` +
            `</tr>`;
        tableBody.insertAdjacentHTML('beforeend', row);
    });
}