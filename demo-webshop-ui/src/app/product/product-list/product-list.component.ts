import { Component, OnInit } from '@angular/core';
import { Product, ProductService, User, UserService } from '../../core';

@Component({
  standalone: false,
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];
  isAdmin = false;
  pageNumber = 1;
  pageSize = 10;
  totalElements = 0;


  constructor(
    private productService: ProductService,
    private userService: UserService) { }

  ngOnInit(): void {
    this.listValues();

    this.userService.isAdmin.subscribe(
      (value) => {
        this.isAdmin = value;
      }
    );
  }

  listValues() {
    this.productService.getAll(this.pageNumber - 1, this.pageSize).subscribe(
      (value) => {
        if (value) {
          this.products = (value.content as Product[]);

          this.pageNumber = value.pageable.pageNumber + 1;
          this.pageSize = value.pageable.pageSize;
          this.totalElements = value.totalElements;
        }
      }
    );
  }

  updatePageSize(value: string) {
    this.pageSize = +value;
    this.listValues();
  }
}
