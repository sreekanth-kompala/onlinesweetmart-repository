import axios from "axios"

const AUTH_REST_API_BASE_URL = "http://localhost:7000/api/auth"

export const registerAPICall = (registerObj) => { return axios.post(AUTH_REST_API_BASE_URL + "/register", registerObj) }

export const loginAPICall = (loginObj) => { return axios.post(AUTH_REST_API_BASE_URL + "/login", loginObj) }

export const storeToken = (token) => { localStorage.setItem("token", token) };

export const getToken = () => { return localStorage.getItem("token") };

export const saveLoggedInUser = (user) => { sessionStorage.setItem("authenticatedUser", JSON.stringify(user)) }

export const isUserLoggedIn = () => {

    const user = sessionStorage.getItem("authenticatedUser")

    if(user == null) {
        return false;
    }

    return true;
}

export const getLoggedInUser = () => {
    return JSON.parse(sessionStorage.getItem("authenticatedUser"));
};

export const logout = () => {
    console.log("hello")
    localStorage.clear();
    sessionStorage.clear();
}