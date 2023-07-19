import React, { useEffect, useState } from "react";
import axios from "axios";
import {formatDateTime} from "../../utils/formatDateTime";

const ArrivalsDepartures = () => {
    const [flights, setFlights] = useState([]);
    const [isArrivals, setIsArrivals] = useState(false);

    useEffect(() => {
        toggleTable();
    }, []);

    const toggleTable = async () => {
        setIsArrivals(!isArrivals);
        try {
            const response = await axios.get(
                `${process.env.REACT_APP_API_URL}/api/flights/${isArrivals ? "arrivals" : "departures"}/MAN`
            );
            const flightsData = response.data;
            setFlights(flightsData);
        } catch (error) {
            console.error(`Error fetching ${isArrivals ? "arrivals" : "departures"} data:`, error);
        }
    };

    return (
        <>
            <div className="row" style={{ paddingTop: '50px', paddingBottom: '20px' }}>
                <div className="col">
                    <h4 className="table-heading" id="table-heading">
                        {isArrivals ? "Arrivals" : "Departures"}
                    </h4>
                </div>
                <div className="col-auto toggle-container">
                    <div className="form-check form-switch">
                        <label className="form-check-label">
                            {isArrivals ? "Departures/Arrivals" : "Arrivals/Departures"}
                            <input
                                className="form-check-input"
                                type="checkbox"
                                id="toggle-switch"
                                onChange={toggleTable}
                                checked={isArrivals}
                            />
                        </label>
                    </div>
                </div>
            </div>

            <table className="table" id="flight-table">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Flight Number</th>
                    <th scope="col">Origin</th>
                    <th scope="col">Destination</th>
                    <th scope="col">Terminal</th>
                    <th scope="col" id="gate-header">
                        {isArrivals ? "Arrival Gate" : "Departure Gate"}
                    </th>
                    <th scope="col" id="time-header">
                        {isArrivals ? "Arrival Time" : "Departure Time"}
                    </th>
                    <th scope="col">Status</th>
                </tr>
                </thead>
                <tbody id="table-body">
                {flights.map((flight, index) => (
                    <tr key={index}>
                        <th scope="row">{index + 1}</th>
                        <td>{flight.flightIata}</td>
                        <td>{flight.depIata}</td>
                        <td>{flight.arrIata}</td>
                        <td>{flight.gate.terminal.number}</td>
                        <td>{flight.gate.number}</td>
                        <td>
                            {isArrivals
                                ? formatDateTime(flight.arrTime)
                                : formatDateTime(flight.depTime)}
                        </td>
                        <td>{flight.status}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </>
    );
};

export default ArrivalsDepartures;