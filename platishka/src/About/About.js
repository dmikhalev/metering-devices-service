import {Page} from "../Page/Page";
import s from './About.module.css';
import React from 'react';

export class About extends React.Component {
    state = {
        about: null,
    };

    render() {
        let text = this.state.about
        if (text === null) {
            this.getData();
        }
        return (
            <Page isLoggedIn={false}>
                <React.Fragment>
                    <div className={s.title}>
                        <h3>О нас</h3>
                    </div>
                    {text}
                </React.Fragment>
            </Page>
        );
    }

    async getData() {
        const r = await (await fetch('/api/v1/info/about')).json();
        console.log(r);
        this.setState({
            about: r.text,
        });
    }

}
