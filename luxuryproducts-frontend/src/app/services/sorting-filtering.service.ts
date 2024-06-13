import {Injectable} from "@angular/core";
import {ExistingOrder} from "../models/existing-order.model";

@Injectable({
  providedIn: 'root'
})
export class SortingFilteringService {

  public alterOrders(orders: ExistingOrder[], searchFilter: string, sortNewestFirst: boolean) {
    var alteredOrders: ExistingOrder[] = [];

    alteredOrders = searchFilter ? orders.filter(order => order.id == searchFilter) : orders.slice();

    alteredOrders = alteredOrders
      .filter(order => order.orderDate !== undefined)
      .sort((a, b) => {
        const dateA = new Date(a.orderDate!);
        const dateB = new Date(b.orderDate!);

        if (sortNewestFirst) {
          return dateB.getTime() - dateA.getTime();
        } else {
          return dateA.getTime() - dateB.getTime();
        }
      });

    return alteredOrders;
  }
}