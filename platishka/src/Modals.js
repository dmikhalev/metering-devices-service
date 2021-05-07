import {Modal} from "./Modal/Modal";

let tarif_modal = (
    <form>
        <label>
            Введите данные:
            <input type="text" name="tarif" />
        </label>
        <input type="submit" value="Изменить" />
    </form>
);
let  users_modal = (
    <form>
        <label>
            ФИО:
            <input type="text" name="name" />
            Логин:
            <input type="text" name="name" disabled={true}/>
        </label>
    </form>
);

let text = this.state.currUser.name;
let modalRed = (
    <Modal>
        <label>
            ФИО:
            <input onChange={this.changeName} value={text}/>
        </label>
    </Modal>
);
changeName = (e) => {
    this.setState({
        currUser: {
            name: e.target.value
        }
    });
};

showEdit = (item) => {
    this.setState({
            showModalEdit: true,
            currUser: item,
        }
    )
}
