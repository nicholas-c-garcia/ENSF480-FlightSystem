import React, { useState, useEffect } from 'react';
import Header from "../Components/Header"
import { useLocation, useNavigate } from 'react-router-dom';
import { Container, Col, Row } from "react-bootstrap";
import axios from 'axios';
import '../Styles/Seats.css';

function Seats() {
  const location = useLocation();
  const [seats, setSeats] = useState([]);
  const [selectedSeats, setSelectedSeats] = useState(Array(5).fill(null).map(() => ({})));
  const [seatIds, setSeatIds] = useState(Array(5).fill(null).map(() => ({})));
  const { login, currentFlight, browseInfo } = location.state || {};
  const loginState = 'Log Out';
  const [index, setIndex] = useState(0);


  const navigate = useNavigate();
 
  const switchPage = () => {
      // Assuming navigate function supports passing state/params in the second argument
      navigate("/payment-details", { state: { login:login, currentFlight: currentFlight, browseInfo: browseInfo, selectedSeats: selectedSeats }});
    };

  useEffect(() => {
    loadSeats();
    console.log(currentFlight);

  }, []);

  const loadSeats = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/getSeats/${currentFlight}`);
      setSeats(response.data);
    } catch (error) {
      console.error('Error loading seats:', error);
    }
  };

  const handleSeatClick = (seat) => {
    const newSelectedSeats = [...selectedSeats];
        newSelectedSeats[index] = seat;
        console.log(newSelectedSeats);
        setSelectedSeats(newSelectedSeats);
        setIndex(index + 1);

    };

  const getSeatButtonStyle = (seat) => {
    let buttonStyle = '';
  
    if (seat.booked === false) {
      switch (seat.type) {
        case 'First Class':
          buttonStyle = 'first-class';
          break;
        case 'Business Class':
          buttonStyle = 'business-class';
          break;
        case 'Commercial Class':
          buttonStyle = 'commercial-class';
          break;
        default:
          // Handle other cases or set a default style
          break;
      }
    } else {
      // Booked seats are not clickable
      buttonStyle = 'booked-seat';
    }
  
    return buttonStyle;
  };


  return (
    <div id="page">
      <div id="header">
        <Header loginState={loginState} />
      </div>
      <div id="main">
        <div id="box-base">
          <div id="seats-box">
            <div style={{marginRight: "10%", paddingLeft: "3%"}}>
              {/* <button type="button" style={{backgroundColor: "#FF9C08", color: "white"}} className='seat-button' disabled="true">hi</button> */}
              <button type="button" style={{backgroundColor: "#B3B2B5", color: "white"}} className='seat-button' disabled="true">Booked</button>
            </div>
            <div>
              {seats.map((seat, index) => (
                    <button
                      className={`seat-button ${getSeatButtonStyle(seat)}`}
                      onClick={() => handleSeatClick(seat)}
                      disabled={seat.booked === true}
                    >
                      {index + 1}
                    </button>
              ))}
            </div>
            <div style={{marginLeft: "10%", paddingRight: "3%"}}>
              <button type="button" className='seat-button first-class' disabled="true">First Class</button>
              <button type="button" className='seat-button business-class' disabled="true">Commercial Class</button>
              <button type="button" className='seat-button commercial-class' disabled="true">Business Class</button>
            </div>
          </div>
          <div id="info-box">
            <div>
                <h1>Select your Seat(s)</h1>
            </div>
            <div>
                {/* Display selected seats for each passenger */}
                {Array.from({ length: browseInfo.passengerNo }, (_, index) => (
                <div key={index}>
                    <h3>Passenger {index + 1} selected seat:</h3>
                    <ul>
                    <li key={index}>
                        {`${selectedSeats[index]?.seatNumber || 'Not selected'}`}
                    </li>
                    </ul>
                </div>
                ))}
            </div>
            <div>
                <button type="button" id="submit-button" onClick={() => switchPage()}>
                  Next
                </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );

}

export default Seats;
