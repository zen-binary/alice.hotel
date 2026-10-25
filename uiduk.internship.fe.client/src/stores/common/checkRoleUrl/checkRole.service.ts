import http from "@/utils/http";

export const getRole = (screenName: String) => {
    return http.get(`/auth/check`,{
        params :{
            value: screenName
        } 
    });
}


