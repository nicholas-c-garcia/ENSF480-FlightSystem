import React from 'react';
import axios from 'axios';
import { useState, useEffect } from 'react';
import Header from "../Components/Header"
import '../Styles/Confirmation.css'
import { styled } from '@mui/material/styles';
import { useLocation, useNavigate } from 'react-router-dom';
import Radio from '@mui/material/Radio';
import FormControlLabel from '@mui/material/FormControlLabel';
import ArrowForwardIosSharpIcon from '@mui/icons-material/ArrowForwardIosSharp';
import MuiAccordion from '@mui/material/Accordion';
import MuiAccordionSummary from '@mui/material/AccordionSummary';
import MuiAccordionDetails from '@mui/material/AccordionDetails';


// material UI Effect
const Accordion = styled((props) => (
    <MuiAccordion disableGutters elevation={0} square {...props} />
  ))(({ theme }) => ({
    border: `1px solid ${theme.palette.divider}`,
    '&:not(:last-child)': {
      borderBottom: 0,
    },
    '&:before': {
      display: 'none',
    },
  }));
  
  const AccordionSummary = styled((props) => (
    <MuiAccordionSummary
      expandIcon={<ArrowForwardIosSharpIcon sx={{ fontSize: '0.9rem' }} />}
      {...props}
    />
  ))(({ theme }) => ({
    backgroundColor:
      theme.palette.mode === 'dark'
        ? 'rgba(255, 255, 255, .05)'
        : 'rgba(0, 0, 0, .03)',
    flexDirection: 'row-reverse',
    '& .MuiAccordionSummary-expandIconWrapper.Mui-expanded': {
      transform: 'rotate(90deg)',
    },
    '& .MuiAccordionSummary-content': {
      marginLeft: theme.spacing(1),
    },
  }));
  
  const AccordionDetails = styled(MuiAccordionDetails)(({ theme }) => ({
    padding: theme.spacing(2),
    borderTop: '1px solid rgba(0, 0, 0, .125)',
  }));




