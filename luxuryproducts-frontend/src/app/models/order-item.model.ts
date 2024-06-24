import {ProductType} from "./product-type.model";

export class OrderItem {
    public quantity: number;
    public typeId: String;


    constructor(quantity: number, typeId: String) {
        this.quantity = quantity;
        this.typeId = typeId;
    }
}
