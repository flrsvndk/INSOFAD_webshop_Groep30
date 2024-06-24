import {User} from "./user.model";
import {Adress} from "./adress.model";
import {PlacedOrderItem} from "./placed-order-item.model";
import {Promocode} from "./promocode.model";

export class ExistingOrder {
    public id: string;
    public notes: string;
    public orderDate: string;
    public adress : Adress;
    public user: User;
    public userId: string;
    public orderItems: PlacedOrderItem[];
    public status: string;
    public promocode: Promocode;
    public totalPriceBeforePromocode: number;
    public totalPriceAfterPromocode: number;
}
