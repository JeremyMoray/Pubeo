import { Injectable } from "@angular/core";
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthenticationService } from './shared/services/authentication.service';

@Injectable()
export class CustomHttpInterceptor implements HttpInterceptor {

    constructor(private authenticationService: AuthenticationService){}
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        let requestNumber = localStorage.getItem('requestNumber');

        if(requestNumber == null)
            localStorage.setItem('requestNumber', '1');
        else
        localStorage.setItem('requestNumber', (parseInt(localStorage.getItem('requestNumber')) + 1).toString());

        const token = this.authenticationService.getToken();
        const reqWithAuth = req.clone({
            setHeaders: {
                Authorization: `Bearer ${token}`
            }
        });

        return next.handle(reqWithAuth)
            .pipe(
                catchError((error: HttpErrorResponse) => {
                    let requestErrorNumber = sessionStorage.getItem('requestErrorNumber');

                    if(requestErrorNumber == null)
                        sessionStorage.setItem('requestErrorNumber', '1');
                    else
                        sessionStorage.setItem('requestErrorNumber', (parseInt(sessionStorage.getItem('requestErrorNumber')) + 1).toString());
                    
                    return throwError(error)
                })
            );
    }
}