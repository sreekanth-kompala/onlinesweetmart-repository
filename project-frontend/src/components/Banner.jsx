import { Typography } from "@mui/material";

const Banner = () => {
    return (
        <>
            <div className="paddingLR" style={{
                display : "flex",
                justifyContent : "space-between",
                paddingTop : "30px",
                paddingBottom : "30px",
                background : "white"
            }}>
                <Typography variant="h5">🍃 High Quality</Typography>
                <Typography variant="h5">🟢 100% Vegetarian</Typography>
                <Typography variant="h5">✅ Hygienic</Typography>
            </div>
        </>
    )
}

export default Banner;