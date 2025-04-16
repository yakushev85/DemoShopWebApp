import { Injectable } from '@angular/core';
import { map, distinctUntilChanged } from 'rxjs/operators';
import { Observable, BehaviorSubject, ReplaySubject } from 'rxjs';

import { ApiService } from './api.service';
import { PageData, User } from '../models';

@Injectable()
export class UserService {
  private currentUserSubject = new BehaviorSubject<User>({} as User);
  public currentUser = this.currentUserSubject.asObservable();

  private isAuthenticatedSubject = new BehaviorSubject<boolean>(false);
  public isAuthenticated = this.isAuthenticatedSubject.asObservable();

  private isAdminSubject = new BehaviorSubject<boolean>(false);
  public isAdmin = this.isAdminSubject.asObservable();

  constructor (
    private apiService: ApiService
  ) {}

  login(credentials: any): Observable<User> {
    return this.apiService.post('/login', credentials)
      .pipe(map(
      data => {
        this.setAuth(data);
        return data;
      }
    ));
  }

  signup(credentials: any): Observable<User> {
    return this.apiService.post('/signup', credentials)
      .pipe(map(
      data => {
        this.setAuth(data);
        return data;
      }
    ));
  }

  resetPassword(payload: any): Observable<User> {
    return this.apiService.post('/users/password', payload);
  }

  getAll(page = 0, size = 10): Observable<PageData> {
    return this.apiService.get(`/users?page=${page}&size=${size}`);
  }

  getItem(itemid: number): Observable<User> {
    return this.apiService.get(`/users/${itemid}`);
  }

  add(payload: any): Observable<User> {
    return this.apiService.post(`/users`, payload);
  }

  update(payload: any): Observable<User> {
    return this.apiService.put(`/users`, payload);
  }

  logout() {
    this.apiService.get("/logout").subscribe(
      (value) => {
        this.purgeAuth();
      });
  }


  populate() {
    let storedUser = this.loadFromStorage();

    if (storedUser) {
      this.setAuth(storedUser);
    } else {
      this.purgeAuth();
    }
  }

  setAuth(user: User) {
    sessionStorage.setItem("user_data", JSON.stringify(user));
    this.currentUserSubject.next(user);
    this.isAuthenticatedSubject.next(true);
    this.isAdminSubject.next(user.role?.toLowerCase() == 'admin');
  }

  purgeAuth() {
    sessionStorage.removeItem("user_data");
    this.currentUserSubject.next({} as User);
    this.isAuthenticatedSubject.next(false);
    this.isAdminSubject.next(false);
  }

  loadFromStorage(): User | null {
    let storedRawData = sessionStorage.getItem("user_data");

    if (storedRawData) {
      return JSON.parse(storedRawData);
    } else {
      return null;
    }
  }
}
