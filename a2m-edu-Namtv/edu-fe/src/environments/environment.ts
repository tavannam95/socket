// This file can be replaced during build by using the `fileReplacements` array.
// `ng build` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  mainURL: 'http://localhost:8113',
  apiURL: 'http://localhost:8113/api',
  authURL: 'http://localhost:8112/login',

  apiHost: 'http://localhost:8113',

  // apiURL: 'http://192.168.0.106:8088/api',
  // manageApiUrl: 'http://localhost:8080/api',
  manageApiUrl: 'http://192.168.0.106:9000/api',
  // authURL: 'http://gen-auth.atwom.com.vn/login',
  genDataURL: 'http://192.168.0.47:5000',

  serviceUrl: 'http://localhost:8080/filemanager/',

  wsURL: 'http://localhost:8113/edms',
  // apiMailURL: "http://localhost:9192/api",
  // apiMailHost: "http://localhost:9192",
  // apiMailURL: "",
  // apiMailHost: "",
  version: "1.10"
};
