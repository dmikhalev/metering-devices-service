import './App.css';
import {BrowserRouter, Switch, Route} from "react-router-dom";
import {Home} from "./Home/Home";
import {News} from "./News/News";
import {FAQ} from "./FAQ/FAQ";
import {About} from "./About/About";
import {Docs} from "./Docs/Docs";
import {Profile} from "./Profile/Profile";
import {Debt} from "./Debt/Debt";
import {History} from "./History/History";
import {Login} from "./Login/Login";
import {Users} from "./Users/Users";
import {TarifIcon} from "./TarifIcon";
import {TarifPage} from "./TarifPage/TarifPage";
import {ApartmentCrud} from "./ApartmentCRUD/ApartmentCrud";
import {AddInfo} from "./AddInfo/AddInfo";
import Cookies from 'universal-cookie';
import {Payment} from "./Payment/Payment";
import Appi from "./TryPay/Appi";

function App() {
    return (
        <BrowserRouter>
            <Switch>
                <Route exact={true} path="/">
                    <Home/>
                </Route>
                <Route path="/news">
                    <News/>
                </Route>
                <Route path="/about">
                    <About/>
                </Route>
                <Route path="/faq">
                    <FAQ/>
                </Route>
                <Route path="/docs">
                    <Docs/>
                </Route>
                <Route path="/profile">
                    <Profile/>
                </Route>
                <Route path="/history">
                    <History/>
                </Route>
                <Route path="/debt">
                    <Debt/>
                </Route>
                <Route path="/login">
                    <Login/>
                </Route>
                <Route path="/admin/tarifs">
                    <TarifPage/>
                </Route>
                <Route path="/admin/users">
                    <Users/>
                </Route>
                <Route path="/admin/apartments">
                    <ApartmentCrud/>
                </Route>
                <Route path="/admin/info">
                    <AddInfo/>
                </Route>
                <Route path="/payment">
                    <Payment/>
                </Route>
                <Route path="/test_pay">
                    <Appi/>
                </Route>

            </Switch>
        </BrowserRouter>
    );
}

export default App;
