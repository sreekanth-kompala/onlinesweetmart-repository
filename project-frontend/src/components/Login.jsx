import { useState } from "react";
import { loginAPICall, storeToken, saveLoggedInUser } from "../Services/AuthServices";
import { getUserByUsernameOrEmailAPICall } from "../Services/SweetMartService";
import { useNavigate } from "react-router-dom";
import { TextField, Typography, Button } from "@mui/material";

const Login = ({ setUser, setIsLoggedIn }) => {

  const navigate = useNavigate();

  const [errorData, setErrorData] = useState({});

  const [formData, setFormData] = useState({
    usernameOrEmail: '',
    password: ''
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    loginAPICall(formData)
      .then((response) => {
        storeToken(response.data.accessToken);
        getUserByUsernameOrEmailAPICall(formData.usernameOrEmail, response.data.accessToken)
          .then((response) => {
            saveLoggedInUser(response.data);
            setUser(response.data);
            setIsLoggedIn(true);
            navigate("/");
          })
          .catch((error) => {
            console.log(error);
          });
      })
      .catch((error) => {
        console.log(error);
        setErrorData(error.response.data);
      });
  };

  return (
    <div className="register-container" style={{ display: "flex", justifyContent: "center", alignItems: "center", minHeight: "100vh" }}>
      <div className="register-div" style={{ textAlign: "center" }}>
        <img style={{ height: "90px", width: "auto" }} src="/images/logo/logo1.svg" alt="" />
        {/* <Typography variant="h5" style={{ marginBottom: "20px" }}>Login</Typography> */}
        <br />
        <br />
        <form style={{ width: "300px", margin: "0 auto" }} onSubmit={handleSubmit}>
          <TextField
            size="small"
            variant="outlined"
            placeholder="Username or Email"
            type="text"
            id="usernameOrEmail"
            name="usernameOrEmail"
            value={formData.usernameOrEmail}
            onChange={handleChange}
            style={{ marginBottom: "10px", width: "100%" }}
          />
          <Typography variant="body2" style={{ color: "#cc0202", minHeight: "20px" }}>{errorData.usernameOrEmail}</Typography>
          <TextField
            size="small"
            variant="outlined"
            placeholder="Password"
            type="password"
            id="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
            style={{ marginBottom: "10px", width: "100%" }}
          />
          <Typography variant="body2" style={{ color: "#cc0202", minHeight: "20px" }}>{errorData.password}</Typography>
          <Typography variant="body2" style={{ color: "#cc0202", minHeight: "20px" }}>{errorData.message}</Typography>
          <Button
            variant="contained"
            size="large"
            style={{ background: "#88070a", color: "#fff", marginBottom: "10px", width: "100%" }}
            onClick={handleSubmit}
          >
            Login
          </Button>
          <Typography variant="body2">or <span className="sub-text-link secondary-text" onClick={() => { navigate("/register") }}>create an account</span></Typography>
        </form>
      </div>
    </div>
  );
};

export default Login;
