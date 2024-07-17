import { Button, TextField, Typography } from "@mui/material";
import { postSweetOrderAPICall } from "../Services/SweetMartService";
import { getToken } from "../Services/AuthServices";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
 
const Checkout = ({ user }) => {
 
    const navigate = useNavigate();
    const [cart, setCart] = useState(null);
    const [deliveryDetails, setDeliveryDetails] = useState({
        address: '',
        city: '',
        state: '',
        zip: '',
        phone: ''
    });
 
    useEffect(() => {
        if (user) {
            setCart(user.cart)
        }
    }, [user]);
 
    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setDeliveryDetails({ ...deliveryDetails, [name]: value });
    };
 
    const navigateToPayment = () => {
        navigate('/payment')
    };
 
    if (!user || !cart) {
        return <Typography>Loading...</Typography>;
    }
 
    if (!cart.listProduct || cart.listProduct.length === 0) {
        return <Typography>No items in the cart.</Typography>;
    } else {
        return (
            <>
                <div style={{ display: "flex" }}>
                    <div style={{
                        width: "60%",
                        borderRight: '0.5px solid grey',
                        padding: '20px'
                    }}>
                        <Typography variant="h6" gutterBottom>Delivery Details</Typography>
                        <form>
                            <TextField
                                label="Address"
                                name="address"
                                value={deliveryDetails.address}
                                onChange={handleInputChange}
                                fullWidth
                                margin="normal"
                            />
                            <TextField
                                label="City"
                                name="city"
                                value={deliveryDetails.city}
                                onChange={handleInputChange}
                                fullWidth
                                margin="normal"
                            />
                            <TextField
                                label="State"
                                name="state"
                                value={deliveryDetails.state}
                                onChange={handleInputChange}
                                fullWidth
                                margin="normal"
                            />
                            <TextField
                                label="Zip Code"
                                name="zip"
                                value={deliveryDetails.zip}
                                onChange={handleInputChange}
                                fullWidth
                                margin="normal"
                            />
                            <TextField
                                label="Phone"
                                name="phone"
                                value={deliveryDetails.phone}
                                onChange={handleInputChange}
                                fullWidth
                                margin="normal"
                            />
                        </form>
                    </div>
                    <div style={{
                        width: "40%",
                        display: "flex",
                        flexDirection: "column",
                        padding: "40px"
                    }}>
                        <div>
                            {cart.listProduct.map(product => {
                                return (
                                    <div style={{
                                        display: "flex",
                                        justifyContent: "space-between",
                                        alignItems: "center",
                                        marginBottom: '10px'
                                    }} key={product.id}>
                                        <div style={{
                                            display: "flex"
                                        }}>
                                            <img style={{
                                                width: "50px",
                                                height: "50px",
                                                borderRadius: "5px",
                                                objectFit: "cover",
                                                marginRight: "10px"
                                            }} src={product.photoPath} alt="" />
                                            <Typography>{product.name}</Typography>
                                        </div>
                                        <div>
                                            <Typography style={{textAlign : "right"}}>{product.price}</Typography>
                                        </div>
                                    </div>
                                );
                            })}
                        </div>
                        <br />
                        <br />
                        <div style={{
                            display: "flex",
                            justifyContent: "space-between"
                        }}>
                            <div>
                                <Typography>Sub total</Typography>
                                <Typography>Shipping</Typography>
                                <Typography variant="h5">Total</Typography>
                                <Typography>Including 5% of GST</Typography>
                            </div>
                            <div>
                                <Typography>₹{cart.grandTotal}</Typography>
                                <Typography>₹100</Typography>
                                <Typography variant="h5">₹{cart.grandTotal + 100}</Typography>
                            </div>
                        </div>
                        <br />
                        <Button
                            className="button"
                            onClick={navigateToPayment}
                        >
                            Proceed to Payment
                        </Button>  
                    </div>
                </div>
            </>
        );
    }
}
 
export default Checkout;

// import { Button } from "@mui/material";
// import { postSweetOrderAPICall } from "../Services/SweetMartService";
// import { getToken } from "../Services/AuthServices";
// import { useEffect, useState } from "react";
// import { Typography } from "@mui/material";

// const handleClick = (userId) => {
//     const accessToken = getToken();

//     postSweetOrderAPICall(userId, accessToken).then((response) => {
//         console.log(response);
//     }).catch((error) => {
//         console.log(error)
//     })
// }

// const Checkout = ({ user }) => {

//     const [cart, setCart] = useState(null);
//     console.log(window.height)

//     useEffect(() => {
//         if (user) {
//             setCart(user.cart)
//         }

//     }, [user])

//     const screenHeight = window.innerHeight - 90;

//     if (!user || !cart) {
//         return (
//             <>
//                 loading
//             </>
//         );
//     }

//     if (!cart.listProduct || cart.listProduct.length === 0) {
//         return (
//             <>
//                 no item
//             </>
//         );
//     } else {
//         return (
//             <>
//                 <div style={{ display: "flex" }}>
//                     <div style={{
//                         width: "60%",
//                         borderRight: '0.5px solid #3a5453'
//                     }}>
                        
//                     </div>
//                     <div style={{
//                         width: "40%",
//                         display: "flex",
//                         flexDirection: "column",
//                         justifyContent : "space-between",
//                         padding: "40px",
//                         minHeight : screenHeight,
//                         maxHeight : screenHeight
//                     }}>
//                         <div>
//                             {cart.listProduct.map(product => {
//                                 return (
//                                     <div style={{
//                                         display: "flex",
//                                         justifyContent: "space-between",
//                                         alignItems: "center"
//                                     }}>
//                                         <div style={{
//                                             display: "flex"
//                                         }}>
//                                             <img style={{
//                                                 width: "50px",
//                                                 height: "50px",
//                                                 borderRadius: "5px",
//                                                 objectFit: "cover",
//                                                 marginRight: "10px"
//                                             }} src="/images/products/Boondi-Ladoo.jpg" alt="" />
//                                             <Typography>{product.name}</Typography>
//                                         </div>
//                                         <div>
//                                             <Typography>{product.price}</Typography>
//                                         </div>
//                                     </div>
//                                 )
//                             })}
//                         </div>
//                         <br />
//                         <br />
//                         <div style={{
//                             display: "flex",
//                             justifyContent: "space-between"
//                         }}>
//                             <div>
//                                 <Typography>Sub total</Typography>
//                                 <Typography>shipping</Typography>
//                                 <Typography variant="h5">Total</Typography>
//                                 <Typography>Including ₹6.10 in taxes</Typography>
//                             </div>
//                             <div>
//                                 <Typography>{cart.grandTotal}</Typography>
//                                 <Typography>100</Typography>
//                                 <Typography variant="h5">{cart.grandTotal + 100}</Typography>
//                             </div>
//                         </div>
//                     </div>
//                 </div>
//             </>
//         );
//     }


// }

// export default Checkout;