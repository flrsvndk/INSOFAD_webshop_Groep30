import {ProductType} from "./product-type.model";

export class OrderItem {
    public quantity: number;
    public typeId: String;
    public product: ProductType;

    // Deze twee (en quantity) zijn nodig voor het admin-retour-requests overzicht om de requests in te laden.
    public id?: number;
    public productType: ProductType;

    constructor(quantity: number, productId: String, product: ProductType) {
        this.quantity = quantity;
        this.typeId = productId;
        this.product = product;
    }
}
