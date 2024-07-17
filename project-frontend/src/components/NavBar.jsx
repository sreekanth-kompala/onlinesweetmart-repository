import { useNavigate } from "react-router-dom";
import { logout } from "../Services/AuthServices";
import { Typography, Button, Badge, TextField, Avatar, Menu, MenuItem } from "@mui/material";
import { useState } from "react";
import Cart from "./Cart";
import axios from "axios";
 
const NavBar = ({ user, isLoggedIn, setIsLoggedIn, setUser }) => {
 
    // State hooks for managing various component states
    const [isCartOpen, setIsCartOpen] = useState(false);
    const [searchQuery, setSearchQuery] = useState("");
    const [searchResults, setSearchResults] = useState([]);
    const [anchorEl, setAnchorEl] = useState(null);
    const [cartRef, setCartRef] = useState();
 
    // Hook to programmatically navigate
    const navigate = useNavigate();
 
    // Handle opening of the user menu
    const handleMenuOpen = (event) => {
        setAnchorEl(event.currentTarget);
    };
 
    // Handle closing of the user menu
    const handleMenuClose = () => {
        setAnchorEl(null);
    };
 
    // Handle user logout
    const handleLogOut = () => {
        logout();
        setIsLoggedIn(false);
    };
 
    // Toggle the cart drawer
    const toggleCart = () => {
        setIsCartOpen(!isCartOpen);
        if (!isCartOpen && cartRef.current) {
            cartRef.current.scrollTop = 0;
        }
    };
 
    // Handle search input and fetch search results
    const handleSearch = async (query) => {
        try {
            const response = await axios.get(`http://localhost:7000/api/products/productName/${query}`);
            setSearchResults(response.data);
            console.log("Search results:", response.data);
        } catch (error) {
            console.error("Error fetching search results:", error);
        }
    };
 
    // Handle input changes in the search field
    const handleInputChange = (e) => {
        const query = e.target.value;
        setSearchQuery(query);
        if (query) {
            handleSearch(query);
        } else {
            setSearchResults([]);
        }
    };

    // Handle search result item click
    const handleSearchResultClick = (productId) => {
        navigate(`/product/${productId}`);
        setSearchResults([]); // Hide search results
    };
 
    return (
        <>
            <div className="navbar">
                {/* Logo */}
                <img
                    onClick={() => navigate("/")}
                    className="logo"
                    style={{ height: "60px", width: "auto" }}
                    src="/images/logo/logo1.svg"
                    alt="Logo"
                />
                {/* Search Bar */}
                <div>
                    <TextField
                        style={{ width: "400px" }}
                        size="small"
                        type="text"
                        value={searchQuery}
                        onChange={handleInputChange}
                        placeholder="Search for sweets..."
                        InputProps={{ style: { borderRadius: "50px" } }}
                    />
                </div>
                {/* User options */}
                {user && isLoggedIn ? (
                    <div style={{
                        minWidth: "12%",
                        display: "flex",
                        justifyContent: "space-between",
                        alignItems: "center"
                    }}>
                        {user.roles[0].name === "ROLE_ADMIN" ? (
                            <span style={{
                                display: "flex",
                                justifyContent: "center",
                                alignItems: "center"
                            }}>
                                <button
                                    style={{ marginRight: "30px" }}
                                    className="button"
                                    variant="contained"
                                    size="large"
                                    onClick={() => navigate("/admin")}
                                >
                                    Admin Dashboard
                                </button>
                            </span>
                        ) : (
                            <Badge badgeContent={user.cart.productCount} color="secondary">
                                <Typography className="option-button cart-button" onClick={toggleCart}>🛒</Typography>
                            </Badge>
                        )}
                        <Avatar src="/broken-image.jpg" onClick={handleMenuOpen} style={{ cursor: 'pointer' }} />
                        <Menu
                            anchorEl={anchorEl}
                            open={Boolean(anchorEl)}
                            onClose={handleMenuClose}
                        >
                            <MenuItem onClick={() => { navigate("/profile"); handleMenuClose(); }}>Profile</MenuItem>
                            <MenuItem onClick={() => { handleLogOut(); handleMenuClose(); }}>Logout</MenuItem>
                        </Menu>
                    </div>
                ) : (
                    <div>
                        <Button className="button" variant="contained" size="large" onClick={() => navigate("/login")}>Login</Button>
                    </div>
                )}
            </div>
            {/* Cart Drawer */}
            <div className={`cart-drawer ${isCartOpen ? 'open' : ''}`}>
                <Button className="close-button" onClick={toggleCart}>X</Button>
                <Cart user={user} setUser={setUser} toggleCart={toggleCart}></Cart>
            </div>
            {/* Search Results */}
            {searchResults.length > 0 && (
                <div style={{
                    position: 'fixed',
                    top: '100px',
                    left: '50%',
                    transform: 'translateX(-50%)',
                    backgroundColor: 'white',
                    border: '1px solid #ccc',
                    zIndex: 1000,
                    width: '600px',
                    maxHeight: '300px',
                    overflowY: 'auto',
                    transition: "right 0.3s ease-in-out",
                    boxShadow: "rgba(0, 0, 0, 0.3) 0px 10px 15px -3px, rgba(0, 0, 0, 0.05) 0px 4px 6px -2px",
                    borderRadius: "10px",
                    padding: "20px"
                }}>
                    {searchResults.map((product) => (
                        <div className="search-item" style={{
                            display: "flex",
                            alignItems: "center",
                            borderRadius: "5px",
                            margin: "5px"
                        }} key={product.productId} onClick={() => handleSearchResultClick(product.productId)}>
                            <img style={{
                                width: "80px",
                                height: "80px",
                                objectFit: "cover",
                                borderRadius: "10px",
                                margin: "10px"
                            }} src={product.photoPath} alt={product.name} />
                            <Typography>{product.name}</Typography>
                        </div>
                    ))}
                </div>
            )}
        </>
    );
};
 
export default NavBar;