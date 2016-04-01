package cr.clearcorp.odoo.saleorderclient.models;

import java.util.ArrayList;

public class SaleOrder {

    protected Customer customer;
    protected Pricelist pricelist;
    protected Warehouse warehouse;
    protected ArrayList<SaleOrderLine> lines;

    public SaleOrder(){
        this.lines = new ArrayList<>();
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Pricelist getPricelist() {
        return pricelist;
    }

    public void setPricelist(Pricelist pricelist) {
        this.pricelist = pricelist;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public ArrayList<SaleOrderLine> getLines() {
        return lines;
    }

    public void setLines(ArrayList<SaleOrderLine> lines) {
        this.lines = lines;
    }

    public void addProduct(Product product, Double price){
        boolean flag = false;
        for (SaleOrderLine line : this.lines) {
            if (line.product.id == product.id){
                line.setQuantity(line.getQuantity() + 1);
                flag = true;
            }
        }
        if (!flag){
            lines.add(new SaleOrderLine(1.0, product, price));
        }
    }

}
