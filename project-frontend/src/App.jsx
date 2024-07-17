import { useState, useEffect } from 'react';
import './App.css';
import { BrowserRouter as Router, Route, Routes, useLocation } from 'react-router-dom';
import Home from './components/Home';
import Register from './components/Register';
import Login from './components/Login';
import NavBar from './components/NavBar';
import { isUserLoggedIn, getLoggedInUser } from './Services/AuthServices';
import Product from './components/Product';
import Checkout from './components/Checkout';
import AdminDashboard from './components/AdminDashboard';
import ProductList from './components/ProductList';
import SweetOrders from './components/SweetOrders';
import UserDetails from './components/UserDetails';
import UserList from './components/UserList';
import Payment from './components/Payment';
import OrderBill from './components/OrderBill';
import Profile from './components/Profile';

function App() {
    const [isLoggedIn, setIsLoggedIn] = useState(isUserLoggedIn());
    const [user, setUser] = useState(null);

    useEffect(() => {
        if (isLoggedIn) {
            setUser(getLoggedInUser());
        } else {
            setUser(null);
        }
    }, [isLoggedIn]);

    return (
        <Router>
            <ConditionalNavBar user={user} isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} setUser={setUser} />
            <Routes>
                <Route path="/" element={<Home user={user} setUser={setUser} />} />
                <Route path="/register" element={<Register />} />
                <Route path="/login" element={<Login user={user} setUser={setUser} setIsLoggedIn={setIsLoggedIn} />} />
                <Route path="/product/:productId" element={<Product user={user} setUser={setUser}/>} />
                <Route path="/checkout" element={<Checkout user={user} />} />
                <Route path="/admin" element={<AdminDashboard />} />
                <Route path="/products" element={<ProductList />} />
                <Route path="/sweetorders" element={<SweetOrders />} />
                <Route path="/userdetails/:userId" element={<UserDetails />} />
                <Route path="/users" element={<UserList />} />
                <Route path="/payment" element={<Payment user={user} setUser={setUser}/>} />
                <Route path="/order_placed" element={<OrderBill />} />
                <Route path="/profile" element={<Profile />} />
            </Routes>
        </Router>
    );
}

function ConditionalNavBar({ user, isLoggedIn, setIsLoggedIn, setUser }) {
    const location = useLocation();
    const hideNavBar = location.pathname === '/login' || location.pathname === '/register';

    if (hideNavBar) return null;

    return <NavBar user={user} isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} setUser={setUser} />;
}

export default App;
