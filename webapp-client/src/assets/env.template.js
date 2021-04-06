(function(window) {
    window.env = window.env || {};
  
    // Environment variables
    window["env"]["apiUrl"] = "${SERVER_API_URL}";
    window["env"]["clientId"] = "${CLIENT_ID}";
    window["env"]["authority"] = "${AUTHORITY_URL}";
    window["env"]["redirectUrl"] = "${REDIRECT_URL}";
    window["env"]["scopes"] = "${APP_SOCPES}";
  })(this);