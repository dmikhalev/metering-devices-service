import s from './Page.module.css';
import {Navigation} from "../Navigation/Navigation";
import {Button} from "../Button/Button";
import * as cn from "classnames";
import {Modal} from "../Modal/Modal";
import React from 'react';
export function Page({children, isLoggedIn, showModal}) {
    children = React.Children.toArray(children);
    const payButton = (
        <Button color="red" text="Оплатить онлайн" width={150} height={35}/>
    );
    const modal = (
        <Modal>
            {children[1]}
        </Modal>
    );
    return (
        <div className={s.pageContainer}>
            <header>
                <Navigation isLoggedIn={isLoggedIn}/>
            </header>
            <article className={s.bodyStyle}>
                <div className={s.payButtonContainer}>{isLoggedIn ? null : payButton} </div>
                <section className={cn(s.mainWindow, isLoggedIn ? s.mainWindowCenter : null)}>
                    {children[0]}
                </section>
            </article>
            {showModal ? modal : null}

        </div>
    );
}
