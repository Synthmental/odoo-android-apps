package cr.clearcorp.odoo.saleorderclient.models;

public class Pricelist {

    protected Integer id;
    protected String name;

    public Pricelist(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
