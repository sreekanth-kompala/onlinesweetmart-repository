import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { getToken } from '../Services/AuthServices';

const UserList = () => {
    const [users, setUsers] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        // Function to fetch users from the API
        const fetchUsers = async () => {
            try {
                const response = await axios.get("http://localhost:7000/api/users", {
                    headers : {
                        "Authorization" : "Bearer " + getToken()
                    }
                });
                // Update state with the fetched users
                setUsers(response.data);
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        };

        // Fetch users when the component mounts
        fetchUsers();
    }, []);

    // Navigate to user details page
    const handleNavigation = (userId) => {
        navigate(`/userdetails/${userId}`);
    }

    // Navigate back to the admin page
    const handleNavigateBack = () => {
        navigate('/admin');
    }

    const styles = {
        container: {
            padding: '20px',
            fontFamily: 'Arial, sans-serif',
            textAlign: 'center',
        },
        table: {
            width: '100%',
            borderCollapse: 'collapse',
            marginTop: '20px',
            boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',
        },
        th: {
            backgroundColor: '#f8f8f8',
            padding: '15px',
            borderBottom: '2px solid #ddd',
            fontSize: '18px',
            fontWeight: 'bold',
        },
        td: {
            padding: '15px',
            borderBottom: '1px solid #ddd',
            fontSize: '16px',
        },
        tr: {
            backgroundColor: '#fff',
        },
        trHover: {
            backgroundColor: '#f1f1f1',
        },
        button: {
            marginTop: '20px',
            padding: '10px 20px',
            border: 'none',
            borderRadius: '5px',
            backgroundColor: '#007BFF',
            color: '#fff',
            cursor: 'pointer',
            fontSize: '16px',
        },
        detailsButton: {
            padding: '5px 10px',
            border: 'none',
            borderRadius: '3px',
            backgroundColor: '#28a745',
            color: '#fff',
            cursor: 'pointer',
            fontSize: '14px',
        },
        buttonContainer: {
            marginTop: '20px',
            display: 'flex',
            justifyContent: 'center',
        }
    };

    // Display loading state if users data is not yet available
    if(!users) {
        return(
            <div>Loading...</div>
        )
    }

    return (
        <div style={styles.container}>
            <h2>Users List</h2>
            <table style={styles.table}>
                <thead>
                    <tr>
                        <th style={styles.th}>User Id</th>
                        <th style={styles.th}>User Name</th>
                        <th style={styles.th}>More Info</th>
                    </tr>
                </thead>
                <tbody>
                    {users.map(user => (
                        <tr
                            key={user.userId}
                            style={styles.tr}
                            onMouseOver={(e) => e.currentTarget.style.backgroundColor = styles.trHover.backgroundColor}
                            onMouseOut={(e) => e.currentTarget.style.backgroundColor = styles.tr.backgroundColor}
                        >
                            <td style={styles.td}>{user.userId}</td>
                            <td style={styles.td}>{user.username}</td>
                            <td style={styles.td}>
                                <button
                                    style={styles.detailsButton}
                                    onClick={() => handleNavigation(user.userId)}
                                >
                                    Details
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
            <div style={styles.buttonContainer}>
                <button style={styles.button} onClick={handleNavigateBack}>Back</button>
            </div>
        </div>
    );
};

export default UserList;
