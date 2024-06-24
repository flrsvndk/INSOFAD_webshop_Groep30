import {User} from "./user.model";
import {ExistingOrder} from "./existing-order.model";
import {OrderItem} from "./order-item.model";
import {ProductType} from "./product-type.model";
import {RetourReason} from "./retour-reason.model";
import {PlacedOrderItem} from "./placed-order-item.model";

export class RetourRequest {
  public id: string;
  public user?: User;
  public order?: ExistingOrder;
  public retouredProducts?: PlacedOrderItem[];
  public dateTime?: string;
  public reason?: RetourReason;
  public note?: string;
  public state?: string;


  public orderItemIds: number[];
  public reasonId: string;
  public comment: string;
  public orderId: string;

}