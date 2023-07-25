import './App.css';
import React, {useEffect, useState} from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import NavigationBar from "./components/Navbar/NavigationBar";
import { Routes, Route } from "react-router";
import { Navigate } from "react-router-dom";
import Home from "./components/Home/Home";
import ArrivalsDepartures from "./components/ArrivalsDepartures/ArrivalsDepartures";
import OrderedFlights from "./components/OrderedFlights/OrderedFlights";
import Gates from "./components/Gates/GatesOverview/Gates";
import Terminals from "./components/Terminals/Terminals";
import Alerts from "./components/Alerts/Alerts";
import GateSchedule from "./components/Gates/GateCalendar/Schedule/GateSchedule";
import {fetchCurrentTime, handleFastForward} from "./services/time";

function App() {
    const [currentTime, setCurrentTime] = useState(null);
    const [timeJumpTriggered, setTimeJumpTriggered] = useState(false);

    // to refresh gate/flight data
    const handleTimeJumpDataRefresh = () => {
        handleFastForward().then(() => {
            setTimeJumpTriggered(true);
            // set the time jump triggered boolean back to false after 2 seconds
            setTimeout(() => {
                setTimeJumpTriggered(false);
            }, 2000);
        });
    };

    useEffect(() => {
        fetchCurrentTime().then((time) => setCurrentTime(time));
    }, [timeJumpTriggered])

    return (
        <div className="App">
            <NavigationBar onTimeJump={handleTimeJumpDataRefresh} currentTime={currentTime}/>
            <div className="container">
                <Alerts />
                <Routes>
                    <Route path='/' element={<Home/>} />
                    <Route path='arrivals-departures' element={<ArrivalsDepartures />} />
                    <Route path='ordered-flights' element={<OrderedFlights />} />
                    <Route path='terminals' element={<Terminals />} />
                    <Route path='gates' element={<Gates timeJumpTriggered={timeJumpTriggered}/>} />
                    <Route path='gate-calendar' element={<GateSchedule timeJumpTriggered={timeJumpTriggered}/>}/>
                    <Route path='*' element={<Navigate to='/' />} />
                </Routes>
            </div>
        </div>
    );
}

export default App;
