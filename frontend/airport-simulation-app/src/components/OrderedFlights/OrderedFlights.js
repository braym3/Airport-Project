import React, { useEffect, useState } from "react";
import axios from "axios";
import {formatDateTime} from "../../utils/formatDateTime";

const OrderedFlights = () => {
    const [orderedFlights, setOrderedFlights] = useState([]);

    useEffect(() => {
        fetchOrderedFlights();
    }, []);

    const fetchOrderedFlights = () => {
        // fetch ordered flights from API
        axios
            .get(`http://localhost:8080/api/flights/ordered/MAN`)
            .then((response) => {
                const orderedFlights = response.data;
                setOrderedFlights(orderedFlights);
            })
            .catch((error) => {
                console.error("Error fetching flight data:", error);
            });
    };

    return (
        <div className="container">
            <header style={{ paddingTop: "50px", paddingBottom: "20px" }}>
                <h4>Ordered Flights</h4>
            </header>

            <table className="table" id="flight-table">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Flight Number</th>
                    <th scope="col">Origin</th>
                    <th scope="col">Destination</th>
                    <th scope="col">Terminal</th>
                    <th scope="col">Gate</th>
                    <th scope="col">Departure Time</th>
                    <th scope="col">Arrival Time</th>
                    <th scope="col">Status</th>
                </tr>
                </thead>
                <tbody>
                {orderedFlights.map((flight, index) => (
                    <tr key={index}>
                        <th scope="row">{index + 1}</th>
                        <td>{flight.flightIata}</td>
                        <td>{flight.depIata}</td>
                        <td>{flight.arrIata}</td>
                        <td>{flight.gate.terminal.number}</td>
                        <td>{flight.gate.number}</td>
                        <td>{formatDateTime(flight.depTime)}</td>
                        <td>{formatDateTime(flight.arrTime)}</td>
                        <td>{flight.status}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default OrderedFlights;