import React from "react";

export class AddUserModal extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            apartment: props.apartment,
        };
    }

    render() {
        return (
            <div>
                <h3>Укажите логин пользователя</h3>
                <br/>
                <label for="username">Логин</label>
                <input name="username" type="text" value={this.state.apartment.username} onChange={(e) => this.changeValue("username", e.target.value)}/>


                <button onClick={() => this.props.onSave(this.state.apartment.apartmentId, this.state.apartment)}>Сохранить</button>
                <button onClick={this.props.onCancel}>Отменить</button>
            </div>
        );
    }
    changeValue = (key, value) => {
        this.setState(state => {
            state.apartment[key] = value;
            return state;
        });
    }
}
