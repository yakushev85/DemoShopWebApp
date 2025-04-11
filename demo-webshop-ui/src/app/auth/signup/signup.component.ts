import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../../core';


@Component({
  standalone: false,
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  error: string;
  isSubmitting: boolean;
  signupForm: FormGroup;

  constructor(
    private router: Router,
    private userService: UserService,
    private fb: FormBuilder
    ) {

    this.error = "";
    this.isSubmitting = false;

    this.signupForm = this.fb.group({
      'username': ['', [Validators.required, Validators.minLength(2)]],
      'password': ['', [Validators.required, Validators.minLength(3)]],
      'repassword': ['', [Validators.required, Validators.minLength(3)]]
    });
  }

  ngOnInit(): void {
  }

  submitForm() {
    this.isSubmitting = true;
    this.error = "";

    this.userService.signup(this.signupForm.value)
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
