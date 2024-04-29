import React from 'react';
import { useState, useEffect } from 'react';
import Header from "../../Components/Header"
import Account from "../../Components/Account"
import axios from 'axios'
import '../../Styles/Admin.css'
import '../../Styles/Seats.css'

function AdminRegUsers() {
    const loginState = 'Log Out';

    const[accounts, setAccounts] = useState([]);
    useEffect(() => {
        axios
            .get('http://localhost:8080/getRegisteredUsers')
            .then((res) => {
                setAccounts(res.data?res.data:[]);
            })
            console.log(accounts);
    }, []);

    return (
        <div id='page'>
            <div id='header'>
                <Header loginState={ loginState }></Header>
            </div>

            <div id='boxR'>
                <h1>Registered Users</h1>
                {accounts.map((account) => (
                    <Account account={account} key={account.username}/>
                ))}
            </div>
        </div>
    )
}

export default AdminRegUsers;