import React, {useEffect, useRef, useState} from "react";
import {Page} from "../Page/Page";
import gif from "../TryPay/giphy.gif";
import Cookies from 'universal-cookie';
import s from './Payment.module.css'
import * as cn from "classnames";

const cookies = new Cookies();

function Product({product}) {
    const [paidFor, setPaidFor] = useState(false);
    const [error, setError] = useState(null);
    const paypalRef = useRef();

    useEffect(() => {
        window.paypal
            .Buttons({
                createOrder: (data, actions) => {
                    return actions.order.create({
                        purchase_units: [
                            {
                                description: product.description,
                                amount: {
                                    currency_code: 'RUB',
                                    value: product.price,
                                },
                            },
                        ],
                    });
                },
                onApprove: async (data, actions) => {
                    const order = await actions.order.capture();
                    setPaidFor(true);
                    console.log(order);
                    let val = {
                        "sum": product.price,
                        "personalCode": product.accountNumber,
                        "serviceName": product.name,
                        "currAmount": product.lastValue,
                    };
                    const r = await (await fetch('/api/v1/pay/success', {
                        method: 'POST',
                        body: JSON.stringify(val),
                        headers: {
                            'Content-Type': 'application/json;charset=UTF-8'
                        }
                    })).json();
                },
                onError: err => {
                    setError(err);
                    console.error(err);
                },
            })
            .render(paypalRef.current);
    }, [product.description, product.price]);

    if (paidFor) {
        //this.onSuccess()
        return (
            <div>
                <h1>Ваш платеж принят, спасибо, что выбрали нас!</h1>
                <img alt={product.description} src={gif}/>
            </div>
        );
    }

    return (
        <div>
            {error && <div>Что-то пошло не так, обратитесь в банк! {error.message}</div>}
            <h1>
               Счет составит {product.price} рублей.
            </h1>
            {product.description}
            <div ref={paypalRef}/>
        </div>
    );
}

