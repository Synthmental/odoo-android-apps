package cr.clearcorp.odoo.saleorderclient.models;

public class SaleOrderLine {

    protected Integer quantity;
    protected Product product;
    protected Float price;

    public SaleOrderLine(Integer quantity, Product product, Float price) {
        this.quantity = quantity;
        this.product = product;
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

}
