import {Page} from "../Page/Page";
import {Button} from "../Button/Button";
import s from './Users.module.css'
import React from "react";
import {UserModal} from "./UsersModal/UserModal";
import Cookies from "universal-cookie/es6";
import axios from "axios";
let cookies = new Cookies();

export class Users extends React.Component {
    state = {
        users: [
            {
                "id": "1",
                "name": "Иванов Иван Иваныч",
                "username": "ivan2000",
            },
            {
                "id": "2",
                "name": "Иванов Петр Иваныч",
                "username": "petr2000",
            }
        ],
        modal: null,
        user: null,
    };

    constructor(func) {
        super();
        this.getData();
    }


    render() {
        let content = (
            <div>
                <h2>Пользователи</h2>
                <button className={s.addButton} onClick={() => this.onCreateUser()}>Добавить</button>
                <table className={s.table}>
                    <thead>
                    <th>ФИО</th>
                    <th>Логин</th>
                    <th>Изменить</th>
                    <th>Удалить</th>
                    <th>Сбросить пароль</th>
                    </thead>
                    <tbody>
                    {this.state.users.map(item => (
                        <tr>
                            <td>{item.name}</td>
                            <td>{item.username}</td>
                            <td>
                                <button onClick={() => this.onChangeUser(item)}>Изменить</button>
                            </td>
                            <td onClick={() => this.onDeleteUser(item.id)}><Button  text="Удалить" color="red"/></td>
                            <td>
                                <button onClick={() => this.resetPassword(item.id)}>Сбросить пароль</button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        )
        if (this.state.showModal) {

        }
        return (
            <Page showModal={this.state.modal !== null}>
                {content}
                {<UserModal type={this.state.modal} onSave={this.onSave} onCancel={this.cancel} user={this.state.user}/>}
            </Page>
        );
    };

    onCreateUser = async () => {
        let passwd = await this.generatePassword()
        this.setState({
            modal: "add",
            user: {
                "id" : null,
                "username": "",
                "name" : "",
                "password": passwd,
            },
        })
    };
    async generatePassword() {
        const config = {
            headers: {Authorization: `Bearer_${cookies.get('token')}`}
        };

        axios.post('/api/v1/admin/users/generate_pass', config)
            .then(function (response) {
                console.log(response);
                if (response.status === 200) {
                    console.log(response.data)
                    if (response.data)
                        return response.data.password;
                }
            })
            .catch(function (error) {
                console.log(error);
            });


        // const r = await (await fetch('/api/v1/admin/users/generate_pass', {
        //     method:"POST",
        //     headers: {
        //         'Content-Type': 'application/json',
        //         Authorization: `Bearer_${cookies.get('token')}`,
        //     }
        // })).json();
        // return r.password;
    }
    onChangeUser = (item) => {
        this.setState({
            modal: "edit",
            user: item,
        })
    }
    onSave = (user) => {
        let data = user;
        console.log(user);
        fetch('/api/v1/admin/users', {
            method: 'POST',
            body: JSON.stringify(data),
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer_${cookies.get('token')}`,
            }
        });
        this.setState({
            modal: null,
        })
    }
    async onDeleteUser(id) {
        const data = {
            'id': id,
        }
        await fetch('/api/v1/admin/users', {
            method: 'DELETE',
            body: JSON.stringify(data),
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer_${cookies.get('token')}`,
            }
        });
        await this.getData();
    }

    async resetPassword(id) {
        const data = {
            'id': id,
        }
        await fetch('/api/v1/admin/users/reset_pass', {
            method: 'POST',
            body: JSON.stringify(data),
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer_${cookies.get('token')}`,
            }
        });
        await this.getData();
    }
    cancel = () => {
        this.setState({
            modal: null,
        });
    }

    async getData() {
        let auth = 'Bearer_' + cookies.get('token');
        console.log(auth);
        const config = {
            headers: {Authorization: `Bearer_${cookies.get('token')}`}
        };
        let self = this;

        axios.get('/api/v1/admin/users/all', config)
            .then(function (response) {
                console.log(response);
                if (response.status === 200) {
                    if (response.data)
                        self.setState({
                            users: response.data,
                        });
                }
            })
            .catch(function (error) {
                console.log(error);
            });
    };

}
