import s from './Login.module.css'
import React from 'react'
import axios from "axios";
import {Home} from "../Home/Home";
import {Page} from "../Page/Page";
import Cookies from 'universal-cookie';
import {Redirect} from "react-router"


const cookies = new Cookies();


export class Login extends React.Component {
    constructor() {
        super();
        cookies.set('isLoggedIn', false);
        cookies.remove('token');
        this.setState({
            username: null,
            password: null,
            redirect: false,
        });
    }
    state = {
        username: null,
        password: null,
        redirect: false,
    }

    render() {
        if (this.state.redirect) {
            return <Redirect push to="/"/>
        }
        return (
            <Page>
                <div className={s.container}>
                    <label htmlFor="uname"><b>Логин</b></label>
                    <input onChange={this.changeLogin} type="text"
                           placeholder="Enter Username" name="uname" required/>

                    <label htmlFor="psw"><b>Пароль</b></label>
                    <input type="password" placeholder="Enter Password" name="psw" required
                           onChange={this.changePassword}/>

                    <button onClick={(event) => this.handleClick(event)} className={s.greenButton}
                            type="submit">Войти
                    </button>
                </div>
            </Page>
        )
    };

    changeLogin = (e) => {
        this.setState({
            username: e.target.value
        });
    }
    changePassword = (e) => {
        this.setState({
            password: e.target.value
        });
    }

    handleClick = async (event) => {

        var payload = {
            "username": this.state.username,
            "password": this.state.password,
        }
        let self = this;
        // let response = await ( await fetch('/api/v1/auth/login', {
        //     method: 'POST',
        //     body: JSON.stringify(payload),
        //     headers: {
        //         'Content-Type': 'application/json',
        //         'accept': '*/*',
        //     }
        // })).json();
        // console.log(response);
        axios.post('/api/v1/auth/login', payload)
            .then(function (response) {
                console.log(response);
                if (response.status == 200) {
                    console.log("Login successfull");
                    var uploadScreen = [];
                    cookies.set('isLoggedIn', true);
                    cookies.set('token', response.data.token);
                    cookies.set('role', response.data.role);
                    self.setState({
                        redirect: true,
                    })

                } else if (response.status == 204) {
                    console.log("Username password do not match");
                    alert("username password do not match")
                } else {
                    console.log("Username does not exists");
                    alert("Username does not exist");
                }
            })
            .catch(function (error) {
                console.log(error);
            });
    }
}
