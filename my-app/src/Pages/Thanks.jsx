import React from 'react';
import { useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import Header from "../Components/Header";

function Thanks() {
    const location = useLocation();
    const { login } = location.state || {};


    const navigate = useNavigate();
 
    const switchPage = () => {
        // Assuming navigate function supports passing state/params in the second argument
        navigate("/home", {state: {login:login}});
      };

    const loginState = "Log Out"
    return (
        <div id="page">
            <div id="header">
                <Header loginState={loginState}/>
            </div>
            <div style={{ height: "100%", display: "flex", flexDirection: "column", justifyContent: "center", alignItems: "center"}}>
                <div>
                    <h1>Thank you for your booking!</h1>
                </div>
                <div>
                    <button type="button" id="submit-button" onClick={() => switchPage()}>
                            Home
                    </button>
                </div>                    
            </div>
        </div>
      );
}

export default Thanks;