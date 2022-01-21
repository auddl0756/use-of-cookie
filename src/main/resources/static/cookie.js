let cookieObj = {};

(initCookiePage());

function initCookiePage() {
    cookieObj.sendNothing = function () {
        return fetch('/cookie', {
            method: 'post'
        }).then(response => {
            return response.ok;
        }).catch(error => (() => console.log(error)));
    };

    cookieObj.invalidateCookieRequest = function () {
        return fetch("/cookie", {
            method: 'delete'
        }).then(response => {
            return response.ok;
        }).catch(error => (() => console.log(error)));
    }

    cookieObj.sendCookieBtn = document.querySelector('#sendCookieBtn');
    cookieObj.invalidateCookieBtn = document.querySelector('#invalidateCookieBtn');

    cookieObj.sendCookieHandler = (
        () => {
            cookieObj.sendCookieBtn.addEventListener('click', cookieObj.sendNothing);
        }
    )

    cookieObj.invalidateCookieHandler = (() => cookieObj.invalidateCookieBtn.addEventListener('click', cookieObj.invalidateCookieRequest));

    cookieObj.sendCookieHandler();
    cookieObj.invalidateCookieHandler();

}
