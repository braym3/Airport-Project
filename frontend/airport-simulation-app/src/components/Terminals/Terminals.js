import React, { useEffect, useState } from "react";
import axios from "axios";
import './terminals.css';

const Terminals = () => {
    const [terminals, setTerminals] = useState([]);

    useEffect(() => {
        fetchTerminals();
    }, []);

    const fetchTerminals = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/api/terminals/`);
            const terminals = response.data;
            setTerminals(terminals);
        } catch (error) {
            console.error("Error fetching terminal data:", error);
        }
    };

    return (
        <div className="container">
            <header style={{ paddingTop: "50px", paddingBottom: "20px" }}>
                <h4>Terminals</h4>
            </header>

            <div id="terminals-container" className="row">
                {terminals.map((terminal, index) => (
                    <div key={index} className="terminal-card">
                        <h5>{"T" + terminal.number}</h5>
                        <div className="gate-cards-container">
                            {terminal.gates.map((gate, gateIndex) => (
                                <div key={gateIndex} className="gate-card">
                                    {"Gate " + gate.number}
                                </div>
                            ))}
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default Terminals;