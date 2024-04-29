import React from 'react';
import Header from "../../Components/Header"
import Account from "../../Components/Account"
import { useState } from 'react';
import '../../Styles/Staff.css'
import '../../Styles/Seats.css'
import axios from 'axios'
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';


function Staff () {
    const loginState = 'Log Out';
    const [passengers, setPassengers] = useState([]);
    const [flightID, setFlightID] = useState([]);

    const getPassengers = () => {
        axios
            .get(`http://localhost:8080/getAccountsByFlight/${flightID}`)
            .then((res) => {
                setPassengers(res.data?res.data:[]);
            })
            console.log(passengers);
    }

    return (
        <div id='page'>
            <div id='header'>
                <Header loginState={ loginState }></Header>
            </div>
            <div id='box'>
                <h1>Browse Passengers By Flight</h1>
                <div id='browse'>
                    <TextField id='outlined-basic' label='FlightID' variant='outlined' onChange={(e) => setFlightID(e.target.value)}></TextField> <br></br>
                    <Button id="button" variant="contained" onClick={ getPassengers }>Browse</Button>
                </div>
                <h1>Passengers</h1>
                <div id='mapPassenger'>
                    {passengers.map((passenger) => (
                        <Account account={passenger} key={passenger.username}/>
                    ))}
                </div>
            </div>
        </div>

    )
}

export default Staff;