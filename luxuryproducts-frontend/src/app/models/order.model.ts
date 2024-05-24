import {Adress} from "./adress.model";
import {OrderItem} from "./order-item.model";

export class Order {
  public notes: string;
  public adressDTO : Adress;
  public orderItems: OrderItem[];


  constructor(notes: string, adress: Adress, orderItems: OrderItem[]) {
    this.notes = notes;
    this.adressDTO = adress;
    this.orderItems = orderItems;
  }

}
