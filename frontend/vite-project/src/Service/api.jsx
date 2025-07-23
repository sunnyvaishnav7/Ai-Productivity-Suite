import axios from "axios";


const API_URL = "https://ai-productivity-suite-production.up.railway.app/api/ask";

export const fetchChatResponse = async (question) => {
    try{
        const response = await axios.post(API_URL,{question});
        return response.data;
    }catch(error){
        console.error(error);
        throw error;
    }
}