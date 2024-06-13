import {ProductType} from "./product-type.model";

export class OrderItem {
    public id?: number;
    public quantity: number;
    public productType: ProductType;
    public productTypeId: String;


    constructor(quantity: number, productId: String, product: ProductType) {
        this.quantity = quantity;
        this.productTypeId = productId;
        this.productType = product;
    }
}
