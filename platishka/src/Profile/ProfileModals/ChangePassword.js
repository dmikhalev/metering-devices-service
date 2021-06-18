import React from "react";

export class ChangePassword extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            user: props.user,
        };
    }


    render() {
        return (
            <div>
                <h2>Сменить пароль</h2>
                <br/>
                <label htmlFor="oldPassword">Старый пароль</label>
                <input name="oldPassword" type="text" value={this.state.user.oldPassword}
                       onChange={(e) => this.changeValue("oldPassword", e.target.value)}/>
                <label htmlFor="newPassword">Новый пароль</label>
                <input name="newPassword" type="text" value={this.state.user.newPassword}
                       onChange={(e) => this.changeValue("newPassword", e.target.value)}/>
                <label htmlFor="repeatPassword">Повторите новый пароль</label>
                <input name="repeatPassword" type="text" value={this.state.user.repeatPassword}
                       onChange={(e) => this.changeValue("repeatPassword", e.target.value)}/>

                <button
                    onClick={() => this.props.onSave(this.state.user)}>Сохранить
                </button>
                <button onClick={this.props.onCancel}>Отменить</button>
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
