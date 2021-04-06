import { HttpClient, HttpHeaders } from "@angular/common/http"
import { Injectable } from "@angular/core"
import { Observable } from "rxjs"
import { environment } from 'src/environments/environment'
import { Product } from "./product.model"

@Injectable()
export class ProductService {
    productApiUrl = `${environment.cloudServerUrl}/products`

    constructor(private http : HttpClient){}

    findByCode(code: string): Observable<Product> {
        return this.http.get<Product>(`${this.productApiUrl}/${code}`, {headers: this.createHeaders()}).pipe();
    }

    findAll(): Observable<Product[]> {
        return this.http.get<Product[]>(`${this.productApiUrl}`, {headers: this.createHeaders()});
    }

    save(newProduct: Product): Observable<Product> {
        return this.http.post<Product>(`${this.productApiUrl}`, newProduct, {headers: this.createHeaders()});
    }

    update(editedProduct: Product): Observable<Product> {
        return this.http.put<Product>(`${this.productApiUrl}/${editedProduct.code}`, editedProduct, {headers: this.createHeaders()});
    }

    delete(code: string): Observable<any> {
        return this.http.delete(`${this.productApiUrl}/${code}`, {headers: this.createHeaders()});
    }

    private createHeaders(): HttpHeaders {
        return new HttpHeaders().append('Accept', 'application/json');
    }
}