import s from "./Modal.module.css";
import React from 'react';
export function Modal({children}) {
    return (
        <React.Fragment>
            <div className={s.modalBlur}/>
            <div className={s.modalContainer}>
                <div className={s.modal}>
                    {children}
                </div>
            </div>
        </React.Fragment>
    );
}

