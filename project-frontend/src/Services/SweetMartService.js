import axios from "axios";

const AUTH_REST_API_BASE_URL = "http://localhost:7000/api/";

export const getCategoriesAPICall = () => { return axios.get(AUTH_REST_API_BASE_URL + "categories") };

export const getProductsAPICall = () => { return axios.get(AUTH_REST_API_BASE_URL + "products") };

export const getProductByCategoryAPICall = (categoryId) => { return axios.get(AUTH_REST_API_BASE_URL + `products/${categoryId}`) };

export const getUserByUsernameOrEmailAPICall = (usernameOrEmail, accessToken) => {
    return axios.get(AUTH_REST_API_BASE_URL + `users/user/${usernameOrEmail}`, {
        headers: {
            "Authorization": "Bearer " + accessToken
        }
    })
};

export const getProductAPICall = (productId) => { return axios.get(AUTH_REST_API_BASE_URL + `products/product/${productId}`) };

export const addProductToCartAPICall = (cartId, productId, accessToken) => {
    return axios.put(AUTH_REST_API_BASE_URL + `carts/${cartId}/product/${productId}`,
        {},
        {
            headers: {
                "Authorization": "Bearer " + accessToken
            }
        })
};

export const deleteProductToCartAPICall = (cartId, productId, accessToken) => {
    return axios.delete(AUTH_REST_API_BASE_URL + `carts/${cartId}/product/${productId}`,
        {
            headers: {
                "Authorization": "Bearer " + accessToken
            }
        })
};

export const postSweetOrderAPICall = (userId, accessToken) => {
    return axios.post(AUTH_REST_API_BASE_URL + `sweetorders/${userId}`,
        {},
        {
            headers : {
                "Authorization" : "Bearer " + accessToken
            }
        }
    )
};