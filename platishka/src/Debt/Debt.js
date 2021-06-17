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
import Cookies from "universal-cookie/es6";
import s from './Debt.module.css'

let cookie = new Cookies();
export class Debt extends React.Component {
    constructor(props) {
        super(props);
        this.getData();
    }

    state = {
        gas_debt: 0,
        water_debt: 2,
        electro_debt: 1,
    }

    render() {
        let content = (
            <div>
                <div className={s.card}>
                    <Card title="Задолженности" >
                        <DebtIcon height={50} width={50}/>
                    </Card>
                </div>
                <table className={s.table}>
                    <tr>
                        <td><GasIcon/></td>
                        <td>Газоснабжение</td>
                        <td className={s.debt}> {this.state.gas_debt > 0 ? "Неоплаченных месяцев: " + this.state.gas_debt : "Все оплачено"}</td>
                        <td>{this.state.gas_debt > 0 ? <ExistDebtIcon/> : <NoDebtIcon/>}</td>

                    </tr>
                    <tr>
                        <td><ElectroIcon/></td>
                        <td>Электричество</td>
                        <td className={s.debt}> {this.state.electro_debt > 0 ? "Неоплаченных месяцев: " + this.state.electro_debt : "Все оплачено"}</td>
                        <td>{this.state.electro_debt > 0 ? <ExistDebtIcon/> : <NoDebtIcon/>}</td>
                    </tr>
                    <tr>
                        <td><WaterIcon/></td>
                        <td>Вода</td>
                        <td className={s.debt}> {this.state.water_debt > 0 ? "Неоплаченных месяцев: " + this.state.water_debt : "Все оплачено"}</td>
                        <td>{this.state.water_debt > 0 ? <ExistDebtIcon/> : <NoDebtIcon/>}</td>
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
        const r = await (await fetch('/api/v1/user/debt', {
            headers: {
                Authorization: `Bearer_${cookie.get('token')}`,
                'Content-Type': 'application/json'
            }
        })).json();
        console.log(r);
        this.setState({
            gas_debt: r.gasDebt,
            water_debt: r.waterDebt,
            electro_debt: r.electDebt,
        });
    }

}
