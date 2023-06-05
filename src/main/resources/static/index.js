"use strict";

(function() {
    const output = document.getElementById("output");

    const myModal = new bootstrap.Modal('#myModal');

    const address = "";

    let currentCat = {};

    async function getCats() {
        try {
            output.innerHTML = "";
            const res = await axios.get(`${address}/getAll`);
            res.data.forEach(cat => renderCat(cat));
        } catch (e) {
            console.error(e);
        }
    }

    function renderCat({id, name, length, hasWhiskers, evil}) {
        const cat = document.createElement("div");
        cat.classList.add("col");
        const catCard = document.createElement("div");
        catCard.classList.add("card");

        const catBody = document.createElement("div");
        catBody.classList.add("card-body");
        const catName = document.createElement("p");
        catName.innerText = `Name: ${name}`;
        catName.classList.add("card-text");
        catBody.appendChild(catName);

        const catLength = document.createElement("p");
        catLength.innerText = `Length: ${length}`;
        catLength.classList.add("card-text");
        catBody.appendChild(catLength);

        const catWhiskers = document.createElement("p");
        catWhiskers.innerText = `Whiskers: ${hasWhiskers}`;
        catWhiskers.classList.add("card-text");
        catBody.appendChild(catWhiskers);

        const catEvil = document.createElement("p");
        catEvil.innerText = `Evil: ${evil}`;
        catEvil.classList.add("card-text");
        catBody.appendChild(catEvil);

        const updateBtn = document.createElement("button");
        updateBtn.innerText = 'UPDATE';
        updateBtn.classList.add("btn", "btn-danger");
        updateBtn.addEventListener('click', () => {
            currentCat = {id, name, length, hasWhiskers, evil};
            myModal.show();
        });
        catBody.appendChild(updateBtn);

        const deleteBtn = document.createElement("button");
        deleteBtn.innerText = 'DELETE';
        deleteBtn.classList.add("btn", "btn-danger");
        deleteBtn.addEventListener('click', () => deleteCat(id));
        catBody.appendChild(deleteBtn);
        catCard.appendChild(catBody);
        cat.appendChild(catCard);

        output.appendChild(cat);
    }

    async function deleteCat(id) {
        const res = await axios.delete(`${address}/remove/${id}`);
        getCats();
    }

    document.getElementById("myModal").addEventListener("show.bs.modal", function(e) {
        const form = document.getElementById("updateForm");
        for (let k of Object.keys(currentCat)) {
            if (k === 'id') continue;
            const input = form[k];
            if (input.type === 'checkbox')
                input.checked = currentCat[k];
            else
                input.value = currentCat[k];
        }
    });

    document.getElementById("catForm").addEventListener("submit", async function(e) {
        e.preventDefault();
        const {name, length, hasWhiskers, evil} = this;

        const newCat = {
            name: name.value,
            length: length.value,
            hasWhiskers: hasWhiskers.checked,
            evil: evil.checked
        }
        this.reset();
        name.focus();
        try {
            const res = await axios.post(`${address}/create`, newCat);
            getCats();
        } catch(error) {
            console.error(error);
        }
    });

    document.getElementById("updateForm").addEventListener("submit", async function(e) {
        e.preventDefault();
        const {name, length, hasWhiskers, evil} = this;

        const newCat = {
            name: name.value,
            length: length.value,
            hasWhiskers: hasWhiskers.checked,
            evil: evil.checked
        }
        this.reset();
        name.focus();
        try {
            const res = await axios.patch(`${address}/update/${currentCat.id}`, null, { params: newCat });
            getCats();
            myModal.hide();
        } catch(error) {
            console.error(error);
        }
    });

    getCats();
})();