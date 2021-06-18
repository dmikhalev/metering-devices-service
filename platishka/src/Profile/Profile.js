import {Page} from "../Page/Page";
import React from "react";
import s from "./Profile.module.css";
import {Card} from "../Card/Card";
import {ProfileIcon} from "../ProfileIcon/ProfileIcon";
import {ChangePassword} from "./ProfileModals/ChangePassword";
import {delay} from "../utils";
import {TelegrammModal} from "./ProfileModals/TelegrammModal";
import Cookies from "universal-cookie/es6";
import axios from "axios";

let cookies = new Cookies();

export class Profile extends React.Component {
    constructor() {
        super();
        this.getData();

    }

    state = {
        address: '',
        gas_number: '',
        gas_firm: '',
        water_firm: '',
        water_number: '',
        electro_number: '',
        electro_firm: '',
        modal: null,
        user: {
            oldPassword: '',
            newPassword: '',
            repeatPassword: '',
        },
        message: null,
    }
    // let address = 'Улица несбывшихся надежд';
    // let gas_number = '0001';
    // let gas_firm = 'ОООГазВДом';
    // let water_number = '0002';
    // let water_firm = 'ВодичкаВДом';
    // let electro_number = '0003';
    // let electro_firm = 'Пикачу';


    render() {
        let content = (
            <div>
                <section>
                    <div className={s.card}>
                        <Card title="Личный кабинет">
                            <ProfileIcon height={50} width={50}/>
                        </Card>
                    </div>
                    <div className={s.row}>
                        <button className={s.mybutton} onClick={() => this.changePassword()}>Сменить пароль</button>
                        <button className={s.mybutton} onClick={() => this.getTelegramm()}>Телеграмм бот</button>
                    </div>
                    <div className={s.address}>Адрес: {this.state.address}</div>
                    <div className={s.tableContainer}>
                        <table className={s.table}>
                            <tr>
                                <td>Газоснабжение</td>
                                <td>Номер счета: {this.state.gas_number}</td>
                                <td> {this.state.gas_firm}</td>
                            </tr>
                            <tr>
                                <td>Электричество</td>
                                <td>Номер счета: {this.state.electro_number}</td>
                                <td> {this.state.electro_firm}</td>
                            </tr>
                            <tr>
                                <td>Вода</td>
                                <td>Номер счета: {this.state.water_number}</td>
                                <td> {this.state.water_firm}</td>
                            </tr>
                        </table>
                    </div>
                </section>
            </div>
        )
        return (
            <Page isLoggedIn={true} showModal={this.state.modal} showMessage={this.state.message}>
                {content}
                {this.state.modal === "change_password" ?
                    <ChangePassword user={this.state.user} onSave={this.onSavePassword}
                                    onCancel={this.cancel}/> : (this.state.modal === "telegramm" ?
                        <TelegrammModal link={this.getLink()} onCancel={this.cancel}/> : null)}
                {this.state.message}
            </Page>
        );
    }

    async getData() {


        const config = {
            headers: {Authorization: `Bearer_${cookies.get('token')}`}
        };
        let self = this;

        axios.get('/api/v1/user', config)
            .then(function (response) {
                console.log(response);
                if (response.status == 200) {
                    if (response.data) {
                        let r = response.data;
                        self.setState({
                            address: r.address,
                            gas_number: r.gasPersonalCode,
                            gas_firm: r.gasExecutor,
                            water_firm: r.waterExecutor,
                            water_number: r.waterPersonalCode,
                            electro_number: r.electPersonalCode,
                            electro_firm: r.electExecutor,
                        });
                    }
                }
            })
            .catch(function (error) {
                console.log(error);
            });


        // const r = await (await fetch('/api/v1/user'), {
        //     method: 'GET',
        //     headers: {
        //         Authorization: `Bearer_${cookies.get('token')}`,
        //         'Content-Type': 'application/json'
        //     }
        // }).json();
        // console.log(r);
        // this.setState({
        //     address: r.address,
        //     gas_number: r.gasPersonalCode,
        //     gas_firm: r.gasExecutor,
        //     water_firm: r.waterExecutor,
        //     water_number: r.waterPersonalCode,
        //     electro_number: r.electPersonalCode,
        //     electro_firm: r.electExecutor,
        // });
    }

    getLink = () => {
        return "Иди в телегу"
    }
    changePassword = () => {
        this.setState({
            modal: "change_password",
        })
    }

    getTelegramm = () => {
        this.setState({
            modal: "telegramm",
        })
    }
    cancel = () => {
        this.setState({
            modal: null,
        });
    }

    onSavePassword = async (user) => {
        let data = user;
        if (!data.newPassword) {
            alert("Пустой пароль");
            this.setState({
                message: "Пустой пароль",
            });
            await delay(2000);
            this.setState({
                message: null,
            });
            return;
        }
        if (data.newPassword === data.oldPassword) {
            alert("Новый пароль полностью дублирует старый");
            await delay(2000);
            this.setState({
                message: null,
            });
        }
        if (data.newPassword !== data.repeatPassword) {
            alert("Пароли не совпадают");
            await delay(2000);
            this.setState({
                message: null,
            });
        } else {
            let r = await (await fetch('api/v1/user/change_pass', {
                method: 'POST',
                body: JSON.stringify(data),
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer_${cookies.get('token')}`,
                }
            })).json();
            console.log(r);
            console.log("checked")
            if (r.status === "success") {
                alert("Пароль успешно сменен")
                this.setState({
                    modal: null,
                })
                this.getData();
            } else {
                console.log(r);
                alert("Старый пароль указан неверно!");
            }
        }

    }

}
