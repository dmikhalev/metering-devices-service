import React from "react";

export class TarifModal extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            curr_value: props.curr_value,
        };
    }
    render() {
        return (
            <div>
                <label>Изменить тариф</label>
                <input type="text" value={this.state.curr_value} onChange={this.changeValue}/>
                <button onClick={() => this.props.onSave(this.props.id, this.state.curr_value)}>Сохранить</button>
                <button onClick={this.props.onCancel}>Отменить</button>
            </div>
        );
    }

    changeValue = (e) => {
        this.setState({
            curr_value: e.target.value
        });
    }
}
