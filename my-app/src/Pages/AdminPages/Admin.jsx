import React from 'react';
import Header from "../../Components/Header"
import { useNavigate } from 'react-router-dom';
import '../../Styles/Admin.css'
import '../../Styles/Seats.css'
import Button from '@mui/material/Button';


function Admin () {
    const loginState = 'Log Out';
    const navigate = useNavigate();

    const goToRegUsers = () => {
        navigate('/admin/users');
    }

    const goToAircrafts = () => {
        navigate('/admin/aircraft');
    }

    const goToCrew = () => {
        navigate('/admin/crew');
    }

    const goToFlights = () => {
        navigate('/admin/flight');
    }
    

    return (
        <div id='page'>
            <div id='header'>
                <Header loginState={ loginState }></Header>
            </div>

            <div id='buttons'>
                <Button id="button" variant="contained" onClick={ goToCrew }>Browse Crew by Flight</Button>
                <Button id="button" variant="contained" onClick={ goToAircrafts }>Browse Company Aircrafts</Button>
                <Button id="button" variant="contained" onClick={ goToFlights }>Add/Remove/Browse Flight Information</Button>
                <Button id="button" variant="contained" onClick={ goToRegUsers }>View Registered Users</Button>
            </div>
        </div>

    )
}

export default Admin;