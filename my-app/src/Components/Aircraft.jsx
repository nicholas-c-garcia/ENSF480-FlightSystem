import React from 'react';
import Paper from '@mui/material/Paper';

function Aircraft({ aircraft }) {
  const paperStyle={padding:'30px 20px', width:600,margin:"20px auto"}
  
  return (
    <Paper elevation={3} style={paperStyle}>
        Aircraft ID: {aircraft.aircraftId} <br></br>
        Name: {aircraft.aircraftName}
    </Paper>
)
}

export default Aircraft;