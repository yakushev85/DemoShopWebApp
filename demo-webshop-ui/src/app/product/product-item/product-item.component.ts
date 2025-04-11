import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product, ProductService, TransactionService, UserService } from '../../core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  standalone: false,
  selector: 'app-product-item',
  templateUrl: './product-item.component.html',
  styleUrls: ['./product-item.component.css']
})
export class ProductItemComponent implements OnInit {
  product: Product | undefined;
  productForm : FormGroup = this.fb.group({
    description: ['', Validators.required]
  });;
  productAdminForm: FormGroup = this.fb.group({
    id: ['', Validators.required],
    name: ['', Validators.required],
    price: ['', Validators.required],
  });;
  isSubmitting = false;
  isAdmin = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private transactionService: TransactionService,
    private productService: ProductService,
    private fb: FormBuilder
    ) { }

  ngOnInit(): void {
    this.route.data.subscribe(
      (data) => {
        this.product = (data as { product: Product }).product;
        this.productAdminForm.patchValue(this.product);
      }
    );

    this.userService.isAdmin.subscribe(
      (value) => {
        this.isAdmin = value;
      }
    );
  }

  submitForm() {
    if (!this.product) {
      return;
    }

    this.isSubmitting = true;

    let transactionItem = {
      description: (this.productForm.value as { description: string}).description,
      productId: this.product?.id
    };

    // post the changes
    this.transactionService.createItem(transactionItem)
    .subscribe({
      next: () => {
        this.isSubmitting = false;
        this.router.navigateByUrl('/transactions');
      },
      error: () => {
        this.isSubmitting = false;
      }
    });
  }

  submitAdminForm() {
    if (!this.product) {
      return;
    }

    this.isSubmitting = true;

    let updateProductItem = (this.productAdminForm.value as { id: string, name: string, price: string });

    this.productService.update(updateProductItem)
    .subscribe({
      next: () => {
        this.isSubmitting = false;
        this.router.navigateByUrl('/products');
      },
      error: () => {
        this.isSubmitting = false;
      }
    });
  }
}
