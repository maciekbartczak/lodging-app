import { Component } from '@angular/core';
import { AuthService, RegisterRequest } from '../../../core/openapi';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';

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
        username: '',
        firstName: '',
        lastName: '',
        password: ''
    }

    constructor(private authService: AuthService,
                private formBuilder: FormBuilder,
                private router: Router,
                private messageService: MessageService,
                private translateService: TranslateService) {

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

        this.authService.register(this.model).subscribe({
            next: () => {
                this.router.navigate(['/auth/login']).then(() => {
                    this.messageService.add({
                        severity: 'success',
                        summary: this.translateService.instant('auth.register.toast.success.header'),
                        detail: this.translateService.instant('auth.register.toast.success.content')
                    })
                });
            },
            error: () => {
                this.error = true;
                this.loading = false;
            }
        });
    }

}
