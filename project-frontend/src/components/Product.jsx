import { useEffect, useState } from "react";
import { getProductAPICall } from "../Services/SweetMartService";
import { useParams } from "react-router-dom";
import { Button, CircularProgress, Typography, Container, Box } from "@mui/material";
import { getToken } from "../Services/AuthServices";
import { saveLoggedInUser } from "../Services/AuthServices";
import { getUserByUsernameOrEmailAPICall } from "../Services/SweetMartService";
import { addProductToCartAPICall } from "../Services/SweetMartService";

const Product = ({ user, setUser }) => {
    const [product, setProduct] = useState(null);
    const { productId } = useParams();

    useEffect(() => {
        getProductAPICall(productId)
            .then((response) => {
                setProduct(response.data);
            })
            .catch((error) => {
                console.log(error);
            });
    }, [productId]);

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

    if (!product) {
        return (
            <Container>
                <Box display="flex" justifyContent="center" alignItems="center" height="100vh">
                    <CircularProgress />
                </Box>
            </Container>
        );
    }

    return (
        <Container maxWidth="md">
            <Box display="flex" flexDirection={{ xs: "column", md: "row" }} my={4}>
                <Box flex={1} mr={{ md: 2 }}>
                    <img
                        style={{
                            width: "100%",
                            borderRadius: "10px",
                            objectFit: "cover"
                        }}
                        src={product.photoPath}
                        alt={product.name}
                    />
                </Box>
                <Box flex={1} mt={{ xs: 4, md: 0 }}>


                    <Typography style={{textTransform : "capitalize"}} variant="h4" gutterBottom>
                        {product.name}
                    </Typography>
                    <Typography variant="h6" color="textSecondary" gutterBottom>
                        ₹{product.price} <span style={{ fontSize: "13px" }}>/ 500g</span>
                    </Typography>
                    <Typography variant="body1" paragraph>
                        {product.description}
                    </Typography>
                    <Button onClick={() => { handleClickAddProduct(product.productId) }} className="button" variant="contained">
                        Add to cart
                    </Button>
                </Box>
            </Box>
        </Container>
    );
};

export default Product;
