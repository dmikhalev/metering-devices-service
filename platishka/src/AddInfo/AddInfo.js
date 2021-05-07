import {Page} from "../Page/Page";
import React from "react";


export class AddInfo extends React.Component {
    state = {
        about: null,
        news: null,
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
                <button onClick={() => this.changeType("about")}>О нас</button>
                <button onClick={() => this.changeType("news")}>Новости</button>
                <button onClick={() => this.changeType("faq")}>FAQ</button>
                <button onClick={() => this.changeType("docs")}>Документы</button>
                <br/>
                <button onClick={this.save}>Сохранить</button>
                <br/>
                <textarea onChange={this.changeText} value={text}/>
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
        const r = await (await fetch('http://34e583d93f48.ngrok.io/api/v1/info/all')).json();
        console.log(r);
        this.setState({
            [r[0].name]:r[0].text,
            [r[1].name]:r[1].text,
            [r[2].name]:r[2].text,
        });

    }

    save = () => {
        const data = {
            name: this.state.chapter,
            text: this.state[this.state.chapter],
        }
        const currPath = 'http://34e583d93f48.ngrok.io/'
        fetch(currPath + 'api/v1/info/1', {
            method: 'POST',
            body: JSON.stringify(data),
            headers: {
                'Content-Type':'application/json'
            }
        });
    }
}