function Confirmation() {
  const location = useLocation();
  const { login, currentFlight, browseInfo, selectedSeats, ticketInsurance } = location.state || {};
  const [expanded, setExpanded] = useState('panel1');
  const [seatCost, setSeatCost] = useState(null);
  const [ticketInsuranceCost, setTicketInsuranceCost] = useState(0);
  const [sendInsurance, setSendInsurance] = useState(false);
  const [taxCost, setTaxCost] = useState(null);
  const [totalCost, setTotalCost] = useState(null);
  const [payment, setPayment] = useState([]);
  const [reservation, setReservation] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [acct, setAccount] = useState({});
  const [discount, setDiscount] = useState(0);
  const [access, setAccess] = useState('')

  const navigate = useNavigate();

  const switchPage = () => {
    // Assuming navigate function supports passing state/params in the second argument
    navigate("/thank-you", {state: {login:login}});
  };

  useEffect(() => {
    console.log(login);
    if (ticketInsurance === 'Yes') {
      setSendInsurance(true);
    }

    try {
      loadInfo();

    } finally {
      managePayment();
    }
  }, []);

  const loadInfo = async () => {
    try {
      const paymentResponse = await axios.get(`http://localhost:8080/getCreditCard/${login.username}`);
      setPayment(paymentResponse.data);
      const loginResponse = await axios.get(`http://localhost:8080/getAccountById/${login.username}`);
      setAccess(loginResponse.data.accessLevel);
      console.log(loginResponse)
    } catch (error) {
      console.error('Error loading seats:', error);
    }
    
  };

  const managePayment = async () => {
    // Ensure selectedSeats is not undefined or null
    const loginResponse = await axios.get(`http://localhost:8080/getAccountById/${login.username}`);
    if (selectedSeats && selectedSeats.length > 0) {
      // Calculate seatCost by iterating through selectedSeats and summing up the cost field
      const calculatedSeatCost = selectedSeats
        .filter(seat => seat.cost) // Filter out seats without a cost field
        .reduce((accumulator, seat) => accumulator + seat.cost, 0);

        console.log(acct)
        setSeatCost(calculatedSeatCost);
        if (ticketInsurance === 'Yes') {
            setTicketInsuranceCost(50);
            if (loginResponse.data.accessLevel === "Registered" || access === "Registered") {
              console.log("hi")
              setDiscount(0.1 * (calculatedSeatCost + 50))
              setTaxCost(0.05 * (calculatedSeatCost + 50 - (0.1 * (calculatedSeatCost + 50))))
              setTotalCost(1.05 * (calculatedSeatCost + 50 - (0.1 * (calculatedSeatCost + 50))));
            } else {
              setTaxCost(0.05 * (calculatedSeatCost + 50))
              setTotalCost(1.05 * (calculatedSeatCost + 50))
            }
        } else {
          if (loginResponse.data.accessLevel === "Registered" || access ==="Registered") {
            setDiscount(0.1 * (calculatedSeatCost))
            setTaxCost(0.05 * (calculatedSeatCost - (0.1 * (calculatedSeatCost))))
            setTotalCost(1.05 * (calculatedSeatCost - (0.1 * (calculatedSeatCost))));
          } else {
            setTaxCost(0.05 * calculatedSeatCost);
            setTotalCost(1.05 * (calculatedSeatCost));
          }
        }
    } else {
      // Handle the case where selectedSeats is empty or undefined
      setSeatCost(0);
      setTotalCost(ticketInsuranceCost);
    }
  };

  const handleChange = (panel) => (event, newExpanded) => {
    setExpanded(newExpanded ? panel : false);
  };

  const postBooking = async (e) => {
    try {
      setIsLoading(true);
      const loginResponse = await axios.get(`http://localhost:8080/getAccountById/${login.username}`);

      const loginEmail = loginResponse.data.emailAddress;

      const reservationResponse = await axios.post(`http://localhost:8080/addReservation/${currentFlight}`, {
        email: loginEmail,
        insurance: sendInsurance,
        cost: totalCost
      });

      // get reservation id
      const reservationId = reservationResponse.data.reservationId;
      console.log(reservationResponse.data)
      console.log(reservationId)


      for (var i = 0; i < browseInfo.passengerNo; i++) {
        await axios.post(`http://localhost:8080/addTicket/${reservationId}`, {
          flightId: currentFlight,
          seatId: selectedSeats[i].seatId
        });
      }
      switchPage();
    } catch (error) {
      console.error('Error loading seats:', error);
    } finally {
      setIsLoading(false);
    }
  }


  const loginState = 'Log Out';

  return (
    <div id="page">
      <div id="header">
        <Header loginState={loginState}/>
      </div>
      <div id="main">
        <div style={{display:"flex", flexDirection:"column", overflow:"scroll"}} id="box-confirmation">
          <h1 style={{padding:"2%"}}>Confirmation of Details</h1>
          <Accordion expanded={expanded === 'panel1'} onChange={handleChange('panel1')}>
        <AccordionSummary aria-controls="panel1d-content" id="panel1d-header">
        <h4>Flight Details</h4>
        </AccordionSummary>
        <AccordionDetails>
          <p>{`Origin: ${browseInfo.origin}`}</p>
          <p>{`Destination: ${browseInfo.destination}`}</p>
          <p>{`Passenger Number: ${browseInfo.passengerNo}`}</p>
          <p>Passenger Seat(s): </p>
          <ul>
    {selectedSeats.map((seat, index) => (
      // Check if seat number is defined before rendering
      seat.seatNumber && (
        <li key={index}>{`Seat Number: ${seat.seatNumber}`}</li>
      )
    ))}
  </ul>
        </AccordionDetails>
      </Accordion>
      <Accordion expanded={expanded === 'panel2'} onChange={handleChange('panel2')}>
        <AccordionSummary aria-controls="panel2d-content" id="panel2d-header">
          <h4>Payment Details</h4>
        </AccordionSummary>
        <AccordionDetails>
          <p>Payment method: Credit</p>
          {/* <p>{`Card Number: ${maskCreditCard(payment.creditCardNumber.toString())}`}</p> */}
        </AccordionDetails>
      </Accordion>
      <Accordion expanded={expanded === 'panel3'} onChange={handleChange('panel3')}>
        <AccordionSummary aria-controls="panel3d-content" id="panel3d-header">
        <h4>Cost</h4>
        </AccordionSummary>
        <AccordionDetails>
          <p>{`Cost of Seat(s): ${seatCost}`}</p>
          <p>{`Cost of Ticket Insurance: ${ticketInsuranceCost}`}</p>
          <p>{`Discount (Registered user): ${discount}`}</p>
          <p>{`Tax: ${taxCost}`}</p>
          <h4>{`Total: ${totalCost}`}</h4>
        </AccordionDetails>
      </Accordion>
        <div style={{alignItems: "flex-end", display: "flex", marginTop: "2%", paddingLeft: "2%"}}>
                <button type='button' id="submit-button" onClick={(e) => {e.preventDefault(); postBooking() }} >{isLoading ? 'Booking...' : 'Book Flight'}</button>
        </div>
        </div>
      </div>
    </div>
  );
}

export default Confirmation;
