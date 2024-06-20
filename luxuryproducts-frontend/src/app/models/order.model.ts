import {Adress} from "./adress.model";
import {OrderItem} from "./order-item.model";
import {Promocode} from "./promocode.model";

export class Order {
  public notes: string;
  public adressDTO : Adress;
  public orderItems: OrderItem[];
  public promocode: Promocode;
}
