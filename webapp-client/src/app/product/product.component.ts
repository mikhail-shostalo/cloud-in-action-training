import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { Product } from './product.model';
import { ProductService } from './product.service';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css'],
  providers: [ProductService]
})
export class ProductComponent implements OnInit {

  @ViewChild('readOnlyTemplate', { static: false })
  readOnlyTemplate!: TemplateRef<any>;
  @ViewChild('editTemplate', { static: false })
  editTemplate!: TemplateRef<any>;

  products: Array<Product>;

  editedProduct = {
    code: '',
    name: '',
    description: '',
    price: '0.0'
  };

  isNewRecord = false;

  constructor(private productService: ProductService) { 
    this.products = new Array<Product>();
  }

  ngOnInit(): void {
    this.loadProducts();
  }

  loadTemplate(product: Product) {
    if (this.editedProduct && this.editedProduct.code === product.code) {
        return this.editTemplate;
    }
    return this.readOnlyTemplate;
}

  private loadProducts(): void {
    this.productService.findAll().subscribe((data: Product[]) => {
      this.products = data;
    });
  }

  addProduct(): void {
    this.products.push(this.editedProduct);
    this.isNewRecord = true;
  }

  editProduct(product: Product) {
    this.editedProduct = new Product(product.code, product.name, product.description, product.price);
  }

  saveProduct(): void {
    this.isNewRecord? this.saveNewProduct() : this.updateProduct();
  }

  private saveNewProduct(): void {
    this.productService.save(this.editedProduct).subscribe(data => {
      this.loadProducts();
    });
    this.editedProduct = new Product('', '', '', '0.0');
    this.isNewRecord = false;
  }

  private updateProduct(): void {
    this.productService.update(this.editedProduct).subscribe(data => {
      this.loadProducts();
    });
    this.editedProduct = new Product('', '', '', '0.0');
  }

  cancel(): void {
    if (this.isNewRecord) {
      this.products.pop();
      this.isNewRecord = false;
    }
    this.editedProduct = new Product('', '', '', '0.0');
  }

  deleteProduct(product: Product): void {
    this.productService.delete(product.code).subscribe(data => {
      this.loadProducts();
    })
  }

}
