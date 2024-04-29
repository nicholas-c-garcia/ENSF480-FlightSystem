import React from 'react';
import App from '../App';
import { useState, useEffect } from 'react';
import Header from '../Components/Header';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';
import '../App.css';
import '../Styles/Home.css'
import BrowseFlights from '../Components/BrowseFlights';

const AdditionalContent = ({ loadReservations, loadFlights, reservations, flights, currentRes, setCurrent }) => {

  const deleteReservation = async (id) => {
    await axios.delete(`http://localhost:8080/removeReservation/${id}`);
    window.location.reload(false);
    // loadReservations();
  }

  useEffect(() => {
    console.log("hi")
    console.log(reservations);
    console.log(flights);
  })
  
  return (
    <div style={{width: "100%"}}>
      {reservations.map((reservation, index) => (
        <div 
          onClick={() => {setCurrent(reservation.reservationId)}} 
          key={index} 
          style={{
            borderRadius:"30px", 
            display: 'flex', 
            flexDirection: 'row', 
            borderBottom: "1px solid #ccc", 
            justifyContent: "space-evenly", 
            backgroundColor: currentRes === reservation.reservationId ? "#E7E8EB" : "white", 
            cursor: "pointer",
          }}
        >
          <p style={{paddingTop:"1%", fontSize: "15px", fontStyle: "normal", fontWeight: "400", lineHeight: "normal"}}>{`Reservation No: ${reservation.reservationId}`}</p>
          {flights[index] && (
            <>
              <p style={{paddingTop:"1%", fontSize: "15px", fontStyle: "normal", fontWeight: "400", lineHeight: "normal"}}>{`Origin: ${flights[index].origin}`}</p>
              <p style={{paddingTop:"1%", fontSize: "15px", fontStyle: "normal", fontWeight: "400", lineHeight: "normal"}}>{`Destination: ${flights[index].destination}`}</p>
              <p style={{paddingTop:"1%", fontSize: "15px", fontStyle: "normal", fontWeight: "400", lineHeight: "normal"}}>{`Departure Date: ${flights[index].departureDate}`}</p>
              <p style={{paddingTop:"1%", fontSize: "15px", fontStyle: "normal", fontWeight: "400", lineHeight: "normal"}}>{`Departure Time: ${flights[index].departureTime}`}</p>
            </>
          )}
          <button style={{marginTop: "2%"}} type="button" id="delete-button" onClick={() => deleteReservation(reservation.reservationId)}>Delete</button> 
        </div>
      ))}
    </div>
  );
};






function Home() {
  const location = useLocation();
  const[fillFlights, setFillFlights] = useState(false);
  const { login } = location.state || {};
  const [reservations, setReservations] = useState([]);
  const [flights, setFlights] = useState([]);
  const [currentRes, setCurrent] = useState(null);

  const loginState = "Log Out"

  useEffect(() => {
    console.log("Entering useEffect");
    loadReservations();
    loadFlights();
  }, []);


  const loadFlights = async () => {
    console.log("Entering loadFlights");
    try {
      const fetchedFlights = [];
      const apiReservations = await axios.get(`http://localhost:8080/getReservations`);
  
      for (var i = 0; i < apiReservations.data.length; i++) {
        try {
          const apiFlight = await axios.get(`http://localhost:8080/getFlight/${apiReservations.data[i].flightId}`);
          console.log("hi")
          console.log(apiFlight);
          fetchedFlights.push(apiFlight.data);
        } catch (error) {
          console.error(`Error loading flight ${reservations[i].flightId}:`, error);
        }
      }
      console.log(reservations.length);
      setFlights(fetchedFlights);
  
    } catch (error) {
      console.error('Error loading flights:', error);
    }
  };

  const loadReservations = async () => {
    console.log(login);
    try {
      const apiReservations = await axios.get(`http://localhost:8080/getReservations`);
      const loginResponse = await axios.get(`http://localhost:8080/getAccountById/${login.username}`);

      const loginEmail = loginResponse.data.emailAddress;
      console.log(loginEmail);  

      const filteredFlights = [];
      setFillFlights(false);
      for (var i = 0; i < apiReservations.data.length; i++) {
        if (apiReservations.data[i].email === loginEmail ) {
          filteredFlights.push(apiReservations.data[i]);
        }
        setFillFlights(true);
      }
      setReservations(filteredFlights);
    } catch (error) {
      console.error('Error loading flights:', error);
    }
  };

  return (
  <div id="home-page">
    <div id="header">
      <Header loginState={loginState}/>
    </div>

    <div id="home-body">
        <BrowseFlights />
      
      <div style={{display:"flex", flexDirection:"column", justifyContent:"center", alignItems:"center", marginTop:"5%"}}>
        <h1>Manage Bookings</h1>
        <div id="manage-input-box">
          <p>
            {!fillFlights ? "No Flights Booked" : "Manage the Following"}
          </p>
          {fillFlights && <AdditionalContent loadReservations={loadReservations} loadFlights={loadFlights} reservations={reservations} flights={flights} currentRes={currentRes} setCurrent={setCurrent}/>}
        </div>

      </div>
    </div>
    

  </div>
)
}

export default Home;