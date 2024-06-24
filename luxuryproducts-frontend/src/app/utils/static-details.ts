export class StaticDetails {

  // Order statuses
  public static readonly ORDER_PENDING: string = "PENDING";
  public static readonly ORDER_PROCESSING: string = "PROCESSING";
  public static readonly ORDER_CONFIRMED: string = "CONFIRMED";
  public static readonly ORDER_OUT_FOR_DELIVERY: string = "OUT_FOR_DELIVERY";
  public static readonly ORDER_DELIVERED: string = "DELIVERED";
  public static readonly ORDER_RETURNED: string = "RETURNED";
  public static readonly ORDER_PARTIALLY_RETURNED: string = "PARTIALLY RETURNED";

  // Return statuses
  public static readonly RETOUR_PENDING: string = "PENDING";
  public static readonly RETOUR_ACCEPTED: string = "ACCEPTED";
  public static readonly RETOUR_DECLINED: string = "DECLINED";
  public static readonly RETOUR_REFUNDED: string = "REFUNDED";

  // Other
  public static readonly RETOUR_PERIOD: number = 30;

}