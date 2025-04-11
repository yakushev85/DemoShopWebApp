import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../../core';

@Component({
  standalone: false,
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  error: string;
  isSubmitting: boolean;
  loginForm: FormGroup;

  constructor(
    private router: Router,
    private userService: UserService,
    private fb: FormBuilder
    ) {

    this.error = "";
    this.isSubmitting = false;

    this.loginForm = this.fb.group({
      'username': ['', [Validators.required, Validators.minLength(2)]],
      'password': ['', [Validators.required, Validators.minLength(3)]]
    });
  }

  ngOnInit(): void {
  }

  submitForm() {
    this.isSubmitting = true;
    this.error = "";

    this.userService.login(this.loginForm.value)
    .subscribe({
      next: (data) => {
        this.isSubmitting = false;
        this.router.navigateByUrl('/products');
      },
      error: (err) => {
        this.error = err.error.errorMessage;
        this.isSubmitting = false;
      }
    });
  }

}
