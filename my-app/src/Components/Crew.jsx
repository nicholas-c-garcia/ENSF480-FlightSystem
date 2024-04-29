import React from 'react';
import Paper from '@mui/material/Paper';

function Crew({ crew }) {
  const paperStyle={padding:'30px 20px', width:600,margin:"20px auto"}
  return (
    <Paper elevation={3} style={paperStyle}>
        <b>First Name:</b> {crew.firstName} <br></br>
        <b>Last Name:</b> {crew.lastName} <br></br>
        <b>Job:</b> {crew.type} <br></br>
        <b>AircraftID:</b> {crew.aircraftId} <br></br>
        <b>CrewID: </b> {crew.crewId} <br></br>
    </Paper>
)
}

export default Crew;