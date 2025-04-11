import { Injectable, } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Transaction, TransactionService } from '../../core';

@Injectable()
export class TransactionItemResolver implements Resolve<Transaction> {
  constructor(
    private transactionService: TransactionService,
    private router: Router
  ) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<any> {

    return this.transactionService.getItem(route.params['itemid'])
      .pipe(catchError((err) => this.router.navigateByUrl('/')));
  }
}
