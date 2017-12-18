/**
 * Created by amaia.nazabal on 12/12/16.
 */

var DERNIER_ID_MESSAGE;

/**
 * Cette méthode récupere les messages du salon et appel la fonction succesGetMessages (voir plus bas) en cas de succes
 * et la fonction errorGetMessages (plus loin) sinon
 *
 */
function getMessagesBySalon() {
    var params = [];
    var salon = getQueryParams()['salon'];
    Ajax.sendGetRequest('../api/salons/' + salon, params, Ajax.JSON, succesGetMessagesBySalon, errorGetMessagesBySalon, true);
}

/**
 * Cette méthode génére le html pour montrer tous les messages, c'est celui-ci qui
 * initialise le timer
 *
 * @param data la réponse de la rêquete
 */
function succesGetMessagesBySalon(data) {
    var messages = JSON.parse(data);
    var i;

    $("#scroll_body").empty().append('<br>');

    for (i in messages.messages) {
        if (messages.messages.hasOwnProperty(i)) {
            addMessageHtml(messages.messages[i]);

            DERNIER_ID_MESSAGE = messages.messages[i].id;
        }
    }

    if (typeof i == 'undefined') {
        var salon = getQueryParams()['salon'];
        $("#scroll_body").append('<h4>Bienvenue au salon ' + salon + ', dorenavant vous avez la possibilité de ' +
            'discuter avec tous les membres du salon </h4>');
    } else {
        addDeleteEditHTML(DERNIER_ID_MESSAGE);
    }

    scrollWindows();
    $('#scroll_body').scrollTop(300);
    initTimer();
}

/**
 * Si le status line est 404 on ne fait rien sinon, on montre un erreur
 *
 * @param status status line de la réponse à la rêquete
 */
function errorGetMessagesBySalon(status) {
    if (status === Ajax.STATUS_NOT_FOUND) {
        var salon = getQueryParams()['salon'];
        $("#scroll_body").append('<h4>Bienvenue au salon ' + salon + ', dorenavant vous avez la possibilité de ' +
            'discuter avec tous les membres du salon </h4>');

        initTimer();
    } else {
        /* TODO traitement des erreurs */
    }
}


/**
 * Cette méthode appel le service addMessage du REST API pour ajouter un nouveau message dans le salon
 * Si il arrive a envoyer le message appel le methode succesAddMessage
 */
function addMessage() {
    if (checkCookie()) {
        if ($('#message-content').val().trim() != '') {

            var salon = getQueryParams()['salon'];
            var params = {
                id: 3,
                contenu: $('#message-content').val(),
                user: {
                    pseudo: getCookie("username")
                }
            };

            $("#loading").css('visibility', 'visible');
            Ajax.sendPostRequest('../api/salons/' + salon, params, Ajax.JSON, succesAddMessage, errorAddMessage, true, Ajax.JSON);
        } else{
            $('#message-content').focus();
        }
    }
}

/**
 * Si il est arrivé à ajouter correctemente le message, il recharge la page, pour mettre tout dans son lieu
 */
function succesAddMessage(data) {
    var message = JSON.parse(data);
    addMessageHtml(message);

    $("#message-content").val("");
    removeDeleteEditOption(message.id);

    $("#loading").css('visibility', 'hidden');
    $('#scroll_body').scrollTop(300);

    DERNIER_ID_MESSAGE = message.id;
}

function errorAddMessage() {
    $("#loading").css('visibility', 'hidden');
}

/**
 * Cette méthode appel le service getLastMessagesAfterId de l'API REST et demande d'envoyer
 * le résultat à la fonction succesGetLastMessagesAfterId (plus bas)
 *
 */
function getLastMessagesAfterId() {
    var salon = getQueryParams()["salon"];
    var params = [];

    if (typeof DERNIER_ID_MESSAGE != 'undefined') {
        Ajax.sendGetRequest('../api/salons/' + salon + '/' + DERNIER_ID_MESSAGE, params, Ajax.JSON, succesGetLastMessagesAfterId, error, true);
    } else {
        getMessagesBySalon();
    }
}

/**
 * Cette méthode ajoute les messages qui sont déjà dans le serveur et que le client ne l'a pas,
 * elle est appelé si le service getLastMessagesAfterId de l'API retournes des messages
 *
 * @param data les messages qui sont dans le server après de dernier id du message qui est dans le client
 */
function succesGetLastMessagesAfterId(data) {
    /* Si data est vide ça veut dire que la reponse est un 304 */
    if (data) {

        var messages = JSON.parse(data);
        var i;

        for (i in messages.messages) {
            if (messages.messages.hasOwnProperty(i)) {
                addMessageHtml(messages.messages[i]);
                DERNIER_ID_MESSAGE = messages.messages[i].id;
            }
        }

        if (typeof i != 'undefined') {
            removeDeleteEditOption(DERNIER_ID_MESSAGE);
        }
    }
}

