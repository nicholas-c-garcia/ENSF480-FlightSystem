import React from 'react';
import axios from 'axios';
import {useState, useEffect} from "react";
import { useNavigate } from 'react-router-dom';
import Header from "../Components/Header"
import '../Styles/Signup.css';

function Signup() {
    const navigate = useNavigate();
    const loginState = "Have an Account? Sing in!"

    const [users, setUsers] = useState([]);
    const [user, setUser] = useState({
        firstName: '',
        lastName: '',
        emailAddress: '',
        username: '',
        password: '',
        confirmPassword: '',
        accessLevel: 'Customer',
    });

    const [creditCard, setCreditCard] = useState({
        creditCardNumber: '',
        cvv: '',
        expiryMonth: '',
        expiryYear:'',
    });

    const [login, setLogin] = useState({
        username: '',
        password: '',
    });

    const handleFirstNameChange = (e) => {
        setUser({
            ...user,
            firstName: e.target.value,
        });
    };

    const handleLastNameChange = (e) => {
        setUser({
            ...user,
            lastName: e.target.value,
        });
    };
    
    const handleEmailChange = (e) => {
        setUser({
            ...user,
            emailAddress: e.target.value,
        });
    };
    
    const handleUsernameChange = (e) => {
        setUser({
            ...user,
            username: e.target.value,
        });

        setLogin({
            ...login,
            username: e.target.value,
        })
    };
    const handlePasswordChange = (e) => {
        setUser({
            ...user,
            password: e.target.value,
        });
        setLogin({
            ...login,
            password: e.target.value,
        })
    };
    
    const handleConfirmPasswordChange = (e) => {
        setUser ({
            ...user,
            confirmPassword: e.target.value,
        });
    }

    const handleRegisteredUser = (e) => {
        if (user.accessLevel === 'Customer') {
            setUser({
                ...user,
                accessLevel: 'Registered',
            });
        } else {
            setUser({
                ...user,
                accessLevel: 'Customer',
            });
        }
    }

    const handleCreditCardNumber = (e) => {
        setCreditCard({
            ...creditCard,
            creditCardNumber: e.target.value,
        });
    }

    const handleCVV = (e) => {
        setCreditCard({
            ...creditCard,
            cvv: e.target.value,
        });
    }

    const handleExpiryMonth = (e) => {
        setCreditCard({
            ...creditCard,
            expiryMonth: e.target.value,
        });
    }

    const handleExpiryYear = (e) => {
        setCreditCard({
            ...creditCard,
            expiryYear: e.target.value,
        });
    }

    const handleFormSubmit = async (e) => {
        e.preventDefault();
        /* Deals with the the Account information */
        if (user.firstName === '') {
            window.alert("Please fill-in the First Name field");
            return;
        } else if (user.lastName === '') {
            window.alert("Please fill-in the Last Name field");
            return;
        } else if (user.emailAddress === '') {
            window.alert("Please fill-in the Email Address field");
            return;
        } else if(user.username === '') {
            window.alert("Please fill-in the Username field");
            return;
        } else if (user.password === '') {
            window.alert("Please fill-in the Password field");
            return;
        } else if (user.password !== user.confirmPassword) {
            window.alert("Confirmation of password is incorrect, retry your password");
            return;
        }

        /* Deals with the Credit Card Number Information */
        let onlyContainsNumbers = (str) => /^\d+$/.test(str);
        if (creditCard.creditCardNumber !== '') {
            if (!onlyContainsNumbers(creditCard.creditCardNumber)) {
                window.alert("The Credit Card contains non-numeric values");
                return;
            }

            if (creditCard.creditCardNumber.length > 16) {
                window.alert("The length of Credit Card is greater than 16");
                return;
            } else if (creditCard.creditCardNumber.length < 16) {
                window.alert("The length of Credit Card is less than 16");
                return;
            } 
        } else if (creditCard.creditCardNumber === '') {
            window.alert("Please fill-in the Credit Card field");
            return;
        }
        /* Deals with CVV*/
        if (creditCard.cvvr !== '') {
            if (!onlyContainsNumbers(creditCard.cvv)) {
                window.alert("The CVV contains non-numeric values, example: 1234123412341234");
                return;
            }

            if (creditCard.cvv.length > 3) {
                window.alert("The length of CVV is greater than 3");
                return;
            } else if (creditCard.cvv.length < 3) {
                window.alert("The length of CVV is less than 3");
                return;
            } 
        } else if (creditCard.cvv === '') {
            window.alert("Please fill-in the CVV field");
            return;
        }
        /* Deals with Expiry Month*/
        if (creditCard.expiryMonth !== '') {
            if (!onlyContainsNumbers(creditCard.expiryMonth)) {
                window.alert("The month contains non-numeric values, please enter 01-12");
                return;
            }

            if ((parseInt(creditCard.expiryMonth) > 12) && (parseInt(creditCard.expiryMonth) <= 0)) {
                window.alert("Invalid Expiry Month");
                return;
            } 

            if (creditCard.expiryMonth.length !== 2) {
                window.alert("Please set expiry month in the format of: xx");
                return;
            }

        } else if (creditCard.expiryMonth === '') {
            window.alert("Please fill-in Expiry Month");
            return;
        }

        /* Deals with Expiry Year*/
        if (creditCard.expiryYear !== '') {
            if (!onlyContainsNumbers(creditCard.expiryYear)) {
                window.alert("The year contains non-numeric values, please enter a valid expiry year");
                return;
            }

            if (parseInt(creditCard.expiryYear) < 23) {
                window.alert("Invalid Expiry Year");
                return;
            } 
            if (parseInt(creditCard.expiryYear.length !== 2)) {
                window.alert("Please set expiry year in the format of: xx");
                return;
            } 
        } else if (creditCard.expiryYear === '') {
            window.alert("Please fill-in Expiry Year");
            return;
        }



        const result = await axios.get(`http://localhost:8080/getAccounts`);

        for (var i = 0; i < result.data.length; i++) {
            if ((result.data[i].username === user.username)) {
                window.alert("The username already exists, please try another username");
                return;
            }
        }

        const newCreditCard = {
            creditCardNumber: creditCard.creditCardNumber,
            cvv: creditCard.cvv,
            expiryMonth: creditCard.expiryMonth,
            expiryYear: creditCard.expiryYear,
        }

        const res = await axios.post(`http://localhost:8080/addCreditCard`, newCreditCard);
        console.log(res);

        const newUser = {
            firstName: user.firstName,
            lastName: user.lastName,
            emailAddress: user.emailAddress,
            username: user.username,
            password: user.password,
            accessLevel: user.accessLevel,
            creditCardId: res.data.creditCardId,
        };

        await axios.post("http://localhost:8080/addAccount", newUser);
        setUsers((prevUsers) => [...prevUsers, newUser]);
        navigate('/home', {state: {login:login}});

    };

    useEffect(() => { console.log(users); }, [users]);


    return (
        <div id="sign-page">
            <div id="header">
                <Header />
            </div>
            <h1>Sign Up</h1>
            <div style={{overflowY: 'scroll', display: "flex", flexDirection: "column", alignItems: "center", justifyContent: 'center', width:"60%", height:"100%", margin: '0px 0px 90px 0px'}}>
                <div id="sign-up-form"> 
                    <form style={{display:'flex', flexDirection:'column', justifyContent:'center', alignItems:'center'}} onSubmit={ handleFormSubmit }>
                        <input style={{padding: '0px 15px 0px 20px'}} id="user-info-sign-up" type="text" name="firstName" placeholder='First Name' value={user.firstName} onChange={ handleFirstNameChange }></input>
                        <input style={{padding: '0px 15px 0px 20px'}} id="user-info-sign-up" type="text" name="lastName" placeholder='Last Name' value={user.lastName} onChange={ handleLastNameChange }></input>
                        <input style={{padding: '0px 15px 0px 20px'}} id="user-info-sign-up" type="text" name="emailAddress" placeholder='Email Address' value={user.emailAddress} onChange={ handleEmailChange }></input>
                        <input style={{padding: '0px 15px 0px 20px'}} id="user-info-sign-up" type="text" name="username" placeholder='Username' value={user.username} onChange={ handleUsernameChange }></input>
                        <input style={{padding: '0px 15px 0px 20px'}} id="user-info-sign-up" type="password" name="password" placeholder='Password' value={user.password} onChange={ handlePasswordChange }></input>
                        <input style={{padding: '0px 15px 0px 20px'}} id="user-info-sign-up" type="password" name="confirmPassword" placeholder='Confirm Password' value={user.confirmPassword} onChange={ handleConfirmPasswordChange }></input>
                        <input style={{padding: '0px 15px 0px 20px'}} id="user-info-sign-up" type="text" name="creditCardNumber" placeholder='Credit Card Number' value={creditCard.creditCardNumber} onChange={ handleCreditCardNumber }></input>
                        <input style={{padding: '0px 15px 0px 20px'}} id="user-info-sign-up" type="text" name="cvv" placeholder='CVV' value={creditCard.cvv} onChange={ handleCVV }></input>
                        <input style={{padding: '0px 15px 0px 20px'}} id="user-info-sign-up" type="text" name="expiryMonth" placeholder='Expiry Month - 01 for January, 12 for December' value={creditCard.expiryMonth} onChange={ handleExpiryMonth }></input>
                        <input style={{padding: '0px 15px 0px 20px'}} id="user-info-sign-up" type="text" name="expiryYear" placeholder='Expiry Year' value={creditCard.expiryYear} onChange={ handleExpiryYear }></input>
                        <div style={{marginTop: '15px', marginBottom:'15px'}} id = "registered-user-section">
                            <b>Become a registered user? </b><input style={{marginTop: '-100%', transform: 'scale(0.5)'}} id="user-info-sign-up" type="checkbox" name="registeredUser" onChange={handleRegisteredUser}></input>
                        </div>
                        <button style={{cursor: 'pointer'}} id="submit-button" type="submit">Submit</button>
                    </form>
                </div>
            </div>
        </div>
      );
}

export default Signup;