import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product, ProductService, Transaction, UserService } from '../../core';

@Component({
  standalone: false,
  selector: 'app-transaction-item',
  templateUrl: './transaction-item.component.html',
  styleUrls: ['./transaction-item.component.css']
})
export class TransactionItemComponent implements OnInit {
  transaction: Transaction | undefined;
  product: Product | undefined;
  isAdmin = false;
  isSubmitting = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private productService: ProductService)
     { }

  ngOnInit(): void {
    this.route.data.subscribe(
      (data) => {
        this.transaction = (data as { transaction: Transaction }).transaction;

        this.productService.getItem(this.transaction.product.id).subscribe(
          (value) => {
            this.product = value;
          }
        );
      }
    );

    this.userService.isAdmin.subscribe(
      (value) => {
        this.isAdmin = value;
      }
    );
  }

}
