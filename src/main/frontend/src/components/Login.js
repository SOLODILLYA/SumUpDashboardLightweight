import React from "react";
import "./Login.css";

const LoginPage = () => {
  const handleLogin = () => {
    window.location.href = "https://web.sumup.com/login";
  };

  return (
    <div className="login-page">
      <div className="login-card">
        <h1 className="login-title">SumUp Dashboard</h1>
        <button className="login-button" onClick={handleLogin}>
          Login using SumUp
        </button>
      </div>
    </div>
  );
};

export default LoginPage;
