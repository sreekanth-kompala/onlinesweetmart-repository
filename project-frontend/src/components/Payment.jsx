import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { getToken } from "../Services/AuthServices";
import { postSweetOrderAPICall } from "../Services/SweetMartService";
import { getUserByUsernameOrEmailAPICall } from "../Services/SweetMartService";
import { saveLoggedInUser } from "../Services/AuthServices";
import { Button } from "@mui/material";

const Payment = ({ user, setUser }) => {
    const [paymentMode, setPaymentMode] = useState("");
    const [upiDetails, setUpiDetails] = useState({ upiId: "", pin: "" });
    const [cardDetails, setCardDetails] = useState({ cardNumber: "", expiryDate: "", cvv: "", pin: "" });

    const navigate = useNavigate();

    const PlaceOrder = (userId) => {
        const accessToken = getToken();

        postSweetOrderAPICall(userId, accessToken).then((response) => {
            const sweetOrderDetails = response.data;
            getUserByUsernameOrEmailAPICall(user.username, getToken()).then((response) => {
                saveLoggedInUser(response.data)
                setUser(response.data)
            })
            console.log(response);
            navigate('/order_placed', { state: { sweetOrderDetails } });
        }).catch((error) => {
            console.log(error);
        });
    };

    const handlePaymentModeChange = (event) => {
        setPaymentMode(event.target.value);
    };

    const handleUpiChange = (event) => {
        const { name, value } = event.target;
        setUpiDetails({ ...upiDetails, [name]: value });
    };

    const handleCardChange = (event) => {
        const { name, value } = event.target;
        setCardDetails({ ...cardDetails, [name]: value });
    };

    const handleOption = (event) => {
        event.preventDefault();
        if (paymentMode === "upi") {
            console.log("UPI Payment Details:", upiDetails);
        } else if (paymentMode === "card") {
            console.log("Card Payment Details:", cardDetails);
        }
    };

    return (
        <div style={styles.paymentContainer}>
            <h2 style={styles.heading}>Payment</h2>
            <form onSubmit={handleOption}>
                <div style={styles.formGroup}>
                    <label>Choose Mode of Payment:</label>
                    <select value={paymentMode} onChange={handlePaymentModeChange} required style={styles.select}>
                        <option value="">Select</option>
                        <option value="upi">UPI</option>
                        <option value="card">Card</option>
                    </select>
                </div>

                {paymentMode === "upi" && (
                    <div style={styles.upiDetails}>
                        <div style={styles.formGroup}>
                            <label>UPI ID:</label>
                            <input type="text" name="upiId" value={upiDetails.upiId} onChange={handleUpiChange} required style={styles.input} />
                        </div>
                        <div style={styles.formGroup}>
                            <label>PIN:</label>
                            <input type="password" name="pin" value={upiDetails.pin} onChange={handleUpiChange} required style={styles.input} />
                        </div>
                    </div>
                )}

                {paymentMode === "card" && (
                    <div style={styles.cardDetails}>
                        <div style={styles.formGroup}>
                            <label>Card Number:</label>
                            <input type="text" name="cardNumber" value={cardDetails.cardNumber} onChange={handleCardChange} required style={styles.input} />
                        </div>
                        <div style={styles.formGroup}>
                            <label>Expiry Date (MM/YY):</label>
                            <input type="text" name="expiryDate" value={cardDetails.expiryDate} onChange={handleCardChange} required style={styles.input} />
                        </div>
                        <div style={styles.formGroup}>
                            <label>CVV:</label>
                            <input type="password" name="cvv" value={cardDetails.cvv} onChange={handleCardChange} required style={styles.input} />
                        </div>
                        <div style={styles.formGroup}>
                            <label>PIN:</label>
                            <input type="password" name="pin" value={cardDetails.pin} onChange={handleCardChange} required style={styles.input} />
                        </div>
                    </div>
                )}

                <Button type="submit" className="button" onClick={() => PlaceOrder(user.userId)}>Submit</Button>
            </form>
        </div>
    );
};

const styles = {
    paymentContainer: {
        maxWidth: "600px",
        margin: "100px auto",
        padding: "20px",
        border: "1px solid #ddd",
        borderRadius: "8px",
        backgroundColor: "#f9f9f9",
        boxShadow: "0 0 10px rgba(0, 0, 0, 0.1)"
    },
    heading: {
        textAlign: "center",
        marginBottom: "20px",
        color: "#333"
    },
    formGroup: {
        marginBottom: "15px"
    },
    select: {
        width: "100%",
        padding: "8px",
        borderRadius: "4px",
        border: "1px solid #ccc",
        marginTop: "8px"
    },
    input: {
        width: "100%",
        padding: "8px",
        borderRadius: "4px",
        border: "1px solid #ccc",
        marginTop: "8px"
    },
    button: {
        width: "100%",
        padding: "10px",
        borderRadius: "4px",
        border: "none",
        backgroundColor: "#4CAF50",
        color: "white",
        fontSize: "16px",
        cursor: "pointer",
        marginTop: "20px"
    },
    upiDetails: {
        marginTop: "20px"
    },
    cardDetails: {
        marginTop: "20px"
    }
};

export default Payment;
