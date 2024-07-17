import React, { useState, useEffect } from "react";
import { getLoggedInUser } from "../Services/AuthServices";

export default function Profile() {
    const [user, setUser] = useState(null);

    useEffect(() => {
        const fetchUser = async () => {
            const loggedInUser = await getLoggedInUser();
            setUser(loggedInUser);
        };

        fetchUser();
    }, []);

    const userDetailsStyle = {
        padding: '10px',
        margin: '10px 0',
        border: '1px solid #ccc',
        borderRadius: '5px',
        backgroundColor: '#f9f9f9'
    };

    return (
        <>
            <br />
            <h2 className='header'>Profile</h2>
            {user ? (
                <div style={userDetailsStyle}>
                    <p>Name: {user.username}</p>
                    <p>Email: {user.email}</p>
                </div>
            ) : (
                <p>Loading user details...</p>
            )}
            <div className="container">
                <h2 className='header'>Order History</h2>
                {user && user.sweetOrder ? (
                    <table>
                        <thead>
                            <tr>
                                <th>Order ID</th>
                                <th>Order Date</th>
                                <th>Bill Amount</th>
                            </tr>
                        </thead>
                        <tbody>
                            {user.sweetOrder.map(order => (
                                <tr key={order.sweetOrderId}>
                                    <td>{order.sweetOrderId}</td>
                                    <td>{order.orderDate}</td>
                                    <td>₹{order.totalCost}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                ) : (
                    <p>Loading order history...</p>
                )}
            </div>
        </>
    );
}
