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

        const hardcodedToken="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqZWFuLmlsb3RAZ21haWwuY29tIiwianRpIjoiNTczODUxNGQtOTY1MC00NWU4LWFkNGQtNTVhMTcyNDcxOGRmIiwiaWF0IjoxNTk3OTIxNzY1LCJuYmYiOjE1OTc5MjE3NjUsImV4cCI6MTU5NzkyODk2NSwiaXNzIjoiUHViZW9BUElUb2tlblNlcnZlciIsImF1ZCI6Imh0dHA6Ly9sb2NhbGhvc3Q6NTAwMCJ9.8MOAIwfvRG5s1DCKVkOccV2K_t9juLIoxhMPKiBxczI"
        const reqWithAuth = req.clone({
            setHeaders: {
                Authorization: `Bearer ${hardcodedToken}`
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