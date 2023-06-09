"use strict";

const address = "";
const toggleSwitch = document.getElementById('toggle-switch');
const tableHeading = document.getElementById('table-heading');
const departureGateHeading = document.getElementById('departure-gate-header');
toggleSwitch.checked = false;

fetchDepartures();
toggleTable();
function toggleTable() {
    if (toggleSwitch.checked) {
        tableHeading.textContent = 'Arrivals';
        departureGateHeading.textContent = 'Arrival Gate';
        fetchArrivals();
    } else {
        tableHeading.textContent = 'Departures';
        departureGateHeading.textContent = 'Departure Gate';
        fetchDepartures();
    }
}

function fetchArrivals(){
    // fetch arrivals from api
    axios.get(`${address}/api/flights/get/arrivals/MAN`)
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
    axios.get(`${address}/api/flights/get/departures/MAN`)
        .then(response => {
            const departures = response.data;
            displayFlights(departures);
        })
        .catch(error => {
            console.error('Error fetching departures data:', error);
        })
}

function formatDateTime(offsetDateTime){
    const dateTime = new Date(offsetDateTime);
    const options = {
        year: 'numeric',
        month: 'short',
        day: 'numeric',
        hour: 'numeric',
        minute: 'numeric'
    };
    return new Intl.DateTimeFormat(navigator.language, options).format(dateTime);
}

function displayFlights(flights){
    const tableBody = document.getElementById('table-body');
    const tableHeading = document.getElementById('table-heading');
    // clear the table body
    tableBody.innerHTML = '';

    flights.forEach((flight, index) => {
        const formattedDepTime = formatDateTime(flight.depTime);
        const row = `<tr>` +
            `<th scope="row">${index + 1}</th>` +
            `<td>${flight.flightIata}</td>` +
            `<td>${flight.depIata}</td>` +
            `<td>${flight.arrIata}</td>` +
            `<td>${toggleSwitch.checked? flight.arrGate : flight.depGate}</td>` +
            `<td>${formattedDepTime}</td>` +
            `<td>${flight.status}</td>` +
            `</tr>`;
        tableBody.insertAdjacentHTML('beforeend', row);
    });
}