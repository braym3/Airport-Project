import React from "react";
import "./home.css"

const Home = () => {
    return(
        <>
            <div className="home-container">
                <header style={{paddingTop: '50px', paddingBottom: '20px'}} >
                    <h2>Welcome to this Flight Scheduling Simulation</h2>
                </header>

                <div className="content">
                    <p>This application provides a basic simulation of flight scheduling for an airport,
                        showcasing flights scheduled for a single day.
                        The flight data is modeled on real-time flights arriving and departing from a specified airport.
                    </p>
                    <p>
                        The simulation includes a set of random events that have the potential to impact
                        gates, runways, and flights. These events occur based on their likelihood, adding an element
                        of unpredictability to the airport operations.
                        In the event of an impact, the application attempts to reschedule affected flights as close to
                        their original flight time as possible.
                    </p>
                    <p>
                        This interface allows you to trigger the occurrence of these random events and provides
                        multiple views and representations of flight information, terminal and gate allocations,
                        runway status, and the complete schedule of flights for the day.
                    </p>
                </div>
            </div>
        </>
    )
}

export default Home;