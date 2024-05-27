import {User} from "./user.model";
import {Adress} from "./adress.model";
import {PlacedOrderItem} from "./placed-order-item.model";

export class ExistingOrder {
    public id: string;
    public totalProductsPrice: number;
    public notes: string;
    public orderDate: string;
    public adress : Adress;
    public user: User;
    public userId: string;
    public orderItems: PlacedOrderItem[];
}
