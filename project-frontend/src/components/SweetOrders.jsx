import React, { useEffect, useState } from "react";
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { getToken } from "../Services/AuthServices";

const SweetOrders = () => {
    const [sweetOrders, setSweetOrders] = useState([]);

    const navigate = useNavigate();

    useEffect(() => {
        const fetchSweetOrders = async () => {
            try {
                const response = await axios.get(`http://localhost:7000/api/sweetorders`, {
                    headers: {
                        "Authorization": "Bearer " + getToken()
                    }
                });
                setSweetOrders(response.data);
            } catch (error) {
                console.error('Error fetching user:', error);
            }
        };
        fetchSweetOrders();
    }, []);

    const handleNavigateBackToAdmin = () => {
        navigate('/admin');
    }

    return (
        <div className="container">
            <h2 className='header'>Sweet Orders</h2>
            <table>
                <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Order Date</th>
                        <th>Bill Amount</th>
                    </tr>
                </thead>
                <tbody>
                    {sweetOrders.map(order => (
                        <tr
                            key={order.sweetOrderId}
                        >
                            <td>{order.sweetOrderId}</td>
                            <td>{order.orderDate}</td>
                            <td>₹{order.totalCost}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
            <button className="button" onClick={handleNavigateBackToAdmin}>Back</button>
        </div>
    )
}

export default SweetOrders;
