import s from './Login.module.css'
import React from 'react'
import axios from "axios";
import {Home} from "../Home/Home";
import App from "../App";

export class Login extends React.Component {
    state = {
        username: null,
        password: null,
    };

    render() {
        return (
            <div className={s.container}>
                <form>
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
                </form>
            </div>
        )
    };
    changeLogin = (e) => {
        this.setState({
            login: e.target.value
        });
    }
    changePassword = (e) => {
        this.setState({
            password: e.target.value
        });
    }
    handleClick() {
        var apiBaseUrl = " http://e06b8e648d8c.ngrok.io/";
        var self = this;
        var payload = {
            "username": this.state.username,
            "password": this.state.password
        }
        axios.post(apiBaseUrl + 'api/v1/auth/login', payload)
            .then(function (response) {
                console.log(response);
                if (response.data.code == 200) {
                    console.log("Login successfull");
                    var uploadScreen = [];
                    App.cookies.set('isLoggedIn', true);
                    App.cookies.set('token', response.data.token);
                    uploadScreen.push(<Home loggedIn={true} isAdmin={response.data.role === "admin"}/>);
                } else if (response.data.code == 204) {
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
