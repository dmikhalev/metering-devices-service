import {Page} from "../Page/Page";
import s from './Docs.module.css';
import React from 'react';
export class Docs extends React.Component {
    state = {
        docs: null,
    };

    render() {
        let text = this.state.docs
        if (text === null) {
            this.getData();
        }
        return (
            <Page isLoggedIn={false}>
                <React.Fragment>
                    <div className={s.title}>
                        <h3>Документы</h3>
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
        const r = await (await fetch(currPath + 'api/v1/info/docs')).json();
        console.log(r);
        this.setState({
            docs: r.text,
        });
    }

}
