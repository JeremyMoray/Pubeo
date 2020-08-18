import { Injectable } from "@angular/core";
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable()
export class CustomHttpInterceptor implements HttpInterceptor {
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const hardcodedToken="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqZWFuLmlsb3RAZ21haWwuY29tIiwianRpIjoiYTU3YTc2ZTgtZTUwOS00Mjk5LTllMjktZDQ3NzY0YmQ4YTljIiwiaWF0IjoxNTk3NzY0NjAwLCJuYmYiOjE1OTc3NjQ2MDAsImV4cCI6MTU5Nzc3MTgwMCwiaXNzIjoiUHViZW9BUElUb2tlblNlcnZlciIsImF1ZCI6Imh0dHA6Ly9sb2NhbGhvc3Q6NTAwMCJ9.nCWfaiYvkKnMFg4RKl-xc5qcFrXtLQYhLXZ2r-Vx8uw"
        const reqWithAuth = req.clone({
            setHeaders: {
                Authorization: `Bearer ${hardcodedToken}`
            }
        });

        return next.handle(reqWithAuth)
            .pipe(
                catchError((error: HttpErrorResponse) => {
                    alert(`HTTP Error : ${req.url}`);
                    return throwError(error)
                })
            );
    }
}