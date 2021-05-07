import {Page} from "../Page/Page";
import s from './News.module.css';
import React from 'react';


export class News extends React.Component {
    state = {
        news: [],
    };

    render() {
        let text = this.state.news
        if (text.length === 0) {
            this.getData();
        }
        return (
            <Page isLoggedIn={false}>
                <React.Fragment>
                    <div className={s.title}>
                        <h3>Новости</h3>
                    </div>
                    {
                        this.state.news.map((item => (
                            <p>
                                {item}
                            </p>
                        )))
                    }
                </React.Fragment>
            </Page>
        );
    }

    async getData() {
        const currPath = 'http://e06b8e648d8c.ngrok.io/'
        const r = await (await fetch(currPath + 'api/v1/info/news/7')).json();
        console.log(r);
        this.setState({
            news: r.map((item) => item.text),
        });
    }

}
