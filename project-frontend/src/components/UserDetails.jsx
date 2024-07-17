import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';
import { getToken } from '../Services/AuthServices';

const UserDetails = () => {
    const { userId } = useParams();
    const [user, setUser] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        // Function to fetch user details from the API
        const fetchUser = async () => {
            try {
                const response = await axios.get(`http://localhost:7000/api/users/${userId}`, {
                    headers: {
                        "Authorization": "Bearer " + getToken()
                    }
                });
                console.log(response.data)
                // Update state with the fetched user details
                setUser(response.data);
            } catch (error) {
                console.error('Error fetching user:', error);
            }
        };
        // Fetch user details when the component mounts or userId changes
        fetchUser();
    }, [userId]);

    // Navigate back to the users list
    const handleNavigateBack = () => {
        navigate('/users');
    };

    // Display loading state if user data is not yet available
    if (!user) {
        return <div>Loading...</div>;
    }

    return (
        <div className='container'>
            <h2 className='header'>User Details</h2>
            <p className='paragraph'><strong>User ID:</strong> {user.userId}</p>
            <p className='paragraph'><strong>Username:</strong> {user.username}</p>
            <p className='paragraph'><strong>Email:</strong> {user.email}</p>

            <br/>
            <h3 className='header'>Sweet Orders</h3>
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
            <br/>
            <button className='button' onClick={handleNavigateBack}>Back</button>
        </div>
    );
};

export default UserDetails;
