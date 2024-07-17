import { Typography, Link, Box } from "@mui/material";

const Footer = () => {
    return (
        <div style={{
            backgroundColor: "#88070A",
            color: "#ffffff",
            padding: "40px 20px",
            textAlign: "center"
        }}>
            {/* <Typography variant="h5" style={{ marginBottom: "20px" }}>Featured In</Typography> */}

            <div style={{ display: "flex", justifyContent: "center", maxWidth: "1200px", margin: "0 auto" }}>
                <div style={{ flex: 1, maxWidth: "300px", margin: "0 20px" }}>
                    <Typography variant="h6" style={{ marginBottom: "10px" }}>Quick Links</Typography>
                    <Link href="/" style={styles.link}>Home</Link><br />
                    <Link href="/" style={styles.link}>Products</Link><br />
                </div>
                <div style={{ flex: 1, maxWidth: "300px", margin: "0 20px" }}>
                    <Typography variant="h6" style={{ marginBottom: "10px" }}>Contact</Typography>
                    <Typography variant="body2">Email: <Link href="mailto:supportvignesh@sweetmart.com" style={styles.link}>supportvignesh@sweetmart.com</Link></Typography>
                    <Typography variant="body2">Phone: <Link href="tel:+917339221045" style={styles.link}>+91-7339221045</Link></Typography>
                </div>
                <div style={{ flex: 1, maxWidth: "300px", margin: "0 20px" }}>
                    <Typography variant="h6" style={{ marginBottom: "10px" }}>Follow Us</Typography>
                    <Link href="https://www.facebook.com" target="_blank" style={styles.link}>Facebook</Link><br />
                    <Link href="https://www.twitter.com" target="_blank" style={styles.link}>Twitter</Link><br />
                    <Link href="https://www.instagram.com" target="_blank" style={styles.link}>Instagram</Link>
                </div>
            </div>
            <div style={{ margin: "20px 0" }}>
                <Typography variant="h6" style={{ marginBottom: "10px" }}>About Us</Typography>
                <Typography variant="body2">We are a leading online sweet mart bringing you the best quality sweets from around the world.</Typography>
            </div>
            <Typography variant="body2">© 2024 Mittai Kadai. All Rights Reserved.</Typography>
        </div>
    );
}

const styles = {
    link: {
        color: "#ffffff",
        marginBottom: "5px"
    }
};

export default Footer;
