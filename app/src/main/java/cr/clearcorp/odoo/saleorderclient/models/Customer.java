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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}