export class Payment extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            type: "",
            accountNumber: 0,
            lastValue: -1,
            currValue: 0,
            tarif: 0,
            stage: 1,
            isLoggedIn: cookies.get('isLoggedIn') === "true",
            pay: null,
        }
    }

    render() {
        return (

            <Page isLoggedIn={this.state.isLoggedIn} showModal={false}>
                <div>
                    <h2>Коммунальные платежи</h2>
                    <div className={s.subText}>У нас можно оплатить услуги ЖКХ: газ, электричество, водоснабжение</div>
                    <div className={s.columnsContainer}>
                        <div className={s.leftColumn}>
                            <div>
                                <div className={s.row}>
                                    <div className={cn(this.state.stage >= 1 ? s.greenCircle : s.greyCircle)}>1</div>
                                    <h4> Выберите оплачиваемую услугу</h4></div>
                                {this.state.stage === 1 ? <div className={s.buttonsBlock}>
                                    <button onClick={() => this.typeSelected("gas")}
                                            className={cn(this.state.type === "gas" ? s.blueButtonSelected : s.blueButton)}>Газ
                                    </button>
                                    <button onClick={() => this.typeSelected("electricity")}
                                            className={cn(this.state.type === "electricity" ? s.blueButtonSelected : s.blueButton)}>
                                        Электричество
                                    </button>
                                    <button onClick={() => this.typeSelected("water")}
                                            className={cn(this.state.type === "water" ? s.blueButtonSelected : s.blueButton)}>Вода
                                    </button>
                                    <label for="accountNumber">Введите номер счета</label>
                                    <input name="accountNumber" value={this.state.accountNumber}
                                           className={s.blueButton}
                                           disabled={this.state.isLoggedIn === true}
                                           onChange={(e) => this.changeValue("accountNumber", e)}/>
                                </div> : null}
                            </div>
                            <div>
                                <div className={s.row}>
                                    <div className={cn(this.state.stage >= 2 ? s.greenCircle : s.greyCircle)}>2</div>
                                    <h4> Введите данные платежа</h4></div>
                                {this.state.stage === 2 ? <div className={s.buttonsBlock}>
                                    <label htmlFor="lastValue">Последние показания</label>
                                    <input name="lastValue" value={this.state.lastValue} disabled={true}
                                           onChange={(e) => this.changeValue("lastValue", e)}/>
                                    <label htmlFor="currValue">Текущие показания</label>
                                    <input name="currValue" value={this.state.currValue}
                                           onChange={(e) => this.changeValue("currValue", e)}/>
                                </div> : null}
                            </div>
                            <div>
                                <div className={s.row}>
                                    <div className={cn(this.state.stage >= 3 ? s.greenCircle : s.greyCircle)}>3</div>
                                    <h4> Итого к оплате</h4></div>
                                {this.state.stage === 3 ? <div>
                                    <table className={s.mytable}>
                                        <tr>
                                            <th width="30%">Последние показания</th>
                                            <th width="30%">Текущие показания</th>
                                            <th width="20%">Тариф</th>
                                            <th width="20%">К оплате</th>
                                        </tr>
                                        <tr>
                                            <td>{this.state.lastValue}</td>
                                            <td>{this.state.currValue}</td>
                                            <td>{this.state.tarif}</td>
                                            <td>{this.calcTotal()}</td>
                                        </tr>
                                    </table>
                                </div> : null}
                            </div>
                            <div>
                                <div className={s.row}>
                                    <div className={cn(this.state.stage >= 4 ? s.greenCircle : s.greyCircle)}>4</div>
                                    <h4> Оплатите</h4></div>
                                {this.state.stage === 4 ?
                                    <Product product={this.state.pay}/> : null}
                            </div>
                        </div>
                        <div className={s.rightColumn}>
                            <img src="payment.png" width="210" height="240"/>
                            <button className={s.nextButton} onClick={() => this.nextButtonClicked()}>Далее</button>
                        </div>
                    </div>
                </div>
            </Page>
        );
    }

    async typeSelected(type) {
        this.setState({
            type: type,
        });
        let data = {
            "serviceName": type,
        };
        console.log(data)
        if (this.state.isLoggedIn) {
            const r = await (await fetch('/api/v1/pay/page_1', {
                method: 'POST',
                body: JSON.stringify(data),
                headers: {
                    'Content-Type': 'application/json;charset=UTF-8',
                    Authorization: `Bearer_${cookies.get('token')}`,
                }
            })).json();
            console.log(r);
            this.setState({
                accountNumber: r.personalCode,
            });
        }
    }

    async nextButtonClicked() {
        let curr_stage = this.state.stage;
        if (curr_stage === 1) {
            if (this.state.type === '') return;
            if (!await (this.checkAccountNumber())) return;
            await this.getLastValue();
            this.setState({
                stage: this.state.stage + 1,
            });

        }
        if (curr_stage === 2) {
            if (this.state.lastValue < 0 || this.state.currValue <= this.state.lastValue) return;
            this.setState({
                stage: this.state.stage + 1,
            })
        }
        if (curr_stage === 3) {

            if (this.state.tarif === 0) return;
            this.setState({
                stage: this.state.stage + 1,
                pay: {
                    price: this.calcTotal(),
                    name: this.state.type,
                    lastValue: this.state.lastValue,
                    accountNumber: this.state.accountNumber,
                    description: 'Нажмите для оплаты'
                }
            })
        }
        if (curr_stage === 4) {
            //сообщить успешно/нет
        }
    }


    checkAccountNumber = async () => {
        let data = {
            "personalCode": this.state.accountNumber,
            "serviceName": this.state.type,
        };
        if (!this.state.isLoggedIn) {
            const r = await (await fetch('/api/v1/pay/page_1', {
                method: 'POST',
                body: JSON.stringify(data),
                headers: {
                    'Content-Type': 'application/json;charset=UTF-8'
                }
            })).json();
            console.log(r);
            return r.status === "success"
        }
        return this.state.accountNumber > 0 && this.state.type //check real
    }

    getLastValue = async () => {
        let data = {
            "personalCode": this.state.accountNumber,
            "serviceName": this.state.type,
        };
        let self = this;

        const r = await (await fetch('/api/v1/pay/page_2', {
            method: 'POST',
            body: JSON.stringify(data),
            headers: {
                'Content-Type': 'application/json;charset=UTF-8'
            }
        })).json();
        console.log("Last value got")
        console.log(r);
        self.setState({
            lastValue: r.prevAmount,
            tarif: r.tariffCost,
        })


        // this.setState({
        //     lastValue: 0,
        //     tarif: 10,
        // })
    }

    changeValue = (field, e) => {
        this.setState({
            [field]: e.target.value
        });
    }

    calcTotal() {
        return Math.round((this.state.currValue - this.state.lastValue) * this.state.tarif * 100) / 100;
    }
}

