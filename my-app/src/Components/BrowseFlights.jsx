import React from 'react';
import { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';
import '../App.css';

const AdditionalContent = ({ login, currentFlight, setCurrent, browseInfo, flights }) => {
    const navigate = useNavigate();
 
    const switchPage = (flightId) => {
        // Assuming navigate function supports passing state/params in the second argument
        if(!login) {
          navigate("/login")
        } else {
          navigate("/seat-selection", { state: { login:login, currentFlight: flightId, browseInfo: browseInfo } });
        }
      };
    
    return (
        <div style={{width: "100%"}}>
        {flights.map((flight, index) => (
          <div 
          onClick={() => {setCurrent(flight.flightId)}} 
          key={index} 
          style={{ borderRadius:"30px", display: 'flex', flexDirection: 'row', borderBottom: "1px solid #ccc", justifyContent: "space-evenly", backgroundColor: currentFlight === flight.flightId ? "#E7E8EB" : "white", cursor: "pointer",}}>
            <p style={{fontSize: "20px", fontStyle: "normal", fontWeight: "400", lineHeight: "normal"}}>{`Origin: ${flight.origin}`}</p>
            <p style={{fontSize: "20px", fontStyle: "normal", fontWeight: "400", lineHeight: "normal"}}>{`Destination: ${flight.destination}`}</p>
            <p style={{fontSize: "20px", fontStyle: "normal", fontWeight: "400", lineHeight: "normal"}}>{`Departure Date: ${flight.departureDate}`}</p>
            <p style={{fontSize: "20px", fontStyle: "normal", fontWeight: "400", lineHeight: "normal"}}>{`Departure Time: ${flight.departureTime}`}</p>
          </div>
        ))}
        <button style={{marginTop: "2%"}} type="button" id="submit-button" onClick={() => switchPage(currentFlight)}>Continue</button> 
      </div>
    );
  };



function BrowseFlights({ loggedStatus }) {
  const location = useLocation();
    const[isLoading, setIsLoading]= useState(false);
    const[flights, setFlights] = useState([]);
    const[fillFlights, setFillFlights] = useState(false);
    const [currentFlight, setCurrent] = useState(null);
    const { login } = location.state || {};

    const [browseInfo, setBrowseInfo] = useState({
        origin: '',
        destination: '',
        passengerNo: '',
    });

    const handleChange = (e) => {
        setBrowseInfo({
          ...browseInfo,
          [e.target.name]: e.target.value,
        });
    };

    const handleClick = async (e) => {
        console.log(browseInfo);
        setIsLoading(true);
        await loadFlights();
        setIsLoading(false);
    }

    const loadFlights = async () => {
        try {
          const apiFlights = await axios.get("http://localhost:8080/getFlights");
          console.log(apiFlights);
      
          // Create an array to store filtered flights
          const filteredFlights = [];
      
          for (var i = 0; i < apiFlights.data.length; i++) {
            if (apiFlights.data[i].origin === browseInfo.origin && apiFlights.data[i].destination === browseInfo.destination) {
              filteredFlights.push(apiFlights.data[i]);
              setFillFlights(true);
            }
          }
          // Append the filteredFlights to the existing flights array
          setFlights(filteredFlights);
        } catch (error) {
          console.error('Error loading flights:', error);
        }
      };

    return (
        <div >
            <div id="browse-flights">
                <div><h1>Browse Available Flights</h1></div>
                <div id="input-box">
                    <div id="search-attributes">
                        <input id="input-elements" type="text" name="origin" placeholder='From' value={browseInfo.origin} onChange={handleChange}></input>
                        <input id="input-elements" type="text" name="destination" placeholder='To' value={browseInfo.destination} onChange={handleChange}></input>
                        <input id="input-elements" type="text" name="passengerNo" placeholder='Number of Passengers' value={browseInfo.passengerNo} onChange={handleChange}></input> 
                    </div>
                    <button type="button" id="submit-button" onClick={handleClick} disabled={isLoading}>
                        {isLoading ? "Loading..." : "Search Flights"}
                    </button> 
                    {fillFlights && <AdditionalContent login={login} currentFlight={currentFlight} setCurrent={setCurrent} browseInfo={browseInfo} flights={flights}/>}                
                </div>
            </div>
        </div>
    )
}

export default BrowseFlights;
