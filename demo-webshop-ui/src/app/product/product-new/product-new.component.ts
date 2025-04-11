import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { User, UserService, ProductService } from '../../core';

@Component({
  standalone: false,
  selector: 'app-product-new',
  templateUrl: './product-new.component.html',
  styleUrls: ['./product-new.component.css']
})
export class ProductNewComponent implements OnInit {
  productAdminForm: FormGroup  = this.fb.group({
    name: ['', Validators.required],
    price: ['', Validators.required]
  });
  isSubmitting = false;
  isAdmin = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private productService: ProductService,
    private fb: FormBuilder
    ) { }

  ngOnInit(): void {
    this.userService.isAdmin.subscribe(
      (value) => {
        this.isAdmin = value;
      }
    );
  }

  submitAdminForm() {
    this.isSubmitting = true;

    let newProductItem = (this.productAdminForm.value as { name: string, price: string });

    this.productService.add(newProductItem)
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
