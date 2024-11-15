import React, { useState } from "react";
import "../styles/LoginRegistration.css";
import Register from "../Components/Login/Register";
import Login from "../Components/Login/Login";

function LoginRegistration() {
  const [isSignUp, setIsSignUp] = useState(false);

  const toggleSignUp = () => setIsSignUp(true);
  const toggleLogin = () => setIsSignUp(false);

  return (
    <div className="login-registration">
      <h2 className="websitename-login">SPEEDY GO</h2>
      <div id="page" className={`site ${isSignUp ? "signup-show" : "signin-show"}`}>
        <div className="main-container">
          <div className="theform">
            <div className="play">
              <div className="wrapper">
                <div className="card one"></div>
                <div className="card two"></div>
                <div className="card three"></div>
                <div className="card four"></div>
                <div className="card five"></div>
              </div>
            </div>
            <Register toggleLogin={toggleLogin} />

            <Login toggleSignUp={toggleSignUp} />
          </div>
        </div>
      </div>

    </div>
  );
}

export default LoginRegistration;
