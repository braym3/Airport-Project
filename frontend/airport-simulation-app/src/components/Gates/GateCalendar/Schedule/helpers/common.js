import axios from "axios";

// export const fetchChannels = async () =>
//     new Promise((res) => setTimeout(() => res(fetchGates), 5000));
//
// export const fetchEpg = async () =>
//     new Promise((res) => setTimeout(() => res(fetchSchedules), 5000));


export const fetchChannels = async () => {
    try{
        const response = await axios.get(
            `http://localhost:8080/api/gates/`
        );
        const gatesData = response.data;
        console.log(`GATES = ${gatesData}`);
        return initializeGates(gatesData);
    } catch (error){
        console.error('Error fetching gate data:', error);
    }
};

export const fetchEpg = async () => {
    try{
        const response = await axios.get(
            `http://localhost:8080/api/gates/schedule/`
        );
        const timeSlotsData = response.data;
        console.log(`schedules = ${timeSlotsData}`);
        return initializeTimeSlots(timeSlotsData);
    } catch (error){
        console.error('Error fetching schedule data:', error);
    }
};

export const fetchEarliestFlightTime = async () => {
    try{
        const response = await axios.get(
            `http://localhost:8080/api/flights/first/MAN`
        );
        return response.data;
    } catch (error){
        console.error('Error fetching earliest flight time:', error);
    }
};

export const fetchLastFlightTime = async () => {
    try{
        const response = await axios.get(
            `http://localhost:8080/api/flights/last/MAN`
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
        if('flight' in timeSlot){
            title = `Flight ${timeSlot.flight.flightIata}`;
            description = `${timeSlot.flight.depIata} - ${timeSlot.flight.arrIata}`
        }else if('impactEvent' in timeSlot){
            title = `${timeSlot.impactEvent.type}`
            description = `${timeSlot.impactEvent.description}`
        }
        return {
            id: timeSlot.id,
            channelUuid: timeSlot.gate.id,
            title: title,
            description: description,
            logo: 'https://lumiere-a.akamaihd.net/v1/images/character_themuppets_kermit_b77a431b.jpeg?region=0%2C0%2C450%2C450',
            since: timeSlot.startTime,
            till: timeSlot.endTime
        };
    });
}

const initializeGates = (gatesData) => {
    return gatesData.map(gate => {
        return {
            uuid: gate.id,
            type: "channel",
            title: `T${gate.terminal.number} - ${gate.number}`,
            logo: 'https://lumiere-a.akamaihd.net/v1/images/character_themuppets_kermit_b77a431b.jpeg?region=0%2C0%2C450%2C450',
        };
    });
}

// export const fetchChannels =  fetchGates();
//
// export const fetchEpg =  fetchSchedules();
