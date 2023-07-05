import React from "react";

const Navbar = () => {
    return(
        <>
        <nav className="navbar navbar-expand-lg navbar-light bg-light">
            <div className="container-fluid">
                <a className="navbar-brand" href="/">Airport App</a>
                <button className="navbar-toggler" type="button" data-bs-toggle="collapse"
                        data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false"
                        aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse" id="navbarNav">
                    <ul className="navbar-nav">
                        <li className="nav-item">
                            <a className="nav-link" aria-current="page"
                               href="/arrivals-departures">Arrivals/Departures</a>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link" href="/ordered-flights">Ordered Flights</a>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link" href="/terminals">Terminals</a>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link" href="/gates">Gates</a>
                        </li>
                    </ul>
                </div>
                <button id="time-jump-button" className="btn btn-primary">+1 Hr</button>
            </div>
        </nav>
        </>
    )
}

export default Navbar;