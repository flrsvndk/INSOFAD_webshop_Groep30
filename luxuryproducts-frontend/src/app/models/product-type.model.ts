import {ProductSpecification} from "./product-specification.model";
import {OrderItem} from "./order-item.model";

export class ProductType {
    public id: string;
    public name: string;
    public stock: number;
    public price: number;
    public imgUrl: string;
    public productId: string;
    public amount: number;
    public subSpecification: ProductSpecification;
    public order: OrderItem;
}
