export const environment = {
  production: true,
  clientId: window["env"]["clientId"] || "",
  authority: window["env"]["authority"] || "",
  redirectUrl: window["env"]["redirectUrl"] || "",
  cloudServerUrl: window["env"]["apiUrl"] || "",
  scopes: window["env"]["scopes"] || ""
};
