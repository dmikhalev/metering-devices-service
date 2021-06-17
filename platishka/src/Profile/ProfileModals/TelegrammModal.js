import React from "react";

export class TelegrammModal extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            link: props.link,
        };
    }


    render() {
        return (
            <div>
                <h2>Телеграмм</h2>
                <br/>
                <label htmlFor="oldPassword">Ссылка на телеграмм-бота</label>
                <input name="oldPassword" type="text" value={this.state.link}/>

                <button onClick={this.props.onCancel}>Закрыть</button>
            </div>
        );
    }

    changeValue = (key, value) => {
        this.setState(state => {
            state.user[key] = value;
            return state;
        });
    }
}
