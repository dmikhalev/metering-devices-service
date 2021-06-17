import './App.css';
import React, { useState, useRef, useEffect } from 'react';
import chair from './chair.png';
import gif from './giphy.gif';
import {Page} from "../Page/Page";

function Product({ product }) {
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
                },
                onError: err => {
                    setError(err);
                    console.error(err);
                },
            })
            .render(paypalRef.current);
    }, [product.description, product.price]);

    if (paidFor) {
        return (
            <div>
                <h1>Congrats, you just bought {product.name}!</h1>
                <img alt={product.description} src={gif} />
            </div>
        );
    }

    return (
        <div>
            {error && <div>Uh oh, an error occurred! {error.message}</div>}
            <h1>
                {product.description} for ${product.price}
            </h1>
            <img alt={product.description} src={product.image} width="200" />
            <div ref={paypalRef} />
        </div>
    );
}

function Appi() {
    const product = {
        price: 7.77,
        name: 'comfy chair',
        description: 'fancy chair, like new',
        image: chair,
    };

    return (
        <Page>
            <Product product={product} />
        </Page>
    );
}

export default Appi;
