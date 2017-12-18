/**
 *
 *
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 12/12/16.
 */

/**
 *
 * Cette méthode appel le service getUser de l'API REST et demande d'envoyer le réponse à la
 * fonction succesGetUser (plus bas)
 */
function getUser() {
    var params = [];
    var pseudo = getQueryParams()["username"] || getCookie("username");

    if (typeof pseudo == 'undefined') {
        retournIndex();
    }

    Ajax.sendGetRequest('../api/users/' + pseudo, params, Ajax.JSON, succesGetUser, errorGetUser, true);
}

function errorGetUser() {
    retournIndex();
}

/**
 * Cette méthode montre les donnes de l'utilisateur et la liste de salons et créé une cookie avec le pseudo.
 *
 * @param data le resultat de la rêquete à getUser
 */
function succesGetUser(data) {
    var user = JSON.parse(data);

    /*On cree une cookie avec le pseudo de l'utilisateur*/
    setCookie('username', user.pseudo, 5);

    /* Profile information */
    $('h3[name=pseudo]').append(user.pseudo);
    $("p[name=donnees]").html(user.prenom + ' ' + user.nom + '<br/>' + user.mail);

    /* liste de salons */
    for (var i in user.salons) {
        if (user.salons.hasOwnProperty(i))
            $("div[name=liste_salons]").append('<a href="javascript:goChat(\'' + user.salons[i].name + '\')" class="list-group-item">'
                + user.salons[i].name + '</a>');
    }
}

/**
 * Cette méthode fait la rêquete au service de l'API REST modifyUserName
 * Si elle fini le méthode correctement appel succesModifyUsername (plus bas)
 */
function modifyUsername() {
    var new_username = $("#username").val();

    /* ici le nouveau pseudo*/
    var params = {
        pseudo: $('h3[name=pseudo]').html()
    };

    Ajax.sendPutRequest('../api/users/' + new_username, params, Ajax.JSON, succesModifyUsername, error, true, Ajax.JSON);
}

/**
 *
 * Cette méthode recharge la page avec un nouveau pseudo
 * et change le valeur de la cookie.
 *
 * @param data la réponse de la rêquete modifyUsername
 */
function succesModifyUsername(data) {
    var user = JSON.parse(data);
    $("#changeUsername").hide();
    setCookie("username", user.pseudo, 3);

    var newURL = window.location.protocol + "//" + window.location.host + "/" + window.location.pathname
        + '?username=' + user.pseudo;
    location.replace(newURL);
}