import './App.css';
import React from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import Navbar from "./components/Navbar";
import { Routes, Route } from "react-router";
import { Navigate } from "react-router-dom";
import Home from "./components/Home";
import ArrivalsDepartures from "./components/ArrivalsDepartures";

function App() {
    return (
        <div className="App">
            <Navbar/>
            <div className="container">
                <div id="alert-container"></div>
                <Routes>
                    <Route path='/' element={<Home/>} />
                    <Route path='arrivals-departures' element={<ArrivalsDepartures />} />
                    <Route path='*' element={<Navigate to='/' />} />
                </Routes>
            </div>
        </div>
    );
}

export default App;
