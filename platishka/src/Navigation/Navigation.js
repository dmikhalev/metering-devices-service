import {HomeIcon} from "../HomeIcon/HomeIcon";
import {Button} from "../Button/Button";
import s from './Navigation.module.css';
import {Link} from "react-router-dom";

export function Navigation({isLoggedIn}) {
    return (
        <article className={s.navigation}>
            <section className={s.navContainer}>
                <Link to="/">

                    <HomeIcon width="30" height="30"/>
                </Link>
                <div className={s.navSection}>
                    <Link to="/about">
                        <Button text="О Нас" color="white"/>
                    </Link>
                    <Link to="/news">
                        <Button text="Новости" color="white"/>
                    </Link>
                    <Link to="/faq">
                        <Button text="FAQ" color="white"/>
                    </Link>
                    <Link to="docs">
                        <Button text="Документы" color="white"/>
                    </Link>
                </div>
                <Button text={isLoggedIn ? "Выход" : "Вход"} color="red"/>
            </section>
        </article>
    );
}
