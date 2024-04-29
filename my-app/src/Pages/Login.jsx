import axios from "axios";
import React from 'react';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../Styles/Login.css';

function Login() {

  const [login, setLogin] = useState({
      username: '',
      password: '',
  });

  const{username, password} = login;
  const onInputChange=(e)=> {
    setLogin({...login, [e.target.name]:e.target.value})
  }

  const navigate = useNavigate();

  const switchPageHome = () => {
    navigate('/home', {state: {login:login}});
  };

  const switchPageSignUp = () => {
    navigate('/sign-up');
  };

  const switchAdminPage = () => {
    navigate('/admin');
  }

  const switchStaffPage = () => {
    navigate('/staff');
  }

  const checkLogin = async () => {
    const result = await axios.get(`http://localhost:8080/getAccounts`);
    var loggedStatus = false;
    for (var i = 0; i < result.data.length; i++) {
      if ((result.data[i].username === login.username) && (result.data[i].password === login.password)) {
        loggedStatus = true;
        if (result.data[i].accessLevel === "Customer" || result.data[i].accessLevel === "Registered")  {
          switchPageHome();
        } else if (result.data[i].accessLevel === "Admin") {
          switchAdminPage();
        } else if (result.data[i].accessLevel === "Agent") {
          switchStaffPage();
        }
      }
    }

    if (!loggedStatus) {
      window.alert("Incorrect Username or Password");
    }
  }

    return (
        <div id="landing-page">
    
          {/* white part */}
          <div id="white-landing">
            <h1 style={{fontWeight:'1000', fontSize:'3rem'}}>Login to your Account</h1>
            <form id="landing-form">
              <input style={{padding: '0px 15px 0px 20px'}} type="text" id = "user-info" name = "username" placeholder='Username' value = {username} onChange={(e)=>onInputChange(e)}></input>
              <input style={{padding: '0px 15px 0px 20px'}} type="password" id="user-info" name = "password" placeholder='Password' value = {password} onChange={(e)=>onInputChange(e)}></input>
              <button style={{cursor: 'pointer', alignSelf:'center'}} type='button' id="submit-button" onClick={ (e) => { checkLogin();}}>Sign In</button>
            </form>
          </div>
    
          {/* black part */}
          <div id="dark-landing">
            <h1 style={{fontWeight:'1000', fontSize:'2.5rem'}}>New Here?</h1>
            <button style={{cursor: 'pointer'}} id="signup-button" type="button" onClick={ switchPageSignUp }>Sign Up</button>
          </div>
          
        </div>
      );
}

export default Login;