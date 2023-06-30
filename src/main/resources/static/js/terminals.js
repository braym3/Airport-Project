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
        terminalCard.className = 'card';
        terminalCard.style.marginBottom = '30px';

        // create terminal card body
        const terminalCardBody = document.createElement('div');
        terminalCardBody.className = 'card-body';

        // add terminal number to card body
        const terminalNumber = document.createElement('h5');
        terminalNumber.innerText = 'T' + terminal.number;
        terminalCardBody.appendChild(terminalNumber);

        // iterate over gates & create sub cards
        terminal.gates.forEach(function (gate) {
            // create gate card
            const gateCard = document.createElement('div');
            gateCard.className = 'card';
            gateCard.style.marginTop = '10px';

            // create gate card body
            const gateCardBody = document.createElement('div');
            gateCardBody.className = 'card-body';

            // add gate number to card body
            const gateNumber = document.createElement('h6');
            gateNumber.innerText = 'Gate ' + gate.number;
            gateCardBody.appendChild(gateNumber);

            // add gate card body to gate card
            gateCard.appendChild(gateCardBody);
            // add gate card to terminal card
            terminalCardBody.appendChild(gateCard);
        });

        // add terminal card body to terminal card
        terminalCard.appendChild(terminalCardBody);
        // add terminal card to terminals container
        terminalsContainer.appendChild(terminalCard);
    })
}