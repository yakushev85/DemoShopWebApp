import { Injectable, } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Product, ProductService } from '../../core';


@Injectable()
export class ProductItemResolver implements Resolve<Product> {
  constructor(
    private productService: ProductService,
    private router: Router,
  ) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<any> {

    return this.productService.getItem(route.params['itemid'])
      .pipe(catchError((err) => this.router.navigateByUrl('/')));
  }
}
