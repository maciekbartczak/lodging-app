import { Injectable } from '@angular/core';

const TOKEN_STORAGE_KEY = 'auth-token'

@Injectable({
    providedIn: 'root'
})
export class TokenService {

    constructor() {
    }

    public saveToken(token: string): void {
        localStorage.removeItem(TOKEN_STORAGE_KEY);
        localStorage.setItem(TOKEN_STORAGE_KEY, token);
    }

    public getToken(): string {
        const token = localStorage.getItem(TOKEN_STORAGE_KEY);
        return token == null ? '' : token;
    }

    public removeToken(): void {
        localStorage.removeItem(TOKEN_STORAGE_KEY);
    }

    public getUserId(): string {
        const token = this.getToken();
        if (!token) {
            return '';
        }
        return JSON.parse(atob(token.split('.')[1])).sub;
    }
}
