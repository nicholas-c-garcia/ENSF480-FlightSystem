import React from 'react';
import Header from './Components/Header';
import './App.css';
import BrowseFlights from './Components/BrowseFlights';

function App() {

    const loginState = 'Log In';

    return (
        <div id="page">
            <div id="header">
                <Header loginState={loginState} />
            </div>
            <div style={{marginTop: "5%"}}>
                <BrowseFlights />
            </div>
        </div>
    )
}

export default App;
