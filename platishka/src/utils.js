export function delay(mills) {
    return new Promise((res) => {
        setTimeout(res, mills);
    });
}
