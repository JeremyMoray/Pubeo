import { Injectable } from "@angular/core";
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable()
export class CustomHttpInterceptor implements HttpInterceptor {
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        let requestNumber = localStorage.getItem('requestNumber');

        if(requestNumber == null)
            localStorage.setItem('requestNumber', '1');
        else
        localStorage.setItem('requestNumber', (parseInt(localStorage.getItem('requestNumber')) + 1).toString());

        const hardcodedToken="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqZWFuLmlsb3RAZ21haWwuY29tIiwianRpIjoiMTgwMjhmNzQtMTE2Ny00N2FiLTgyZTItNTUzZjhhMmFhYTYyIiwiaWF0IjoxNTk3ODcxMjk1LCJuYmYiOjE1OTc4NzEyOTUsImV4cCI6MTU5Nzg3ODQ5NSwiaXNzIjoiUHViZW9BUElUb2tlblNlcnZlciIsImF1ZCI6Imh0dHA6Ly9sb2NhbGhvc3Q6NTAwMCJ9.f5CRaTmifV9czPVfkT6vL1TCJU0YvSFcFIhtd4XPtc0"
        const reqWithAuth = req.clone({
            setHeaders: {
                Authorization: `Bearer ${hardcodedToken}`
            }
        });

        return next.handle(reqWithAuth)
            .pipe(
                catchError((error: HttpErrorResponse) => {
                    let requestErrorNumber = localStorage.getItem('requestErrorNumber');

                    if(requestErrorNumber == null)
                        localStorage.setItem('requestErrorNumber', '1');
                    else
                    localStorage.setItem('requestErrorNumber', (parseInt(localStorage.getItem('requestErrorNumber')) + 1).toString());
                    
                    return throwError(error)
                })
            );
    }
}