import React from "react";

export class UserModal extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            user: props.user,
            type: props.type,
        };
    }

    render() {
        return (
            <div>
                <h2>{this.state.type === "edit" ? "Редактировать пользователя" : "Создать пользователя"}</h2>
                <br/>
                <label for="name">ФИО</label>
                <input name="name" type="text" value={this.state.user.name}
                       onChange={(e) => this.changeValue("name", e.target.value)}/>
                <label for="username">Логин</label>
                <input name="username" type="text" value={this.state.user.username}
                       onChange={(e) => this.changeValue("username", e.target.value)}/>
                {this.state.type === "edit" ? null :
                    <div>
                        <label htmlFor="password">Пароль</label>
                        <input name="password" type="text" value={this.state.user.password}
                               onChange={(e) => this.changeValue("password", e.target.value)}/>
                    </div>}
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
