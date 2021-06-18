import {Page} from "../Page/Page";
import {Button} from "../Button/Button";
import s from './Apartment.module.css'
import React from "react";
import {EditApartment} from "./ApartmentCrudModals/EditApartment";
import {AddApartment} from "./ApartmentCrudModals/AddApartment";
import {AddUserModal} from "./ApartmentCrudModals/AddUserModal";
import Cookies from "universal-cookie/es6";
import axios from "axios";

let cookie = new Cookies();

export class ApartmentCrud extends React.Component {
    state = {
        apartments: [
            {
                "apartmentId": "1",
                "buildingCity": "Воронеж",
                "buildingStreet": "Лизюкова",
                "buildingNumber": "45",
                "apartmentNumber": "7",
                "gas": "0001",
                "water": "002",
                "electro": "234",
                "username": "ivan2000",
            },
            {
                "apartmentId": "2",
                "buildingCity": "Воронеж",
                "buildingStreet": "Лизюкова",
                "buildingNumber": "45",
                "apartmentNumber": "8",
                "gas": "0002",
                "water": "003",
                "electro": "235",
                "username": null,
            },
        ],
        modal: null,
        apartment: null,
    }

    constructor() {
        super();
        this.getData();
    }


    render() {
        let content = (
            <div>
                <h2>Редактирование счетов у квартир</h2>
                <br/>
                <button className={s.addButton} onClick={() => this.onAddApartment()}>Добавить</button>
                <table className={s.table}>
                    <thead>
                    <tr>
                        <th>Город</th>
                        <th>Улица</th>
                        <th>Дом</th>
                        <th>Кв</th>
                        <th>№ газ</th>
                        <th>№ вода</th>
                        <th>№ элек-во</th>
                        <th>Изменить</th>
                        <th>Удалить</th>
                        <th>Логин жителя</th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.state.apartments.map(item => (
                        <tr key={item.apartmentId}>
                            <td>{item.buildingCity}</td>
                            <td>{item.buildingStreet}</td>
                            <td>{item.buildingNumber}</td>
                            <td>{item.apartmentNumber}</td>
                            <td>{item.gas}</td>
                            <td>{item.water}</td>
                            <td>{item.electro}</td>

                            <td>
                                <button onClick={() => this.onEditApartment(item)}>Изменить</button>
                            </td>
                            <td onClick={() => this.deleteApartment(item.apartmentId)}><Button text="Удалить"
                                        color="red"/></td>
                            <td>{item.username ? item.username :
                                <button onClick={() => this.onUser(item)}>Добавить</button>}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        );
        return (
            <Page isLoggedIn={true} showModal={this.state.modal}>
                {content}
                {this.state.modal === "edit" && this.state.apartment ?
                    <EditApartment apartment={this.state.apartment} onSave={this.onSaveEdit} onCancel={this.cancel}/>
                    : (this.state.modal === "add" && this.state.apartment ?
                        <AddApartment apartment={this.state.apartment} onSave={this.onSaveAdd}
                                      onCancel={this.cancel}/> : (this.state.modal === 'user' ?
                            <AddUserModal apartment={this.state.apartment} onSave={this.onUserSave}
                                          onCancel={this.cancel}/> : null))}
            </Page>
        );
    };

    deleteApartment = (id) => {
        let data = {
            'apartmentId': id,
        };
        fetch('/api/v1/apartments/', {
            method: 'DELETE',
            body: JSON.stringify(data),
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer_${cookie.get('token')}`,
            }
        });
        this.getData();
    }
    onUser = (item) => {
        this.setState({
            modal: 'user',
            apartment: item,
        })
    }
    onUserSave = async (id, apartment) => {
        let data = apartment;
        console.log(data);
        await fetch('/api/v1/admin/apartment/add_user', {
            method: 'POST',
            body: JSON.stringify(data),
            headers: {
                'Content-Type': 'application/json',

                Authorization: `Bearer_${cookie.get('token')}`,
            }
        });
        this.setState({
            modal: null,
        })
        await this.getData();

    }
    onAddApartment = () => {
        this.setState({
            modal: 'add',
            apartment: {
                "apartmentId": null,
                "buildingCity": "",
                "buildingStreet": "",
                "buildingNumber": "",
                "apartmentNumber": "",
                "gas": "",
                "water": "",
                "electro": ""
            },
        });
    }
    onEditApartment = (item) => {
        this.setState({
            apartment: item,
            modal: 'edit',
        }, () => {
            console.log(this.state.apartment);
        });

    }
    cancel = () => {
        this.setState({
            modal: null,
        });
    }
    onSaveAdd = () => {
        let data = this.state.apartment;
        fetch('/api/v1/admin/apartment', {
            method: 'POST',
            body: JSON.stringify(data),
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer_${cookie.get('token')}`,
            }
        });
        this.setState({
            modal: null,
        })
        this.getData();
    }
    onSaveEdit = () => {
        let data = this.state.apartment;
        fetch('/api/v1/admin/apartment', {
            method: 'POST',
            body: JSON.stringify(data),
            headers: {
                Authorization: `Bearer_${cookie.get('token')}`,
                'Content-Type': 'application/json'
            }
        });
        this.setState({
            modal: null,
        })
        this.getData();
    }

    async getData() {

        let auth = 'Bearer_' + cookie.get('token');
        console.log(auth);
        const config = {
            headers: {Authorization: `Bearer_${cookie.get('token')}`}
        };
        let self = this;

        axios.get('/api/v1/admin/apartment/all', config)
            .then(function (response) {
                console.log(response);
                if (response.status == 200) {
                    if (response.data)
                        self.setState({
                            apartments: response.data,
                        });
                }
            })
            .catch(function (error) {
                console.log(error);
            });


        // await fetch('/api/v1/admin/apartment/all', {
        //     method: 'GET',
        //     headers: {
        //         'Authorization': auth,
        //         'Content-Type': 'application/json'
        //     }
        // }).then(function (response) {
        //     console.log(response.json());
        //     this.setState({
        //         apartments: response.json().data,
        //     });
        // });


    }
}
