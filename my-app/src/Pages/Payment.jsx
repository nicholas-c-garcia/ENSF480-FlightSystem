import React, { useState, useEffect } from 'react';
import Radio from '@mui/material/Radio';
import FormControlLabel from '@mui/material/FormControlLabel';
import { useLocation, useNavigate } from 'react-router-dom';
import Header from "../Components/Header"
import '../Styles/Seats.css'
import '../Styles/Payment.css'


function Payment() {

  const loginState = 'Log Out';
  const location = useLocation();
  const { login, currentFlight, browseInfo, selectedSeats } = location.state || {};
  const navigate = useNavigate();
  const [ticketInsurance, setTicketInsurance] = useState(null);

  const handleInsuranceSelection = (value) => {
      // If the user selects the same option again, unselect it
      if (ticketInsurance === value) {
        setTicketInsurance(null);
      } else {
        setTicketInsurance(value);
      }
    };


  const switchPageConfirm = () => {
      navigate("/confirmation", { state: { login:login, currentFlight: currentFlight, browseInfo: browseInfo, selectedSeats: selectedSeats, ticketInsurance: ticketInsurance }});
  };

  useEffect(() => {
    console.log(currentFlight);
    console.log(browseInfo);
    console.log(selectedSeats);
  }, [])

  return (
    <div id="page">
        <div id="header">
            <Header loginState={loginState}/>
        </div>
        <div id="main">
            <div id="payment-box">
                <div id="content">
                    <h1>Ticket Insurance</h1>
                    <p>
                        Introducing our innovative Ticket Insurance Policy –   
                        Our policy covers unforeseen circumstances such as medical emergencies, trip cancellations,   
                        and even flight delays. Would you like to have Ticket Insurance for all tickets for $50?
                    </p>
                    <div>
                        <FormControlLabel
                        control={<Radio />}
                        label="Yes"
                        onChange={() => handleInsuranceSelection('Yes')}
                        checked={ticketInsurance === 'Yes'}
                        />
                        <FormControlLabel
                        control={<Radio />}
                        label="No"
                        onChange={() => handleInsuranceSelection('No')}
                        checked={ticketInsurance === 'No'}
                        />
                    </div>
                    <div style={{alignItems: "flex-end", display: "flex", paddingTop: "3%"}}>
                        <button type='button' id="submit-button" onClick={ switchPageConfirm }>Next</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
)
}

export default Payment;