import React from 'react';
import { useLocation } from 'react-router-dom';

const OrderBill = () => {
    const location = useLocation();
    const { sweetOrderDetails } = location.state || {};

    if (!sweetOrderDetails) {
        // Display an error message if no order details are found
        return (
            <div style={styles.errorContainer}>
                <h2 style={styles.errorText}>Transaction Failed.</h2>
                <h2 style={styles.errorText}>No order details found.</h2>
            </div>
        );
    }

    return (
        <div style={styles.container}>
            <h2>Transaction Successful</h2><br/>
            <h2 style={styles.heading}>Order Bill</h2>
            <div style={styles.details}>
                <p style={styles.detailItem}>Order ID: {sweetOrderDetails.sweetOrderId}</p>
                <p style={styles.detailItem}>Order Date: {sweetOrderDetails.orderDate}</p>
                <p style={styles.detailItem}>Total Amount: ₹{sweetOrderDetails.totalCost}</p>
            </div>
        </div>
    );
};

const styles = {
    container: {
        padding: '20px',
        backgroundColor: '#f9f9f9',
        borderRadius: '8px',
        maxWidth: '600px',
        margin: '20px auto',
        boxShadow: '0 0 10px rgba(0, 0, 0, 0.1)',
        textAlign: 'center',
    },
    heading: {
        fontSize: '24px',
        fontWeight: 'bold',
        marginBottom: '20px',
    },
    details: {
        textAlign: 'left',
    },
    detailItem: {
        fontSize: '18px',
        marginBottom: '10px',
    },
    errorContainer: {
        padding: '20px',
        backgroundColor: '#ffcccc',
        borderRadius: '8px',
        maxWidth: '600px',
        margin: '20px auto',
        boxShadow: '0 0 10px rgba(0, 0, 0, 0.1)',
        textAlign: 'center',
    },
    errorText: {
        fontSize: '20px',
        color: '#ff0000',
    },
};

export default OrderBill;


// import React from 'react';
// import { useLocation } from 'react-router-dom';
 
// const OrderBill = () => {
//     const location = useLocation();
//     const { sweetOrderDetails } = location.state || {};
 
//     if (!sweetOrderDetails) {
//         return (
//             <div style={styles.errorContainer}>
//                 <h2 style={styles.errorText}>Transaction Failed.</h2>
//                 <h2 style={styles.errorText}>No order details found.</h2>
//             </div>
//         );
//     }
 
//     return (
//         <div style={styles.container}>
//             <h2>Transaction Successful</h2><br/>
//             <h2 style={styles.heading}>Order Bill</h2>
//             <div style={styles.details}>
//                 <p style={styles.detailItem}>Order ID: {sweetOrderDetails.sweetOrderId}</p>
//                 <p style={styles.detailItem}>Order Date: {sweetOrderDetails.orderDate}</p>
//                 <p style={styles.detailItem}>Total Amount: ₹{sweetOrderDetails.totalCost}</p>
//             </div>
//         </div>
//     );
// };
 
// const styles = {
//     container: {
//         padding: '20px',
//         backgroundColor: '#f9f9f9',
//         borderRadius: '8px',
//         maxWidth: '600px',
//         margin: '20px auto',
//         boxShadow: '0 0 10px rgba(0, 0, 0, 0.1)',
//         textAlign: 'center',
//     },
//     heading: {
//         fontSize: '24px',
//         fontWeight: 'bold',
//         marginBottom: '20px',
//     },
//     details: {
//         textAlign: 'left',
//     },
//     detailItem: {
//         fontSize: '18px',
//         marginBottom: '10px',
//     },
//     errorContainer: {
//         padding: '20px',
//         backgroundColor: '#ffcccc',
//         borderRadius: '8px',
//         maxWidth: '600px',
//         margin: '20px auto',
//         boxShadow: '0 0 10px rgba(0, 0, 0, 0.1)',
//         textAlign: 'center',
//     },
//     errorText: {
//         fontSize: '20px',
//         color: '#ff0000',
//     },
// };
 
// export default OrderBill;