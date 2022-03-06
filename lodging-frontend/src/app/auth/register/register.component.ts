import { Component } from '@angular/core';
import { AuthService, RegisterRequest } from '../../../core/openapi';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.scss']
})
export class RegisterComponent {

    registerForm: FormGroup;
    loading = false;
    error = false;
    submitted = false;

    model: RegisterRequest = {
        email: '',
        firstName: '',
        lastName: '',
        password: ''
    }

    constructor(private authService: AuthService,
                private formBuilder: FormBuilder) {

        this.registerForm = this.formBuilder.group({
            username: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(20)]],
            firstName: ['', Validators.required],
            lastName: ['', Validators.required],
            password: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(50)]]
        })
    }

    register() {
        this.submitted = true;

        if(this.registerForm.invalid) {
            return;
        }

        this.loading = true;

        this.authService.register(this.model).subscribe(
            () => {
                console.log('registered!')
            },
            () => {
                this.error = true;
                this.loading = false;
            },
            () => {
                this.loading = false;
            }
        )
    }

}
