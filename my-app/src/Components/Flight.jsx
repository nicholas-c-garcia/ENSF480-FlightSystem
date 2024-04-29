import React from 'react';
import Paper from '@mui/material/Paper';

function Flight({ flight }) {
  const paperStyle={padding:'30px 20px', width:600,margin:"20px auto"}
  
  return (
    <Paper elevation={3} style={paperStyle}>
        Flight ID: {flight.flightId} <br></br>
        Origin: {flight.origin} <br></br>
        Destination: {flight.destination} <br></br>
        Departure Date: {flight.departureDate} <br></br>
        Departure Time: {flight.departureTime} <br></br>
    </Paper>
)
}

export default Flight;