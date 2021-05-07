import {Page} from "../Page/Page";
import {GasIcon} from "../GasIcon";
import {Button} from "../Button/Button";
import {ElectroIcon} from "../ElectroIcon";
import {WaterIcon} from "../WaterIcon";
import React from 'react';
export class TarifPage extends React.Component {
    state = {
        curr_gas: 200,
        curr_electro: 300,
        curr_water: 100,
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
                <label>Тарифы</label>
                <br/>
                <button>Добавить</button>
                <table>
                    <tr>
                        <td><GasIcon/></td>
                        <td><label>{this.state.curr_gas}</label></td>
                        <td>
                            <button>Изменить</button>
                        </td>
                        <td><Button text="Удалить" color="red"/></td>
                    </tr>
                    <tr>
                        <td><ElectroIcon/></td>
                        <td><label>{this.state.curr_electro}</label></td>
                        <td>
                            <button>Изменить</button>
                        </td>
                        <td><Button text="Удалить" color="red"/></td>
                    </tr>
                    <tr>
                        <td><WaterIcon/></td>
                        <td><label>{this.state.curr_water}</label></td>
                        <td>
                            <button>Изменить</button>
                        </td>
                        <td><Button text="Удалить" color="red"/></td>
                    </tr>
                </table>
            </div>
        );
        return (
            <Page isLoggedIn={true}>
                {content}
            </Page>
        );
    }

    async getData() {
        const currPath = 'http://21f340c28901.ngrok.io/'
        const r = await (await fetch(currPath + 'api/v1/admin/tarifs')).json();
        console.log(r);
        this.setState({
            curr_gas: r.curr_gas,
            curr_electro: r.curr_electro,
            curr_water: r.curr_water,
        });
    }
}
