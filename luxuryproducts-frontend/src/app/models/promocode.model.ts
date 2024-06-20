export class Promocode {
    public id: number;
    public name: string;
    public description: string;
    public percentageOff: number;
    public maxUsages: number;
    public usages: number;
    public totalPriceOrders: number;
    public available: boolean;
    public dedicatedPromocode: boolean;
    public dedicatedUserEmail: string;
}