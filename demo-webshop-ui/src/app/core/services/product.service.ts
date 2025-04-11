import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

import { ApiService } from './api.service';
import { Product } from '../models/product.model';
import { PageData } from '../models';


@Injectable()
export class ProductService {
  constructor (
    private apiService: ApiService,
    private http: HttpClient,
  ) {}

  getAll(page = 0, size = 10): Observable<PageData> {
    return this.apiService.get(`/products?page=${page}&size=${size}`);
  }

  getItem(itemId: number): Observable<Product> {
    return this.apiService.get(`/products/${itemId}`);
  }

  update(product: any): Observable<Product> {
    return this.apiService.put(`/products`, product);
  }

  add(product: any): Observable<Product> {
    return this.apiService.post(`/products`, product);
  }
}
