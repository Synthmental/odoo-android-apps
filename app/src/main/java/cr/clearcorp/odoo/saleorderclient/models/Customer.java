package cr.clearcorp.odoo.saleorderclient.models;

public class Customer {

    protected Integer id;
    protected String name;

    public Customer(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
