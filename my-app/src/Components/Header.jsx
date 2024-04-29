import React from 'react';
import '../App.css';

import { useNavigate } from 'react-router-dom';

function Header({ loginState } ) {

  const navigate = useNavigate();

  const switchPage = () => {
    if (loginState === "Log In") {
        navigate('/login');
    } else if (loginState === "Log Out") {
        navigate('/');
    }
  };
  
  return (
    <div id="page">
        <div id="header">
            <h1 style={{paddingBottom: '40px', paddingLeft: "20px", color:"white"}}>Ctrl Alt Air✈️</h1>
            {loginState && <button type='button' id="login-button" onClick={ switchPage }>{loginState}</button>}
        </div>
    </div>
)
}

export default Header;