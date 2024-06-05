import {ProductType} from "./product-type.model";

export class OrderItem {
    public quantity: number;
    public typeId: String;
    public product: ProductType;


    constructor(quantity: number, productId: String, product: ProductType) {
        this.quantity = quantity;
        this.typeId = productId;
        this.product = product;
    }
}
