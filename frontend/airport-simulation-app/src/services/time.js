import axios from "axios";

export const fetchCurrentTime = async () => {
    try{
        const response = await axios.get(
            `${process.env.REACT_APP_API_URL}/api/time/`
        );
        return response.data;
    } catch (error){
        console.error('Error fetching current simulation time:', error);
    }
};

export const handleFastForward = async () => {
    try{
        const response = await axios.post(
            `${process.env.REACT_APP_API_URL}/api/time/fast-forward`
        );
        return response.data;
    } catch (error){
        console.error('Error fast forwarding simulation time:', error);
    }
};

// export const handleFastForward = async () => {
//     axios
//         .get(`${process.env.REACT_APP_API_URL}/api/time/fast-forward`)
//         .then(function (response) {
//             return response.data;
//         })
//         .catch((error) => {
//             console.error('Error fast forwarding simulation time:', error);
//         });
// };