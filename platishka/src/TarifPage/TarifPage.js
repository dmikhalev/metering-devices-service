import {Page} from "../Page/Page";
import {GasIcon} from "../GasIcon";
import {Button} from "../Button/Button";
import {ElectroIcon} from "../ElectroIcon";
import {WaterIcon} from "../WaterIcon";
import React from 'react';
import {TarifModal} from "./TarifModal/TarifModal";
import axios from "axios";
import Cookies from "universal-cookie/es6";
import s from "./TarifPage.module.css";

let cookies = new Cookies();

export class TarifPage extends React.Component {
    state = {
        curr_gas: 200,
        curr_electro: 300,
        curr_water: 100,
        modal: null,

    }

    constructor() {
        super();
        this.getData()
    }

    // let
    // curr_gas = 300;
    // let
    // curr_electro = 200;
    // let
    // curr_water = 100;

    render() {
        let content = (
            <div>
                <h2>Тарифы</h2>
                <div className={s.container}>
                    <table className={s.table}>
                        <tr>
                            <td><GasIcon/></td>
                            <td><label>{this.state.curr_gas}</label></td>
                            <td>
                                <button onClick={() => this.clickedOn("gas")}>Изменить</button>
                            </td>
                        </tr>
                        <tr>
                            <td><ElectroIcon/></td>
                            <td><label>{this.state.curr_electro}</label></td>
                            <td>
                                <button onClick={() => this.clickedOn("electricity")}>Изменить</button>
                            </td>
                        </tr>
                        <tr>
                            <td><WaterIcon/></td>
                            <td><label>{this.state.curr_water}</label></td>
                            <td>
                                <button onClick={() => this.clickedOn("water")}>Изменить</button>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        );

        return (
            <Page isLoggedIn={true} showModal={this.state.modal}>
                {content}
                <TarifModal onSave={this.save} onCancel={this.cancel} id="1" curr_value={this.getValue()}/>
            </Page>
        );
    }

    getValue() {
        let newVal = 0;
        switch (this.state.modal) {
            case "gas": {
                newVal = this.state.curr_gas;
                break;
            }
            case "water" : {
                newVal = this.state.curr_water;
                break;
            }
            case "electricity" : {
                newVal = this.state.curr_electro;
                break;
            }
            default: {
                newVal = 0;
                break;
            }
        }
        return newVal;
    }

    clickedOn(type) {
        this.setState({
            modal: type,
        })
    }

    save = async (id, value) => { //узнать шо как
        //console.log(value);
        const data = {
            cost: value,
            serviceName: this.state.modal,
        };
        console.log(data);
        await fetch('/api/v1/admin/tariff', {
            method: 'POST',
            body: JSON.stringify(data),
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer_${cookies.get('token')}`
            }
        });
        this.setState({
            modal: null,
        });
        await this.getData();
    }
    cancel = () => {
        this.setState({
            modal: null,
        });
    }

    async getData() {
        const config = {
            headers: {Authorization: `Bearer_${cookies.get('token')}`}
        };
        let self = this;

        axios.get('/api/v1/admin/tariff/active', config)
            .then(function (response) {
                console.log(response);
                if (response.status == 200) {
                    console.log(response.data)
                    if (response.data)
                        self.setState({
                            curr_gas: response.data.gas,
                            curr_electro: response.data.electricity,
                            curr_water: response.data.water,
                        });
                }
            })
            .catch(function (error) {
                console.log(error);
            });

        //
        // const r = await (await fetch('/api/v1/admin/tariff/active')).json();
        // console.log(r);
        // this.setState({
        //     curr_gas: r.gas,
        //     curr_electro: r.electricity,
        //     curr_water: r.water,
        // });
    }
}
