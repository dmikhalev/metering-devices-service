import {Page} from "../Page/Page";
import React from "react";
import s from "../Home/Home.module.css";
import {Card} from "../Card/Card";
import {ProfileIcon} from "../ProfileIcon/ProfileIcon";

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
                <section className={s.table}>
                    <Card title="Личный кабинет">
                        <ProfileIcon height={50} width={50}/>
                    </Card>
                    <div className={s.row}>
                        <button>Сменить пароль</button>
                        <button>Телеграмм бот</button>
                    </div>
                    <label>Адрес: {this.state.address}</label>
                    <table>
                        <tr>
                            <th>Наименование услуги</th>
                            <th>Номер счета</th>
                            <th>Обслуживающая компания</th>
                        </tr>
                        <tr>
                            <td>Газоснабжение</td>
                            <td> {this.state.gas_number}</td>
                            <td> {this.state.gas_firm}</td>
                        </tr>
                        <tr>
                            <td>Электричество</td>
                            <td> {this.state.electro_number}</td>
                            <td> {this.state.electro_firm}</td>
                        </tr>
                        <tr>
                            <td>Вода</td>
                            <td> {this.state.water_number}</td>
                            <td> {this.state.water_firm}</td>
                        </tr>
                    </table>
                </section>
            </div>
        )
        return (
            <Page isLoggedIn={true}>
                {content}
            </Page>
        );
    }

    async getData() {
        const currPath = 'http://21f340c28901.ngrok.io/'
        const r = await (await fetch(currPath + 'api/v1/user')).json();
        console.log(r);
        this.setState({
            address: r.address,
            gas_number: r.gasPersonalCode,
            gas_firm: r.gasExecutor,
            water_firm: r.waterExecutor,
            water_number: r.waterPersonalCode,
            electro_number: r.electPersonalCode,
            electro_firm: r.electExecuto,
        });
    }


}
