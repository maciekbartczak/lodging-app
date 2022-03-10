import { Injectable } from '@angular/core';
import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable, of, throwError } from 'rxjs';
import { catchError } from "rxjs/operators";
import { Router } from "@angular/router";
import { TokenService } from './token.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    constructor(private tokenService: TokenService,
                private router: Router) {
    }

    intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
        const token = this.tokenService.getToken();
        const req = request.clone({
            headers: request.headers.set('Authorization', `Bearer ${token}`)
        });
        return next.handle(req).pipe(catchError(e => this.handleAuthError(e)));
    }

    private handleAuthError(err: HttpErrorResponse): Observable<any> {

        if (err.status === 403) {
            this.router.navigate(['/404']);
            return of(err.message);
        }
        if (err.status === 401) {
            this.router.navigate(['/auth/login']);
            return of(err.message);
        }
        return throwError(() => err);
    }
}
