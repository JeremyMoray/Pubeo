import { Injectable } from "@angular/core";
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable()
export class CustomHttpInterceptor implements HttpInterceptor {
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const hardcodedToken="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqZWFuLmlsb3RAZ21haWwuY29tIiwianRpIjoiZGI5OTE3NWItMzM0OS00NDI2LTgxNWUtZTFlMGY4MjAwMjY2IiwiaWF0IjoxNTk3ODM1NjMzLCJuYmYiOjE1OTc4MzU2MzMsImV4cCI6MTU5Nzg0MjgzMywiaXNzIjoiUHViZW9BUElUb2tlblNlcnZlciIsImF1ZCI6Imh0dHA6Ly9sb2NhbGhvc3Q6NTAwMCJ9.58aLcgYS_IqwIyo4Y4QvD3N1HSJBvjw55HvrvPjJV_c"
        const reqWithAuth = req.clone({
            setHeaders: {
                Authorization: `Bearer ${hardcodedToken}`
            }
        });

        return next.handle(reqWithAuth)
            .pipe(
                catchError((error: HttpErrorResponse) => {
                    return throwError(error)
                })
            );
    }
}