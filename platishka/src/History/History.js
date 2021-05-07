import {HistoryIcon} from "../HistoryIcon/HistoryIcon";
import {Card} from "../Card/Card";
import {Page} from "../Page/Page";
import s from './History.module.css'
import React from "react";

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
    // let payHistory = [
    //     {
    //         'type': 'Газ',
    //         'date': '20.03.2021',
    //         'sum': '2000р',
    //     },
    //     {
    //         'type': 'Вода',
    //         'date': '20.03.2021',
    //         'sum': '2000р',
    //     },
    //     {
    //         'type': 'Электричество',
    //         'date': '20.03.2021',
    //         'sum': '2000р',
    //     }
    // ];

    render() {
        let content = (
            <div>
                <Card title="История платежей">
                    <HistoryIcon height={50} width={50}/>
                </Card>
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
        const currPath = 'http://21f340c28901.ngrok.io/'
        const r = await (await fetch(currPath + 'api/v1/info/user/payment_history')).json();
        console.log(r);
        this.setState({
            payHistory: r,
        });
    }

}
