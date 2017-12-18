/**
 *
 * Library for ajax request
 * Methodes includes:
 *
 * POST
 * GET
 * PUT
 * DELETE
 *
 * @author amaia.nazabal
 * @since 12/7/16.
 * @version 1.0
 */
var Ajax  = {
    JSON : 'JSON',
    XML : 'XML',
    FORM_URL_ENCODE : 'FORM_URL_ENCODE',
    STATUS_OK : 200,
    STATUS_CREATED : 201,
    STATUS_NOT_CONTENT : 204,
    STATUS_NOT_MODIFIED : 304,
    STATUS_NOT_FOUND: 404,
    STATUS_INTERNAL_SERVER_ERROR : 500,

    /*
     * Function for post request
     *
     * @param url = l'url qui ont veut appeler'/connect/{id}'
     * @param params = [{"key": "key", "value" : "value"},
     *                  {"key": "key", "value" : "value"}];
     * @param accept : JSON
     * @param callback_succes : le nom du function qui va appeler si le post atteindre
     * @param callback_error : le nom du function qui va appeler si le post rate
     * @param asynchron : true ou false
     *
     * */

    sendPostRequest: function (url, params, accept, callback_succes, callback_error, asynchron, type) {
        type = type || Ajax.FORM_URL_ENCODE;
        var headers = [];
        headers.push(this.getContentHeader(type));
        headers.push(this.getAcceptHeader(accept));

        this.sendAjaxRequest("POST", params, url, headers, callback_succes, callback_error, asynchron, type);
    },

    /*
     * Function for get request
     *
     * @param url = l'url qui ont veut appeler'/connect/{id}'
     * @param params = [{"key": "key", "value" : "value"},
     *                  {"key": "key", "value" : "value"}];
     * @param accept : JSON
     * @param callback_succes : le nom du function qui va appeler si le post atteindre
     * @param callback_error : le nom du function qui va appeler si le post rate
     * @param asynchron : true ou false
     *
     * */

    sendGetRequest : function(url, params, accept, callback_succes, callback_error, asynchron) {
        var headers = [];
        headers.push(this.getAcceptHeader(accept));
        this.sendAjaxRequest("GET", params, url, headers, callback_succes, callback_error, asynchron);
    },

    /*
     * Function for PUT request
     *
     * @param url = l'url qui ont veut appeler'/connect/{id}'
     * @param params = [{"key": "key", "value" : "value"},
     *                  {"key": "key", "value" : "value"}];
     * @param accept : JSON
     * @param callback_succes : le nom du function qui va appeler si le post atteindre
     * @param callback_error : le nom du function qui va appeler si le post rate
     * @param asynchron : true ou false
     *
     * */

    sendPutRequest : function(url, params, accept, callback_succes, callback_error, asynchron, type) {
        type = type || Ajax.FORM_URL_ENCODE;
        var headers = [];
        headers.push(this.getContentHeader(type));
        headers.push(this.getAcceptHeader(accept));

        this.sendAjaxRequest("PUT", params, url, headers, callback_succes, callback_error, asynchron);
    },

    /*
     * Function for DELETE request
     *
     * @param url = l'url qui ont veut appeler'/connect/{id}'
     * @param params = [{"key": "key", "value" : "value"},
     *                  {"key": "key", "value" : "value"}];
     * @param accept : JSON
     * @param callback_succes : le nom du function qui va appeler si le post atteindre
     * @param callback_error : le nom du function qui va appeler si le post rate
     * @param asynchron : true ou false
     *
     * */

    sendDeleteRequest : function (url, params, accept, callback_succes, callback_error, asynchron) {
        var headers = [];
        headers.push(this.getAcceptHeader(accept));

        this.sendAjaxRequest("DELETE", params, url, headers, callback_succes, callback_error, asynchron);
    },

    /*
     * Function AJAX request
     *
     * @param method = POST ou GET ou DELETE ou PUT
     * @param data = les parametres avec ce format:
     *                 [{"key": "key", "value" : "value"},
     *                  {"key": "key", "value" : "value"}];
     * @param headers : les header qu'on veut envoyer
     * @param callback_succes : le nom du function qui va appeler si le post atteindre
     * @param callback_error : le nom du function qui va appeler si le post rate
     * @param asynchron : true ou false
     *
     * */
    sendAjaxRequest : function (method, data, url, headers, callback_succes, callback_error, asynchron, type) {
        var xmlhttp = new XMLHttpRequest();
        type = type || this.URL_ENCODE;


        xmlhttp.onreadystatechange = function () {
           if (xmlhttp.readyState == XMLHttpRequest.DONE) {
              var STATUS_ACCEPTED = [Ajax.STATUS_OK, Ajax.STATUS_CREATED, Ajax.STATUS_NOT_CONTENT, Ajax.STATUS_NOT_MODIFIED];
             if (STATUS_ACCEPTED.indexOf(xmlhttp.status) >= 0) {
                   var data = xmlhttp.responseText;
                 callback_succes(data);
               } else {
                    callback_error(xmlhttp.status);
                }
           }
      };

        /* On l'appel de manière asyncrone */
        xmlhttp.open(method, url, asynchron);

        for (var i in headers) {
            xmlhttp.setRequestHeader(headers[i].header, headers[i].value);
        }

        var params = '';

        if (type == Ajax.FORM_URL_ENCODE) {
            var first = false;

            for (var j in data) {
                if (first === true)
                    params += '&';
                params += data[j].key + "=" + data[j].value;
                first = true;
                console.log(params);
            }

            if (params)
                params = encodeURI(params);
        } else {
            params = JSON.stringify(data);
        }

        xmlhttp.send(params);
    },

    /*
     * Retourne le header pour accepter soit JSON soit XML
     *
     * @param accept: JSON ou XML
     * */
    getAcceptHeader : function (accept) {
        var obj;
        if (accept === Ajax.JSON) {
            obj = {header: "accept", value: "application/json"};
        } else {
            obj = {header: "accept", value: "application/xml"};
        }

        return obj;
    },

    /*
     * Retourne le header pour content type soit FORM_URL_ENCODE soit JSON soit XML
     *
     * @param type: FORM_URL_ENCODE ou JSON ou XML
     * */
    getContentHeader : function (type) {
        var obj;
        if (type == Ajax.FORM_URL_ENCODE){
            obj = {"header": "Content-Type", "value": "application/x-www-form-urlencoded"};
        } else if (type == Ajax.JSON) {
            obj = {"header": "Content-Type", "value": "application/json"};
        } else {
            obj = {"header": "Content-Type", "value": "application/xml"};
        }

        return obj;
    }
};
