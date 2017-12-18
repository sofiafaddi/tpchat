/**
 * Created by amaia.nazabal on 12/12/16.
 */



/**
 *
 * Cette methode nettoye l'url
 *
 * @param url l'url de l'application
 * @returns {string} enlève les données des pages et laisse uniquement l'addresse relative
 */
function cleanURL(url) {
    return(url.replace(/\?.*$/, "").replace(/\/[^\/]*$/, "") + "/");
}

/**
 * Cette methode retourne l'addresse de l'application, l'addresse relative
 *
 * @returns {string}
 */
function getPath() {
    return cleanURL(window.location.protocol + "//" + window.location.host + window.location.pathname);
}

/**
 *
 * Fonction pour récuperer le valeur d'un parametre du link.
 *
 * Par exemple:
 * http:/localhost/chat?salon=name&message=content retourne:
 *
 * ['salon'] = name
 * ['message'] = content
 *
 * Source: http://stackoverflow.com/questions/979975/how-to-get-the-value-from-the-get-parameters#1099670
 * @returns {{}} un objet avec tous les parametres du link
 */
function getQueryParams() {
    var qs = window.location.search;
    qs = qs.split('+').join(' ');

    var params = {},
        tokens,
        re = /[?&]?([^=]+)=([^&]*)/g;

    while (tokens = re.exec(qs)) {
        params[decodeURIComponent(tokens[1])] = decodeURIComponent(tokens[2]);
    }

    return params;
}

/**
 * Fonction pour récuperer une cookie
 * Source: http://www.w3schools.com/js/js_cookies.asp
 *
 * @param cookie_name le nom de la cookie
 * @returns {*} le valeur de la cookie
 */
function getCookie(cookie_name) {
    var name = cookie_name + "=";
    var ca = document.cookie.split(';');
    for(var i = 0; i <ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length,c.length);
        }
    }
    return undefined;
}

/**
 * Fonction pour générer une cookie
 * Source: http://www.w3schools.com/js/js_cookies.asp
 *
 * @param cookie_name le nom de la cookie
 * @param cookie_value le valeur de la cookie
 * @param minutes les minutes avant d'expirer
 */
function setCookie(cookie_name, cookie_value, minutes) {
    var d = new Date();
    d.setTime(d.getTime() + (minutes*60*1000));
    var expires = "expires="+ d.toUTCString();
    document.cookie = cookie_name + "=" + cookie_value + ";" + expires + ";path=/";
}

/**
 * Cette méthode recharge la page
 */
function reloadPage(){
    location.reload()
}

/**
 * Cette methode redirige vers l'index
 */
function retournIndex() {
    location.replace(getPath());
}

/**
 * Cette méthode vérifie que la cookie est déjà crée dans la page
 * Source: http://www.w3schools.com/js/js_cookies.asp
 *
 * @returns {boolean} si la cookie a un valeur
 */
function checkCookie() {
    var username = getCookie("username");

    if (username != '' && typeof username != 'undefined') {
        return true;
    } else {
        retournIndex();
    }
    return false;
}