import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Route, Routes } from "react-router-dom";
import './index.css';
import App from './App';
import Login from './Pages/Login';
import Signup from './Pages/Signup';
import Home from './Pages/Home';
import Payment from './Pages/Payment'
import Seats from './Pages/Seats'
import Confirmation from './Pages/Confirmation';
import Admin from './Pages/AdminPages/Admin';
import AdminRegUsers from './Pages/AdminPages/AdminRegUsers';
import AdminAircraft from './Pages/AdminPages/AdminAircraft';
import AdminFlight from './Pages/AdminPages/AdminFlight';
import AdminCrew from './Pages/AdminPages/AdminCrew';
import Staff from './Pages/StaffPages/Staff';
import reportWebVitals from './reportWebVitals';
import Thanks from './Pages/Thanks';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <BrowserRouter>
    <Routes>
      <Route path="/" element={<App />}/>
      <Route path="/home" element={<Home />}/>
      <Route path="/login" element={<Login />}/>
      <Route path="/sign-up" element={<Signup />}/>
      <Route path="/payment-details" element={<Payment />}/>
      <Route path="/seat-selection" element={<Seats />}/>
      <Route path="/confirmation" element={<Confirmation />}/>

      <Route path="/admin" element={<Admin/>}/>
      <Route path="/admin/users" element={<AdminRegUsers/>}/>
      <Route path="/admin/aircraft" element={<AdminAircraft/>}/>
      <Route path="/admin/flight" element={<AdminFlight/>}/>
      <Route path="/admin/crew" element={<AdminCrew/>}/>
      <Route path="/staff" element={<Staff/>}/>

      <Route path="/thank-you" element={<Thanks />}/>

    </Routes>
  </BrowserRouter>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