/**
 * Cette méthode retourne le contenu d'un message donné en appelant le service getMessageById
 * de l'API REST
 *
 * @param id l'id du message
 */
function getMessageById(id) {
    var params = {};
    Ajax.sendGetRequest('../api/messages/' + id, params, Ajax.JSON, succesGetMessageById, error, true, Ajax.JSON);
}

/**
 * Cette méthode montre le contenu d'un message donné.
 *
 * @param data le message
 */
function succesGetMessageById(data) {
    var message = JSON.parse(data);
    $('#message-content-' + message.id).html(message.contenu);
    $('#link-' + message.id).remove();
}


/**
 * Cette méthode supprime le dernier message (si celui-ci c'est vraiment le dernier message)
 * en appelant le service deleteMessage du REST API, si la réponse est correcte elle appele le
 * méthode succesDeleteMessage (plus bas)
 */
function deleteMessage() {
    var salon = getQueryParams()["salon"];
    var params = [];

    Ajax.sendDeleteRequest('../api/messages/' + salon + '/' + DERNIER_ID_MESSAGE, params,
        Ajax.JSON, succesDeleteMessage, error, true);
}

/**
 * Si il est arrivé à supprimer correctemente le message, il recharge la page, pour mettre tout dans son lieu
 */
function succesDeleteMessage() {
    reloadPage();
}

/**
 * Cette méthode appel le service updateLastMessage du API REST en envoyant l'id du dernier message,
 * si la service a bien modifié le message appel le service succesUpdateLastMessage (plus bas)
 * @param id_message
 */
function updateLastMessage(id_message) {
    var salon = getQueryParams()["salon"];
    var new_username = $("#username").val();
    var content = $("#content_message").val();

    var params = {
        id: id_message,
        contenu: content,
        user: {
            pseudo: new_username
        }
    };

    Ajax.sendPutRequest('../api/messages/' + salon + '/' + id_message,
        params, Ajax.JSON, succesUpdateLastMessage, error, true, Ajax.JSON);
}

/**
 * Si il est arrivé à modifier correctemente le message, il recharge la page, pour mettre tout dans son lieu
 */
function succesUpdateLastMessage() {
    reloadPage();
}


/**
 * Cette methode cree le html pour ajouter les options de modifier et supprimer, laquelles normalement
 * doivent se montrer just dans le dernier message.
 *
 * @param id l'id du dernier message
 */
function addDeleteEditHTML(id) {
    $("#message_" + id + ' a').first().after('<a class="message_a last_option" href="#"' +
        'data-toggle="modal" data-target="#deleteMessage">Delete message</a>')
        .after('<a class="message_a last_option" href="javascript:editMessage(' + DERNIER_ID_MESSAGE + ')">Modifier message</a>');
}

/**
 * Cette methode supprime l'option de supprimer et modifier le dernier message et
 * reassigne les options au id envoyé
 *
 * @param id id du message auquel sera assigne les options de delete et update
 */
function removeDeleteEditOption(id) {
    $('#scroll_body div.block_message > a.last_option').each(function () {
        $(this).remove();
    });
    addDeleteEditHTML(id);
}


/**
 * Cette méthode montre un text-area pour que l'utilisateur puisse modifier le message
 *
 * @param id_message
 */
function editMessage(id_message) {
    var message = $("#message_" + id_message + ' h5').html();
    $("#message_" + id_message + ' h5').remove();
    $("#message_" + id_message + ' a').each(function () {
        $(this).remove();
    });

    $("#message_" + DERNIER_ID_MESSAGE).append('<textarea autofocus class="form-control" id="content_message" rows="2">'
        + message + '</textarea>')
        .append('<button type="button" onclick="updateLastMessage(' + id_message + ')" class=' +
            '"btn btn-primary btn-xs marge">Sauvegarder</button>')
        .append('<button type="button" onclick="succesUpdateLastMessage()" class="btn btn-default btn-xs marge">' +
            'Annuler</button>');
}

/**
 * Cette methode cree le html d'un message dans le div scroll_body
 *
 * @param message un message
 */
function addMessageHtml(message) {
    var html = '<div id="message_' + message.id + '" class="block_message triangle-border left">'
        + '<h4 class="user_user"><i class="glyphicon glyphicon-user"></i>'
        + message.user.pseudo + ' a dit :</h4>'
        + '<div class="date_user">' + message.hourFormatted + '</div>'
        + '<div style="clear:both"></div>'
        + addShowOption(message.id)
        + '<h5 class="message_user" id="message-content-' + message.id + '"></h5>'
        + '</div>';

    $("#scroll_body").append(html);
}

/**
 * Cette methode ajoute l'option pour montrer un message
 *
 * @param id l'id du message
 * @returns {string} le html qui doit creer
 */
function addShowOption(id) {
    return '<a class="message_a" href="javascript:getMessageById(' + id + ')" '
        + 'id="link-' + id + '">Voir le contenu</a>';
}