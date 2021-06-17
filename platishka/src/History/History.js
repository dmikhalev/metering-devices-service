import {HistoryIcon} from "../HistoryIcon/HistoryIcon";
import {Card} from "../Card/Card";
import {Page} from "../Page/Page";
import s from './History.module.css'
import React from "react";
import Cookies from "universal-cookie/es6";
let cookie = new Cookies();
export class History extends React.Component {
    state = {
        payHistory: [
            {
                    'service': 'Газ',
                    'date': '20.03.2021',
                    'sum': '2000р',
                },
                {
                    'service': 'Вода',
                    'date': '20.03.2021',
                    'sum': '2000р',
                },
                {
                    'service': 'Электричество',
                    'date': '20.03.2021',
                    'sum': '2000р',
                }
        ],
    }
    constructor() {
        super();
        this.getData();
    }

    render() {
        let content = (
            <div>
                <div className={s.card}>
                <Card title="История платежей">
                    <HistoryIcon height={50} width={50}/>
                </Card>
                </div>
                <table className={s.table}>
                    <thead>
                    <tr>
                        <th>Услуга</th>
                        <th>Дата оплаты</th>
                        <th>Сумма платежа</th>
                    </tr>
                    </thead>
                    <tbody>

                    {this.state.payHistory.map(item => (
                        <tr>
                            <td>{item.service}</td>
                            <td>{item.date}</td>
                            <td>{item.sum}</td>
                        </tr>
                    ))}
                    </tbody>
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
        const r = await (await fetch('/api/v1/user/payment_history', {
            headers: {
                Authorization: `Bearer_${cookie.get('token')}`,
                'Content-Type': 'application/json'
            }
        })).json();
        console.log(r);
        this.setState({
            payHistory: r,
        });
    }

}
