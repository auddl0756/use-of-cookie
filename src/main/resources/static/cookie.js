let cookieObj = {};

(initCookiePage());

function initCookiePage() {
    cookieObj.sendNothing = function () {
        return fetch('/cookie', {
            method: 'post'
        }).then(response => {
            return response.ok;
        }).catch(error => (() => console.log(error)));
    }

    cookieObj.sendCookieBtn = document.querySelector('#sendCookieBtn');

    cookieObj.sendCookieHandler = (
        () => {
            cookieObj.sendCookieBtn.addEventListener('click', cookieObj.sendNothing);
        }
    )

    cookieObj.sendCookieHandler();
}
