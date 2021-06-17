import React from "react";
import s from "../Apartment.module.css"

export class AddApartment extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            apartment: props.apartment,
        };
    }

    render() {
        return (
            <div>
                <h2>Привязать счета к квартире</h2>
                <br/>
                <label htmlFor="buildingCity">Город</label>
                <input name="buildingCity" type="text" value={this.state.apartment.buildingCity}
                       onChange={(e) => this.changeValue("buildingCity", e.target.value)}/>
                <label htmlFor="buildingStreet">Улица</label>
                <input name="buildingStreet" type="text" value={this.state.apartment.buildingStreet}
                       onChange={(e) => this.changeValue("buildingStreet", e.target.value)}/>
                <label htmlFor="buildingNumber">Дом</label>
                <input name="buildingNumber" type="text" value={this.state.apartment.buildingNumber}
                       onChange={(e) => this.changeValue("buildingNumber", e.target.value)}/>
                <label htmlFor="apartmentNumber">Квартира</label>
                <input name="apartmentNumber" type="text" value={this.state.apartment.apartmentNumber}
                       onChange={(e) => this.changeValue("apartmentNumber", e.target.value)}/>
                <div className={s.row}>
                    <div className={s.column}>
                        <label htmlFor="gas">Газ</label>
                        <input name="gas" type="text" value={this.state.apartment.gas}
                               onChange={(e) => this.changeValue("gas", e.target.value)}/>
                    </div>
                    <div className={s.column}>
                        <label htmlFor="gasLastValue">Текущее показание: газ</label>
                        <input name="gasLastValue" type="text" value={this.state.apartment.gasLastValue}
                               onChange={(e) => this.changeValue("gasLastValue", e.target.value)}/>
                    </div>
                </div>
                <div className={s.row}>
                    <div className={s.column}>
                        <label htmlFor="water">Вода</label>
                        <input name="water" type="text" value={this.state.apartment.water}
                               onChange={(e) => this.changeValue("water", e.target.value)}/>
                    </div>
                    <div className={s.column}>
                        <label htmlFor="waterLastValue">Текущие показатели: вода</label>
                        <input name="waterLastValue" type="text" value={this.state.apartment.waterLastValue}
                               onChange={(e) => this.changeValue("waterLastValue", e.target.value)}/>
                    </div>
                </div>
                <div className={s.row}>
                    <div className={s.column}>
                        <label htmlFor="electro">Электричество</label>
                        <input name="electro" type="text" value={this.state.apartment.electro}
                               onChange={(e) => this.changeValue("electro", e.target.value)}/>
                    </div>
                    <div className={s.column}>
                        <label htmlFor="electroLastValue">Текущие показатели: электричество</label>
                        <input name="electroLastValue" type="text" value={this.state.apartment.electroLastValue}
                               onChange={(e) => this.changeValue("electroLastValue", e.target.value)}/>
                    </div>
                </div>
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
