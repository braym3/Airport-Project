import axios from "axios";
import planeIcon from "../helpers/white-plane-icon.png";
import errorIcon from "../helpers/white-error-icon.png";

export const fetchChannels = async () => {
    try{
        const response = await axios.get(
            `${process.env.REACT_APP_API_URL}/api/gates/`
        );
        const gatesData = response.data;
        return initializeGates(gatesData);
    } catch (error){
        console.error('Error fetching gate data:', error);
    }
};

export const fetchEpg = async () => {
    try{
        const response = await axios.get(
            `${process.env.REACT_APP_API_URL}/api/gates/schedule/`
        );
        const timeSlotsData = response.data;
        return initializeTimeSlots(timeSlotsData);
    } catch (error){
        console.error('Error fetching schedule data:', error);
    }
};

export const fetchEarliestFlightTime = async () => {
    try{
        const response = await axios.get(
            `${process.env.REACT_APP_API_URL}/api/flights/first/MAN`
        );
        return response.data;
    } catch (error){
        console.error('Error fetching earliest flight time:', error);
    }
};

export const fetchLastFlightTime = async () => {
    try{
        const response = await axios.get(
            `${process.env.REACT_APP_API_URL}/api/flights/last/MAN`
        );
        return response.data;
    } catch (error){
        console.error('Error fetching latest flight time:', error);
    }
};

const initializeTimeSlots = (schedulesData) => {
    return schedulesData.map(timeSlot => {
        let title = '';
        let description = '';
        let image = '';
        if('flight' in timeSlot){
            title = `Flight ${timeSlot.flight.flightIata}`;
            description = `${timeSlot.flight.depIata} - ${timeSlot.flight.arrIata}`;
            image = planeIcon;
        }else if('impactEvent' in timeSlot){
            title = `${timeSlot.impactEvent.type}`;
            description = `${timeSlot.impactEvent.description}`;
            image = errorIcon;
        }
        return {
            id: timeSlot.id,
            channelUuid: timeSlot.gate.id,
            title: title,
            description: description,
            since: timeSlot.startTime,
            till: timeSlot.endTime,
            image: image
        };
    });
}

const initializeGates = (gatesData) => {
    return gatesData.map(gate => {
        return {
            uuid: gate.id,
            type: "channel",
            title: `T${gate.terminal.number} - ${gate.number}`,
            logo: '',
        };
    });
}