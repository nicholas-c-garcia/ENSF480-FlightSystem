import React from 'react';
import Header from "../../Components/Header"
import axios from 'axios'
import { useState, useEffect } from 'react';
import Crew from '../../Components/Crew'
import '../../Styles/Admin.css'
import '../../Styles/Seats.css'
import Container from '@mui/material/Container';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';

function AdminCrew() {
    const loginState = 'Log Out';
    const[newCrew, setNewCrew] = useState({
        firstName: '',
        lastName: '',
        aicraftId: '',
        type: '',
    });

    const[removeCrew, setRemoveCrew] = useState({
        aircraftId: '',
        crewId: '',
    });

    const[crews, setCrews] = useState([]);

    const onInputChange=(e)=> {
        setNewCrew({...newCrew, [e.target.name]:e.target.value});
      }
    
    const onInputChangeRemove=(e)=> {
        setRemoveCrew({...removeCrew, [e.target.name]:e.target.value});
    }


    const addCrew = async (e) => {
        e.preventDefault();
        // const crew = { fName, lName, type, flightID };
        if (newCrew.aircraftId === '') {
            const sendCrew = {
                firstName: newCrew.firstName,
                lastName: newCrew.lastName,
                type: newCrew.type,
            }
            try {
                axios.post(`http://localhost:8080/addCrewMember`, sendCrew);
                window.location.reload(false);
            } catch {

            }
        } else {
            try {
                axios.post(`http://localhost:8080/addCrewMember/${newCrew.aircraftId}`, newCrew);
                window.location.reload(false);
            } catch {
            }
        }

    }

    const deleteCrew = async(e) => {
        e.preventDefault();

        try {
            if (removeCrew.aircraftId === '') {
                axios.delete(`http://localhost:8080/deleteCrewMember/${removeCrew.crewId}`)
                window.location.reload(false);
            } 
        } catch {
            console.log("Deleting gone wrong");
        }
        
    }

    useEffect(() => {
        try {
            axios
            .get('http://localhost:8080/getCrewMembers')
            .then((res) => {
                setCrews(res.data ? res.data : []);
            })
        } catch {
            console.log("Connection lost to database");
        }
    }, []);

    
    return (
        <div id='page'>
        <div id='header'>
            <Header loginState={ loginState }></Header>
        </div>

        <div id='boxC'>
            <Container>
                <h1>Crew Members</h1>
                {crews.map((crew) => (
                    <Crew crew={crew} key={crew.crewId}/>
                ))}
            </Container>
            <Container>
                <h1>Add Crew Members </h1>
                <TextField id='outlined-basic' label='First Name' name='firstName' variant='outlined' value={newCrew.firstName} onChange={(e) => onInputChange(e)}></TextField>
                <TextField id='outlined-basic' label='Last Name' name='lastName' variant='outlined' value={newCrew.lastName} onChange={(e) => onInputChange(e)}></TextField>
                <TextField id='outlined-basic' label='Job' name='type' variant='outlined' value={newCrew.type} onChange={(e) => onInputChange(e)}></TextField>
                <TextField id='outlined-basic' label='Aircraft ID' name='aircraftId' variant='outlined' value={newCrew.aircraftId} onChange={(e) => onInputChange(e)}></TextField>
                <Button id="button" style={{marginLeft: '40px', marginTop: '5px'}} variant="contained" onClick={ addCrew }>Add Crew</Button>
                <Container>
                    <h1 style={{marginLeft:"-25px"}}>Remove Crew Members</h1>
                    <TextField style={{marginLeft:"-25px"}} id='outlined-basic' label='Crew ID' name='crewId' variant='outlined' value={removeCrew.crewId} onChange={(e) => onInputChangeRemove(e)}></TextField>
                    <Button style={{marginLeft:"40px"}} id="button" variant="contained" onClick={deleteCrew}>Remove Crew</Button>
                </Container>
            </Container>
        </div>
    </div>
    )
}

export default AdminCrew;