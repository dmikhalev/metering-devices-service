import React from "react";

export class EditApartment extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            apartment: props.apartment,
        };
    }

    render() {
        return (
            <div>

                <h2>Изменить номера счетов</h2>
                <br/>
                <label htmlFor="buildingStreet">Улица</label>
                <input name="buildingStreet" type="text" value={this.state.apartment.buildingStreet} disabled={true}/>
                <label htmlFor="buildingNumber">Дом</label>
                <input name="buildingNumber" type="text" value={this.state.apartment.buildingNumber} disabled={true}/>
                <label htmlFor="apartmentNumber">Квартира</label>
                <input name="apartmentNumber" type="text" value={this.state.apartment.apartmentNumber} disabled={true}/>
                <label htmlFor="gas">Газ</label>
                <input name="gas" type="text" value={this.state.apartment.gas}
                       onChange={(e) => this.changeValue("gas", e.target.value)}/>
                <br/>
                <label htmlFor="water">Вода</label>
                <input name="water" type="text" value={this.state.apartment.water}
                       onChange={(e) => this.changeValue("water", e.target.value)}/>
                <br/>
                <label htmlFor="electro">Электричество</label>
                <input name="electro" type="text" value={this.state.apartment.electro}
                       onChange={(e) => this.changeValue("electro", e.target.value)}/>

                <button
                    onClick={() => this.props.onSave(this.state.apartment.apartmentId, this.state.apartment)}>Сохранить
                </button>
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
