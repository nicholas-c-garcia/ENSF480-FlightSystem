import React from 'react';
import Header from "../../Components/Header"
import Flight from "../../Components/Flight"
import { useState, useEffect } from 'react';
import '../../Styles/Admin.css'
import '../../Styles/Seats.css'
import axios from 'axios'
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';

function AdminFlight() {

    const loginState = 'Log Out';
    const[flights, setFlights] = useState([]);
    const[flightId, setFlightID] = useState('');
    const[aircraftID, setAircraftID] = useState('');
    const[origin, setOrigin] = useState('');
    const[destination, setDestination] = useState('');
    const[departureDate, setDepartureDate] = useState('');
    const[departureTime, setDepartureTime] = useState('');

    const getFlights = () => {
        axios
            .get(`http://localhost:8080/getFlightsByDate/${departureDate}`)
            .then((res) => {
                setFlights(res.data?res.data:[]);
            })
            console.log(flights);
    }

    const deleteFlight = () => {
        try {
            axios
                .delete(`http://localhost:8080/deleteFlight/${flightId}`)
        } catch {

        }
    }

    const addFlight = (e) => {
        e.preventDefault();
        const flight = { aircraftID, origin, destination, departureDate, departureTime };
        
        fetch(`http://localhost:8080/addFlight/${aircraftID}`, {
            method:"POST",
            headers:{"Content-Type":"application/json"},
            body:JSON.stringify(flight)
        })
        .then(()=>{
            console.log("New Flight Added");
            window.location.reload(false);
        })
    }

    return (
        <div id='page'>
        <div id='header'>
            <Header loginState={ loginState }></Header>
        </div>

        <div id='boxF'>
            <div id='half'>
                <div id='browseFlight'>
                    <h1>Browse Flights By Date</h1>
                    <TextField id='outlined-basic' label='Flight Date' variant='outlined' onChange={(e) => setDepartureDate(e.target.value)}></TextField>
                    <Button id="button" variant="contained" onClick={ getFlights }>Browse</Button> 
                </div>  
                <div id='flightViewer'>
                    <h1>Flights</h1>
                    {flights.map((flight) => (
                        <Flight flight={flight} key={flight.flightId}/>
                    ))}
                </div>
            </div>
            <div id='half'>
                <div id='addFlight'>
                    <h1>Add Flight</h1>
                    <TextField id='outlined-basic' label='Aircraft ID' variant='outlined' onChange={(e) => setAircraftID(e.target.value)}> </TextField>
                    <TextField id='outlined-basic' label='Origin' variant='outlined' onChange={(e) => setOrigin(e.target.value)}></TextField>
                    <TextField id='outlined-basic' label='Destination' variant='outlined' onChange={(e) => setDestination(e.target.value)}></TextField>
                    <TextField id='outlined-basic' label='Departure Date' variant='outlined' onChange={(e) => setDepartureDate(e.target.value)}></TextField>
                    <TextField id='outlined-basic' label='Departure Time' variant='outlined' onChange={(e) => setDepartureTime(e.target.value)}></TextField>
                    <Button id="button" variant="contained" onClick={ addFlight }>Add Flight</Button>
                </div>
                <div id='removeFlight'>
                    <h1>Remove Flight By ID</h1>
                    <TextField id='outlined-basic' label='Flight ID' variant='outlined' onChange={(e) => setFlightID(e.target.value)}></TextField>
                    <Button id="button" variant="contained" onClick={ deleteFlight }>Remove Flight</Button>  
                </div>
            </div>

        </div>
    </div>
    )
}

export default AdminFlight;