import {Page} from "../Page/Page";
import s from './FAQ.module.css';
import React from 'react';
export class FAQ extends React.Component {
    state = {
        faq: null,
    };

    render() {
        let text = this.state.faq
        if (text === null) {
            this.getData();
        }
        return (
            <Page isLoggedIn={false}>
                <React.Fragment>
                    <div className={s.title}>
                        <h3>FAQ</h3>
                    </div>
                    <p>
                        {text}
                    </p>
                </React.Fragment>
            </Page>
        );
    }

    async getData() {
        const currPath = 'http://e06b8e648d8c.ngrok.io/'
        const r = await (await fetch(currPath + 'api/v1/info/faq')).json();
        console.log(r);
        this.setState({
            faq: r.text,
        });
    }

}
