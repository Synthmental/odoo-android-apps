package cr.clearcorp.odoo.saleorderclient.models;

public class Product {

    protected Integer id;
    protected String name;
    protected String imageMedium;


    public Product(Integer id, String name, String imageMedium){
        this.id = id;
        this.name = name;
        this.imageMedium = imageMedium;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String getImageMedium() {
        return imageMedium;
    }

    public void setImageMedium(String imageMedium) {
        this.imageMedium = imageMedium;
    }

}
