import {User} from "./user.model";
import {ExistingOrder} from "./existing-order.model";
import {OrderItem} from "./order-item.model";
import {ProductType} from "./product-type.model";

export class RetourRequest {
  public id?: string;
  public user?: User;
  public order?: ExistingOrder;
  public retouredProducts?: OrderItem[];
  public dateTime?: string;
  public reason?: string;
  public note?: string;
  public state?: string;


  public orderItemIds: number[];
  public reasonId: string;
  public comment: string;
  public orderId: string;

}