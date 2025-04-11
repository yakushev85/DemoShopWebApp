import { Injectable } from '@angular/core';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';

import { UserService } from '../services';

@Injectable()
export class HttpTokenInterceptor implements HttpInterceptor {
  constructor(private userService: UserService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let headersConfig;
    const token = this.userService.loadFromStorage()?.token;

    if (token) {
      headersConfig = {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Authorization': `Bearer ${token}`
      };
    } else {
      headersConfig = {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
        } 
    }
    

    const requestChanged = request.clone({ setHeaders: headersConfig });

    return next.handle(requestChanged);
  }
}
