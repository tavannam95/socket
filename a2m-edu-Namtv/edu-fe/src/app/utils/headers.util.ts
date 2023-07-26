import {HttpHeaders} from '@angular/common/http';
import {Cookie} from 'ng2-cookies';
import { AuthConstant } from '../constants/auth.constant';
import { isNullOrUndefined } from 'is-what';

export class HeadersUtil {

  public static getHeaders(): HttpHeaders {
    return new HttpHeaders({
      'Content-Type': 'application/json'
    });
  }

  public static getHeadersAuthOnly(): HttpHeaders {
    const token = AuthConstant.TOKEN_TYPE_KEY + Cookie.get(AuthConstant.ACCESS_TOKEN_KEY);
    if (!token) {
      return new HttpHeaders();
    }
    return new HttpHeaders({
      'Authorization': token
    });
  }

  public static getHeadersUploadOnly(): HttpHeaders {
    const token = AuthConstant.TOKEN_TYPE_KEY + Cookie.get(AuthConstant.ACCESS_TOKEN_KEY);
    if (!token) {
      return new HttpHeaders();
    }
    return new HttpHeaders({
      'Authorization': token,
      'Upload-check': "1"
    });
  }

  public static getHeadersAuth(): HttpHeaders {
    const token = AuthConstant.TOKEN_TYPE_KEY + Cookie.get(AuthConstant.ACCESS_TOKEN_KEY);
    if (!token) {
      return HeadersUtil.getHeaders();
    }

    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': token,
    });
  }

  public static getHeadersAuthGenData(): HttpHeaders {
    return new HttpHeaders({
      'Access-Control-Allow-Origin': '*',
      // 'Access-Control-Allow-Credentials': 'true',
      'Content-Type': 'multipart/form-data',
      // 'Access-Control-Allow-Methods' : 'GET, POST, OPTIONS',
      // 'Access-Control-Allow-Headers': 'X-Requested-With,content-type'
    });
  }

  public static getHeadersAuthFile(): HttpHeaders {
    const token = AuthConstant.TOKEN_TYPE_KEY + Cookie.get(AuthConstant.ACCESS_TOKEN_KEY);
    if (!token) {
      return HeadersUtil.getHeaders();
    }

    return new HttpHeaders({
      'Content-Type': 'application/pdf',
      'Authorization': token
    });
  }

  public static getHeadersAuthWithUsername(): HttpHeaders {
    const token = AuthConstant.TOKEN_TYPE_KEY + Cookie.get(AuthConstant.ACCESS_TOKEN_KEY);
    if (!token) {
      return HeadersUtil.getHeaders();
    }
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': token,
      // 'X-UNAME': Cookie.get('username')
    });
  }

  public static getHeadersAuthSendingFile(): HttpHeaders {
      const token = AuthConstant.TOKEN_TYPE_KEY + Cookie.get(AuthConstant.ACCESS_TOKEN_KEY);
      if (!token) {
        return HeadersUtil.getHeaders();
      }
      return new HttpHeaders({
        // 'Content-Type': 'multipart/form-data',
        'Authorization': token
      });
  }

  public static getHeadersAuthAttachment(): HttpHeaders {
    const token =
      AuthConstant.TOKEN_TYPE_KEY + Cookie.get(AuthConstant.ACCESS_TOKEN_KEY);
    if (isNullOrUndefined(token)) {
      return HeadersUtil.getHeaders();
    }

    return new HttpHeaders({
      'Access-Control-Allow-Origin': '*',
      responseType: 'blob' as 'json',
      Authorization: token,
    });
  }

}
