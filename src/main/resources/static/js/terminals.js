"use strict";

const address = "";
const terminalsContainer = document.getElementById('terminals-container');

fetchTerminals();

function fetchTerminals(){
    // fetch arrivals from api
    axios.get(`${address}/api/terminals/`)
        .then(response => {
            const terminals = response.data;
            displayTerminals(terminals);
        })
        .catch(error => {
            console.error('Error fetching terminal data:', error);
        })
}

function displayTerminals(terminals){
    // iterate over terminals & create cards
    terminals.forEach(function (terminal) {
        // create terminal card
        const terminalCard = document.createElement('div');
        terminalCard.classList.add('terminal-card');

        // add terminal number to card body
        const terminalCardBody = document.createElement('h5');
        terminalCardBody.innerText = 'T' + terminal.number;
        terminalCard.appendChild(terminalCardBody);

        // create container for gate cards
        const gateCardsContainer = document.createElement('div');
        gateCardsContainer.classList.add('gate-cards-container');

        // iterate over gates & create sub cards
        terminal.gates.forEach(function (gate) {
            // create gate card
            const gateCard = document.createElement('div');
            gateCard.classList.add('gate-card');
            gateCard.innerText = 'Gate ' + gate.number;

            // add gate card to container
            gateCardsContainer.appendChild(gateCard);
        });

        // add gate cards container to terminal
        terminalCard.appendChild(gateCardsContainer);
        // add terminal card to terminals container
        terminalsContainer.appendChild(terminalCard);
    })
}