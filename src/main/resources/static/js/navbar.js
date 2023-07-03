// fetch the navbar.html file and insert content into navbar container
axios.get('navbar.html')
    .then(response => {
        const navbarContainer = document.getElementById('navbar-container');
        navbarContainer.innerHTML = response.data;
    })
    .catch(error => {
        console.error('Error fetching navbar:', error);
    })