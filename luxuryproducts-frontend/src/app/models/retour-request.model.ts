import {User} from "./user.model";
import {ExistingOrder} from "./existing-order.model";
import {OrderItem} from "./order-item.model";

export class RetourRequest {
  public orderProductIds: number[];
  public reasonId: number;
  public comment: string;
  public orderId: string;

  public dateTime?: string;
  public id?: string;
  public note?: string;
  public order?: ExistingOrder;
  public reason?: string;
  public retouredProducts?: OrderItem[];
  public state?: string;
  public user?: User;
}