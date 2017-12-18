/**
 * @author sofiaa faddi
 * @version 1.0
 * @since 1.0 09/12/2016.
 *
 * TODO check cookie
 */
var Username = "";

function succes(data) {
    if (data.search("Inscrivez vous en remplissons ce formulaire") != -1) {
        var inscription_ajax = confirm("Vous allez etre redirigé vers la page d'inscription , cliquer sur confirmer");
        if (inscription_ajax != false) {
            window.location = "../inscription.jsp";
        }
        else {
            $('.cn_pr').append("Cliquer sur le lien pour vous inscrire <a href='../inscription.jsp' class='btn btn-primary but' >Inscription</a> ")
        }
    } else {
        Username = document.getElementById("name").value;
        window.location = "../AjaxHtml/profil.html?username=" + Username;
    }
}

/**
 * Se deplace a la page chat dans le salon indiqué
 *
 * @param salon le nom du salon
 */
function goChat(salon) {
    var newURL = getPath() + 'chat.html?salon=' + salon;
    location.replace(newURL);
}

/**
 * Il initialise le timer pour s'executer chaque 5 seconds, en appelant
 * la fonction getLastMessagesAfterId (fichier message.js)
 */
function initTimer() {
    setInterval(getLastMessagesAfterId, 5000);
}

function error() {
    console.log("error");
}

function login() {
    var newURL = getPath() + 'profil.html?username=' + document.getElementById("name").value;
    location.replace(newURL);
}

function logout() {
    var newURL = getPath();
    /* On supprime la cookie de l'utilisateur*/
    setCookie('username', '', -5);
    location.replace(newURL);
}

/**
 * Cette fonction créé un nouveau salon quand l'utilisateur en appelant
 * le service de l'API REST qui de plus associé le salon avec l'utilisateur.
 * Si cette fonction fini correctement, elle appel la fonction successNewChannel (plus bas)
 */
function newChannel() {
    var params = [];
    params.push({key: "salon", value: $("#name_salon").val()});
    params.push({key: "pseudo", value: getCookie("username")});

    Ajax.sendPostRequest('../api/salons', params, Ajax.JSON, successNewChannel, errorNewChannel, true, Ajax.FORM_URL_ENCODE);
}

/**
 * Cette méthode récupere le nom du salon et envoie à l'utilisateur à la nouvelle page
 * pour voir les messages en appelant la fonction goChat
 */
function successNewChannel() {
    var salon = $("#name_salon").val();
    goChat(salon);
}

/**
 * Si le salon déjà exists il retourne error, donc on envoie à l'utilisateur vers le forum de ce salon
 */
function errorNewChannel() {
    var salon = $("#name_salon").val();
    goChat(salon);
}

/**
 * Cette fonction verifie que change la source du iframe pour envoyer le salon
 */
function verifierURL() {
    if (checkCookie()) {
        var path = getPath() + 'affichage.html?salon=';
        var param = getQueryParams();
        $('#messages').attr('src', path + param['salon'])
    }
}

/**
 *
 */
function scrollWindows() {
    var contents = $('#scroll_body').height();
    $(".bgelement").scrollTop(contents);
}