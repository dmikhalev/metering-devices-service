import s from './Button.module.css';
import * as cn from "classnames";

export function Button({color, text, width, height}) {
    return (
        <button style={{width, height}} className={cn(s.button, color === "red" ? s.redButton : s.whiteButton)}>
            {text}
        </button>
    );
}

