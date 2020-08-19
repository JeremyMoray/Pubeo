import { Injectable } from "@angular/core";
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable()
export class CustomHttpInterceptor implements HttpInterceptor {
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const hardcodedToken="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqZWFuLmlsb3RAZ21haWwuY29tIiwianRpIjoiYzc2Y2QzNmUtYTJiYy00NWE0LTk0ZWMtMWE1ZGE2YWEwY2UxIiwiaWF0IjoxNTk3Nzk0MzU5LCJuYmYiOjE1OTc3OTQzNTksImV4cCI6MTU5NzgwMTU1OSwiaXNzIjoiUHViZW9BUElUb2tlblNlcnZlciIsImF1ZCI6Imh0dHA6Ly9sb2NhbGhvc3Q6NTAwMCJ9._QuHYW3XQCAuvcff2f8QxE1D5oY9TUnHohF-bjLI7eg"
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