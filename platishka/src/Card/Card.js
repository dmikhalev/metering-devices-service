import s from './Card.module.css';
export function Card({children, title}) {
    return (
        <div className={s.card}>
            <div>
                {children}
            </div>
            <div>
                {title}
            </div>
        </div>
    );
}
