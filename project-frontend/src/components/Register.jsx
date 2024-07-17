import { useState } from "react";
import { registerAPICall } from "../Services/AuthServices";
import { TextField, Typography, Button } from "@mui/material";
import { useNavigate } from "react-router-dom";

const Register = () => {


  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: '',
    confirmPassword: ''
  });

  const [errorData, setErrorData] = useState({});

  const [successMessage, setSuccessMessage] = useState("");

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value
    });
  };

  const handleSubmit = (e) => {

    e.preventDefault();

    registerAPICall(formData).then((response) => {
      console.log(response.data);
      setSuccessMessage(response.data);
    }).catch((error) => {
      console.log(error);
      setErrorData(error.response.data)
    })

  };

  return (
    <div className="register-container" style={{ display: "flex", justifyContent: "center", alignItems: "center", minHeight: "100vh" }}>
      <div className="register-div" style={{ textAlign: "center", paddingTop : "20px", paddingBottom : "20px" }}>
        <img style={{ height: "90px", width: "auto" }} src="/images/logo/logo1.svg" alt="" />
        <br />
        <br />
        <form style={{ width: "300px", margin: "0 auto" }} onSubmit={handleSubmit}>

          <TextField
            size="small"
            variant="outlined"
            type="text"
            id="username"
            name="username"
            placeholder="username"
            value={formData.username}
            onChange={handleChange}
            style={{ marginBottom: "10px", width: "100%" }}
          />
          <Typography variant="body2" style={{ color: "#cc0202", minHeight: "20px" }}>{errorData.username ? (<span>{errorData.username}*</span>) : null}</Typography>

          <TextField
            size="small"
            variant="outlined"
            placeholder="email"
            type="email"
            id="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            style={{ marginBottom: "10px", width: "100%" }}
          />
          <Typography style={{
            minHeight: "20px",
            color: "#Cc0202"
          }} className="sub-text">{errorData.email ? (<span>{errorData.email}*</span>) : " "}</Typography>
          <TextField
            size="small"
            variant="outlined"
            placeholder="password"
            type="password"
            id="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
            style={{ marginBottom: "10px", width: "100%" }}
          />
          <Typography variant="body2" style={{ color: "#cc0202", minHeight: "20px" }}>{errorData.password ? (<span>{errorData.password}*</span>) : " "}</Typography>
          <TextField
            size="small"
            variant="outlined"
            placeholder="confirm password"
            type="password"
            id="confirmPassword"
            name="confirmPassword"
            value={formData.confirmPassword}
            onChange={handleChange}
            style={{ marginBottom: "10px", width: "100%" }}
          />
          <Typography variant="body2" style={{ color: "#cc0202", minHeight: "20px" }}>{errorData.confirmPassword ? (<span>{errorData.confirmPassword}*</span>) : " "}</Typography>
          <Typography variant="body2" style={{ color: "#cc0202", minHeight: "20px" }}>{errorData.message ? (<span>{errorData.message}*</span>) : " "}</Typography>
          <Button
            variant="contained"
            size="large"
            style={{ background: "#88070a", color: "#fff", marginBottom: "10px", width: "100%" }} onClick={e => { handleSubmit(e) }}>Register</Button>
          <Typography variant="body2" style={{ color: "#4BB543", minHeight: "20px" }}>{successMessage ? (<span>{successMessage}*</span>) : " "}</Typography>
          <br />
          <Typography variant="body2">or <span className="sub-text-link secondary-text" onClick={() => { navigate("/login") }}>login</span></Typography>
        </form>

      </div>
    </div>
  );
};

export default Register;