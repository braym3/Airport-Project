import './App.css';
import React, {useState} from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import Navbar from "./components/Navbar/Navbar";
import { Routes, Route } from "react-router";
import { Navigate } from "react-router-dom";
import Home from "./components/Home/Home";
import ArrivalsDepartures from "./components/ArrivalsDepartures/ArrivalsDepartures";
import OrderedFlights from "./components/OrderedFlights/OrderedFlights";
import Gates from "./components/Gates/Gates";
import Terminals from "./components/Terminals/Terminals";
import TimeJumpAlerts from "./components/Navbar/TimeJumpAlerts";
import {handleTimeJump} from "./utils/timeJumpUtils";

function App() {
    const [timeJumpTriggered, setTimeJumpTriggered] = useState(false);
    const handleTimeJumpTriggered = () => {
        handleTimeJump();
        setTimeJumpTriggered(true);
        // set the time jump triggered boolean back to false after 2 seconds
        setTimeout(() => {
            setTimeJumpTriggered(false);
        }, 2000);
    };

    return (
        <div className="App">
            <Navbar onTimeJump={handleTimeJumpTriggered}/>
            <div className="container">
                {/*<TimeJumpAlerts/>*/}
                <div id="alert-container"></div>
                <Routes>
                    <Route path='/' element={<Home/>} />
                    <Route path='arrivals-departures' element={<ArrivalsDepartures />} />
                    <Route path='ordered-flights' element={<OrderedFlights />} />
                    <Route path='terminals' element={<Terminals />} />
                    <Route path='gates' element={<Gates timeJumpTriggered={timeJumpTriggered}/>} />
                    <Route path='*' element={<Navigate to='/' />} />
                </Routes>
            </div>
        </div>
    );
}

export default App;
