import {Page} from "../Page/Page";
import s from './Home.module.css';
import React from "react";
import {Card} from "../Card/Card";
import {PayCardIcon} from "../PayCardIcon/PayCardIcon";
import {Profile} from "../Profile/Profile";
import {ProfileIcon} from "../ProfileIcon/ProfileIcon";
import {History} from "../History/History";
import {HistoryIcon} from "../HistoryIcon/HistoryIcon";
import {DebtIcon} from "../DebtIcon/DebtIcon";
import {Link} from "react-router-dom";
import {FlatIcon} from "../FlatIcon";
import {UsersIcon} from "../UsersIcon";
import {InfoIcon} from "../InfoIcon";
import {TarifIcon} from "../TarifIcon";

export function Home({loggedIn, isAdmin}) {
    let content = (
        <React.Fragment className={s.straight}>
            <div className={s.title}>
                <h3>Условия получения услуги на сайте</h3>
            </div>
            <p>
                Услуга позволяет получить сведения о текущих выставленных платежных документах и общей сумме
                задолженности
                по
                оплате коммунальных платежей, в том числе в разрезе расчетных периодов, а также оплачивать
                коммунальные
                услуги
                через интернет.

                Для получения услуги нужно заполнить код плательщика, указанный в едином платежном документе и
                указать
                номер
                квартиры.
                Пожалуйста, авторизируйтесь, если Вам пришло письмо о том, что ваш дом участвует в данной программе.
            </p>
        </React.Fragment>

    );
    if (loggedIn) {
        content = (
            <section className={s.table}>
                <div className={s.column}>
                    <div className={s.superLink}>
                        <Link to="/pay">
                            <Card title="Оплатить квитанцию">
                                <PayCardIcon/>
                            </Card>
                        </Link>
                    </div>
                    <div className={s.superLink}>
                        <Link to="/history">
                            <Card title="История платежей">
                                <HistoryIcon/>
                            </Card>
                        </Link>
                    </div>
                </div>
                <div className={s.column}>
                    <div className={s.superLink}>
                        <Link to="/profile">
                            <Card title="Личная информация">
                                <ProfileIcon/>
                            </Card>
                        </Link>
                    </div>

                    <div className={s.superLink}>
                        <Link to="/debt">
                            <Card title="Задолженности">
                                <DebtIcon/>
                            </Card>
                        </Link>
                    </div>
                </div>

            </section>
        )
    }
    if (isAdmin) {
        content = (
            <section className={s.table}>
                <div className={s.column}>
                    <div className={s.superLink}>
                        <Link to="/admin/apartments">
                            <Card title="Дома, квартиры">
                                <FlatIcon/>
                            </Card>
                        </Link>
                    </div>
                    <div className={s.superLink}>
                        <Link to="/admin/info">
                            <Card title="Добавить информацию">
                                <InfoIcon/>
                            </Card>
                        </Link>
                    </div>
                </div>
                <div className={s.column}>
                    <div className={s.superLink}>
                        <Link to="/admin/users">
                            <Card title="Пользователи">
                                <UsersIcon/>
                            </Card>
                        </Link>
                    </div>

                    <div className={s.superLink}>
                        <Link to="/admin/tarifs">
                            <Card title="Тарифы">
                                <TarifIcon/>
                            </Card>
                        </Link>
                    </div>
                </div>
            </section>
        )
    }

    return (
        <Page isLoggedIn={true}>
            {content}
        </Page>
    );

}
