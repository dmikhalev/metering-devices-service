import {Page} from "../Page/Page";
import React from "react";

import {Card} from "../Card/Card";
import {ProfileIcon} from "../ProfileIcon/ProfileIcon";
import {DebtIcon} from "../DebtIcon/DebtIcon";
import {ExistDebtIcon} from '../ExistDebtIcon'
import {GasIcon} from "../GasIcon";
import {ElectroIcon} from "../ElectroIcon";
import {WaterIcon} from "../WaterIcon";
import {NoDebtIcon} from "../NoDebtIcon";

export class Debt extends React.Component {
    state = {
        gas_debt: 0,
        water_debt: 0,
        electro_debt: 0,
    }

    render() {
        let content = (
            <div>
                <Card title="Задолженности">
                    <DebtIcon height={50} width={50}/>
                </Card>
                <table>
                    <tr>
                        <th></th>
                        <th>Услуга</th>
                        <th>Задолженность</th>
                        <th>Картиночка</th>
                    </tr>
                    <tr>
                        <td><GasIcon/></td>
                        <td>Газоснабжение</td>
                        <td> {this.state.gas_debt}</td>
                        <td>{this.state.gas_debt > 0 ? <DebtIcon/> : <NoDebtIcon/>}</td>

                    </tr>
                    <tr>
                        <td><ElectroIcon/></td>
                        <td>Электричество</td>
                        <td> {this.state.electro_debt}</td>
                        <td>{this.state.electro_debt > 0 ? <DebtIcon/> : <NoDebtIcon/>}</td>
                    </tr>
                    <tr>
                        <td><WaterIcon/></td>
                        <td>Вода</td>
                        <td> {this.state.water_debt}</td>
                        <td>{this.state.water_debt > 0 ? <DebtIcon/> : <NoDebtIcon/>}</td>
                    </tr>
                </table>

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
        const r = await (await fetch(currPath + 'api/v1/info/user/debt')).json();
        console.log(r);
        this.setState({
            gas_debt: r.gasDebt,
            water_debt: r.waterDebt,
            electro_debt: r.electDebt,
        });
    }

}
