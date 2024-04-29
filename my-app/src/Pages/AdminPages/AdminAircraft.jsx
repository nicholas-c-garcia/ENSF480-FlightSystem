import React from 'react';
import Header from "../../Components/Header"
import { useState, useEffect } from 'react';
import Aircraft from '../../Components/Aircraft'
import '../../Styles/Admin.css'
import '../../Styles/Seats.css'
import axios from 'axios'
import Container from '@mui/material/Container';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';


function AdminAircraft() {

    const loginState = 'Log Out';
    const[aircrafts, setAircrafts] = useState([]);
    const[aircraftName, setAircraftName] = useState('');
    const[aircraftId, setAircraftID] = useState('');


    const addAircraft = (e) => {
        e.preventDefault();
        const aircraft = { aircraftName };

        fetch('http://localhost:8080/addAircraft', {
            method:"POST",
            headers:{"Content-Type":"application/json"},
            body:JSON.stringify(aircraft)
        })
        .then(()=>{
            console.log("New Aircraft Added");
            window.location.reload(false);
        })
    }

    const deleteAircraft = () => {
        axios
            .delete(`http://localhost:8080/removeAircraftById/${aircraftId}`)
            .then(()=>{
                window.location.reload(false);
            })
    }

    useEffect(() => {
        axios
            .get('http://localhost:8080/getAircrafts')
            .then((res) => {
                setAircrafts(res.data ? res.data : []);
            })
            console.log(aircrafts);
    }, []);

    return (
        <div id='page'>
            <div id='header'>
                <Header loginState={ loginState }></Header>
            </div>

            <div id='boxA'>
            <h1>Add/Remove Aircrafts</h1>
                <div id='flexChild'>
                    <TextField id='outlined-basic' label='Aircraft Name' variant='outlined' value={aircraftName} onChange={(e) => setAircraftName(e.target.value)}></TextField><br></br>
                    <Button id="button" variant="contained" onClick={ addAircraft }>Add Aircraft</Button><br></br>
                    <TextField id='outlined-basic' label='Aircraft ID' variant='outlined' value={aircraftId} onChange={(e) => setAircraftID(e.target.value)}></TextField><br></br>
                    <Button id="button" variant="contained" onClick={ deleteAircraft }>Remove Aircraft</Button>
                </div>
                <h1>Aircrafts</h1>
                <div id='flexChild'>
                    <Container>
                        {aircrafts.map((aircraft) => (
                            <Aircraft aircraft={aircraft} key={aircraft.aircraftId}/>
                        ))}
                    </Container>
                </div>
            </div>
        </div>
    )
}

export default AdminAircraft;