import React from 'react';
import Paper from '@mui/material/Paper';

function Account({ account }) {
  const paperStyle={padding:'30px 20px', width:600,margin:"20px auto"}

  return (
    <Paper elevation={3} style={paperStyle}>
      First Name: {account.firstName} <br></br>
      Last Name: {account.lastName} <br></br>
      Email Address: {account.emailAddress} <br></br>
    </Paper>
)
}

export default Account;