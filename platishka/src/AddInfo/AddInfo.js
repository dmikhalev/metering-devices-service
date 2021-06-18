import {Page} from "../Page/Page";
import React from "react";
import Cookies from "universal-cookie/es6";
import axios from "axios";
import s from "./AddInfo.module.css";

let cookie = new Cookies();

export class AddInfo extends React.Component {
    state = {
        about: null,
        news: "",
        faq: null,
        docs: null,
        chapter: "about",
    };

    render() {
        let text = this.state[this.state.chapter];
        if (text === null) {
            this.getData();
        }
        let content = (
            <div>
                <h2>Изменение информации</h2>
                <div className={s.container}>
                    <div className={s.buttonRow}>
                        <button className={s.blueButton} onClick={() => this.changeType("about")}>О нас</button>
                        <button className={s.blueButton} onClick={() => this.changeType("news")}>Новости</button>
                        <button className={s.blueButton} onClick={() => this.changeType("faq")}>FAQ</button>
                        <button className={s.blueButton} onClick={() => this.changeType("docs")}>Документы</button>
                    </div>
                    <div className={s.textContainer}>
                        <textarea rows="10" cols="250" onChange={this.changeText} value={text}/>
                    </div>
                    <br/>
                    <button onClick={this.save}>Сохранить</button>
                    <br/>
                </div>
            </div>
        );
        return (
            <Page>
                {content}
            </Page>
        );
    }

    changeType(chapter) {
        this.setState({
            chapter
        });
    }

    changeText = (e) => {
        this.setState({
            [this.state.chapter]: e.target.value
        });
    }

    async getData() {


        let auth = 'Bearer_' + cookie.get('token');
        console.log(auth);
        const config = {
            headers: {Authorization: `Bearer_${cookie.get('token')}`}
        };
        let self = this;

        axios.get('/api/v1/info/all', config)
            .then(function (response) {
                console.log(response);
                if (response.status === 200) {
                    if (response.data) {
                        const r = response.data;
                        self.setState({
                            [r[0].name]: r[0].text,
                            [r[1].name]: r[1].text,
                            [r[2].name]: r[2].text,
                        });
                    }
                }
            })
            .catch(function (error) {
                console.log(error);
            });

    }

    save = () => {
        const data = {
            name: this.state.chapter,
            text: this.state[this.state.chapter],
        }
        let auth = 'Bearer_' + cookie.get('token');
        let path = "/api/v1/admin/info/";
        if (this.state.chapter == "news") {
            path = "/api/v1/info/news"
        }
        fetch(path, {
            method: 'POST',
            body: JSON.stringify(data),
            headers: {
                'Authorization': auth,
                'Content-Type': 'application/json'
            }
        });
    }
}
