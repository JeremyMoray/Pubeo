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

        const hardcodedToken="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqZWFuLmlsb3RAZ21haWwuY29tIiwianRpIjoiY2Y5ODRlYWEtOWRhYy00NjM1LWExYTgtMWE1ZjM4MTEzZTQyIiwiaWF0IjoxNTk3OTEzMzA0LCJuYmYiOjE1OTc5MTMzMDMsImV4cCI6MTU5NzkyMDUwMywiaXNzIjoiUHViZW9BUElUb2tlblNlcnZlciIsImF1ZCI6Imh0dHA6Ly9sb2NhbGhvc3Q6NTAwMCJ9.YHQySC9UYaWbe0HNlZQgMbs5lSG5a-lqmMdHoV4FDuA"
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