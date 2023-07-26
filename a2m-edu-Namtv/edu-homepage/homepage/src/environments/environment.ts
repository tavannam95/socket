// This file can be replaced during build by using the `fileReplacements` array.
// `ng build` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  apiHost: 'https://backend.atwom.com.vn',
  mainURL: 'http://localhost:8113',
  apiURL: 'http://localhost:8113/api',
  // apiURL: 'http://192.168.0.106:8113/api',
  // manageApiUrl: 'http://localhost:8080/api',
  manageApiUrl: 'http://192.168.0.106:9000/api',
  // authURL: 'http://gen-auth.atwom.com.vn/login',
  authURL: 'http://localhost:9898/login',
  genDataURL: 'http://192.168.0.47:5000',

  serviceUrl: 'http://localhost:8080/filemanager/'
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.
