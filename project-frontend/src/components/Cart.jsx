import { useEffect, useState } from "react";
import { Typography, Button } from "@mui/material";
import { getToken, saveLoggedInUser } from "../Services/AuthServices";
import { deleteProductToCartAPICall, getUserByUsernameOrEmailAPICall } from "../Services/SweetMartService";
import { useNavigate } from "react-router-dom";

const Cart = ({ user, setUser, toggleCart }) => {
    
    // State to store the cart data
    const [cart, setCart] = useState(null);

    const navigate = useNavigate();

    useEffect(() => {
        // Set the cart state when the user changes
        if (user) {
            setCart(user.cart)
        }
    }, [user]);

    // Function to handle product removal from the cart
    const handleClick = (productId) => {
        const accessToken = getToken();
        deleteProductToCartAPICall(cart.cartId, productId, accessToken).then((response) => {
            console.log(response);
            // Fetch updated user data and update the state
            getUserByUsernameOrEmailAPICall(user.username, getToken()).then((response) => {
                saveLoggedInUser(response.data)
                setUser(response.data)
            })
        }).catch((error) => {
            console.log(error)
        })
    }

    return (
        <>
            <div style={{
                padding: "30px",
                paddingTop: "20px"
            }}>
                <Typography variant="h5">Cart</Typography>
                <br />
                <hr />
                
                {user && cart && cart.listProduct.length > 0 ? (
                    <div>
                        {cart.listProduct.map((product) => {
                            return (
                                <div style={{
                                    padding: "5px",
                                    margin: "10px",
                                    display: 'flex',
                                    alignItems: "center",
                                    justifyContent: "space-between"
                                }}>
                                    <div style={{
                                        display: "flex",
                                        justifyContent: "center",
                                        alignItems: "center"
                                    }}>
                                        <img style={{
                                            borderRadius: "5px",
                                            width: "70px",
                                            height: "70px",
                                            objectFit: "cover",
                                            marginRight: "10px"

                                        }} src={product.photoPath} alt="" />
                                        <div>
                                            <Typography variant="h6" style={{textTransform : "capitalize"}}>{product.name}</Typography>
                                            <Typography>Price : ₹{product.price}</Typography>
                                        </div>
                                    </div>
                                    <div>
                                        <Typography className="sub-text cross"><span onClick={() => { handleClick(product.productId) }}>❌</span></Typography>
                                    </div>
                                </div>
                            )
                        })}
                        <br />
                        <hr />
                        <br />
                        <Typography variant="h6">Grand Total : ₹{cart.grandTotal}</Typography>
                        <br />
                        <Button style={{
                            width: "100%"
                        }} className="button" variant="contained" size="large" onClick={() => {
                            // Navigate to checkout and close the cart
                            navigate("/checkout");
                            toggleCart();
                        }}>Proceed to checkout</Button>
                    </div>
                ) : (
                    // Display message if the cart is empty
                    <div>
                        <Typography>Your cart is empty</Typography>
                    </div>
                )}
            </div>
        </>
    );
};

export default Cart;
