import { useEffect, useState } from "react";
import {
    getCategoriesAPICall,
    getProductsAPICall,
    addProductToCartAPICall,
} from "../Services/SweetMartService";
import {
    getProductByCategoryAPICall,
    getUserByUsernameOrEmailAPICall,
} from "../Services/SweetMartService";
import { useNavigate } from "react-router-dom";
import Cart from "./Cart";
import { getToken } from "../Services/AuthServices";
import { saveLoggedInUser } from "../Services/AuthServices";
import { Typography, Button, Grid } from "@mui/material";
import Banner from "./Banner";
import Featured from "./Footer";
import NavBar from "./NavBar";
import { Carousel } from "react-responsive-carousel";
import "react-responsive-carousel/lib/styles/carousel.min.css";

export default function Home({ user, setUser }) {
    const [categories, setCategories] = useState([]);
    const [products, setProducts] = useState([]);
    const navigate = useNavigate();

    const fetchCategories = () => {
        getCategoriesAPICall()
            .then((response) => {
                setCategories(response.data);
            })
            .catch((error) => {
                console.log(error);
            });
    };

    const fetchProducts = () => {
        getProductsAPICall()
            .then((response) => {
                setProducts(response.data);
            })
            .catch((error) => console.log(error));
    };

    const fetchProductByCategory = (categoryId) => {
        getProductByCategoryAPICall(categoryId)
            .then((response) => {
                setProducts(response.data);
            })
            .catch((error) => {
                console.log(error);
            });
    };

    const handleClick = (categoryId) => {
        fetchProductByCategory(categoryId);
    };

    useEffect(() => {
        fetchCategories();
        fetchProducts();
    }, []);

    const handleClickAddProduct = (productId) => {
        if (!user) {
            navigate("/login");
        }
        const accessToken = getToken();
        addProductToCartAPICall(user.cart.cartId, productId, accessToken)
            .then((response) => {
                console.log(response);
                getUserByUsernameOrEmailAPICall(user.username, getToken()).then(
                    (response) => {
                        saveLoggedInUser(response.data);
                        setUser(response.data);
                    }
                );
            })
            .catch((error) => {
                console.log(error);
            });
    };

    return (
        <>
            <div
                className=""
                style={{
                    background: "#F4EEE3",
                }}
            >
                <div
                    style={{
                        width: "auto",
                    }}
                >
                    <Carousel interval={5000} showThumbs={false} autoPlay={true} showStatus={false} infiniteLoop>
                        <div>
                            <img src="images/banner/2.svg" alt="" />
                        </div>
                        <div>
                            <img src="images/banner/1.svg" alt="" />
                        </div>
                        <div>
                            <img src="images/banner/3.svg" alt="" />
                        </div>
                    </Carousel>
                </div>
                <Banner></Banner>
                <br />
                <div
                    style={{
                        paddingRight: "3%",
                        paddingLeft: "3%",
                    }}
                >
                    <br />
                    <Typography variant="h4">Sweets & Savouries</Typography>
                    <Typography variant="h6">Savor the sweetness of family</Typography>
                    <br />
                    <Button
                        style={{
                            marginRight : "5px"
                        }}
                        className="button"
                        size="large"
                        variant="contained"
                        onClick={(e) => {
                            fetchProducts();
                        }}
                    >
                        All
                    </Button>{" "}
                    {categories.map((category) => {
                        return (
                            <>
                                <Button
                                    style={{
                                        marginRight : "5px"
                                    }}
                                    className="button"
                                    size="large"
                                    variant="contained"
                                    key={category.categoryId}
                                    onClick={(e) => {
                                        handleClick(category.categoryId);
                                    }}
                                >
                                    {category.name}
                                </Button>{" "}
                            </>
                        );
                    })}
                    <br />
                    <br />
                    <br />
                    <Grid container className="standard-gridcontainer" spacing={2}>
                        {products.map((product) => {
                            return (
                                <Grid
                                    item
                                    lg={3}
                                    key={product.productId}
                                    className="standard-griditem"
                                    style={{
                                        padding: "10px",
                                        display: "flex",
                                        flexDirection: "column",
                                        alignItems: "center",
                                    }}
                                >
                                    <div onClick={() => {navigate(`product/${product.productId}`)}}
                                        className="photobox"
                                        style={{
                                            borderRadius: "10px",
                                            overflow: "hidden",
                                            height: "200px",
                                            width: "100%",
                                            display: "flex",
                                            justifyContent: "center",
                                            alignItems: "center",
                                        }}
                                    >
                                        <img
                                            className="home-image"
                                            style={{
                                                maxHeight: "100%",
                                                maxWidth: "100%",
                                                borderRadius : "10px"
                                            }}
                                            src={product.photoPath}
                                            alt=""
                                        />
                                    </div>
                                    <Typography
                                        style={{
                                            textTransform: "capitalize",
                                            textAlign: "center",
                                            marginTop: "10px",
                                            height: "40px",
                                            overflow: "hidden",
                                            textOverflow: "ellipsis",
                                            whiteSpace: "nowrap",
                                        }}
                                        variant="h6"
                                    >
                                        {product.name}
                                    </Typography>
                                    <Typography
                                        style={{
                                            color: "#88070a",
                                        }}
                                    >
                                        <strong>₹{product.price}</strong>{" "}<span style={{ color : "grey", fontSize : "11px" }}>/ 500g</span>
                                    </Typography>
                                    {user && user.roles[0].name !== "ROLE_ADMIN" ? (
                                        <Button
                                            style={{
                                                width: "100%",
                                                marginTop: "auto",
                                            }}
                                            className="button"
                                            variant="contained"
                                            size="large"
                                            onClick={() => {
                                                handleClickAddProduct(product.productId);
                                            }}
                                        >
                                            Add to cart
                                        </Button>
                                    ) : null}
                                </Grid>
                            );
                        })}
                    </Grid>
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                </div>
            </div>
            <Featured></Featured>
        </>
    );
}
