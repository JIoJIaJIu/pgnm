package goldpoisk_parser;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import javax.persistence.*;

import com.goldpoisk.parser.orm.Item;
import com.goldpoisk.parser.orm.UpdatedValue;

@Entity
@Table(name="goldpoisk_entity")
public class Product {
    @Id @GeneratedValue
    Integer id;
	String article;
	String name;
	String material;
	String category;
	String url;
	String type;
	int proba;
	int price;
	int oldPrice;
	int weight;
	String description;
	String discount;
	int count = -1;

    @Transient
    String shopName;
    @Transient
	ArrayList<ByteArrayOutputStream> images = new ArrayList<ByteArrayOutputStream>();

    /*
	ArrayList<String> kamni = new ArrayList<String>();
	ArrayList<String> kamniColor = new ArrayList<String>();
	ArrayList<String> kamniWeight = new ArrayList<String>();
	ArrayList<String> kamniSize = new ArrayList<String>();
	
	String watch_material = "";
	String watch_material_body = "";
	String watch_glass = "";
	String watch_type = "";
	String watch_mechanic = "";
    */

    @Transient
    private final Database db;
    private static GoldpoiskDatabase goldpoiskDb = Parser.goldpoiskDb;

    public Product(IStore shop) {
        this.shopName = shop.getShopName();
        db = shop.getDatabase();
    }

    /*
	public void addKamni(String kamen) {
		kamni.add(kamen);
	}
	
	public void addKamniColor(String kamen) {
		kamniColor.add(kamen);
	}
	
	public void addKamniWeight(String kamen) {
		kamniWeight.add(kamen);
	}
	
	public void addKamniSize(String kamen) {
		kamniSize.add(kamen);
	}
    */
	
	public void addImage(ByteArrayOutputStream image) {
		images.add(image);
	}

    public boolean exist() {
        return this.goldpoiskDb.existProduct(article, shopName);
    }

    public boolean update() {
        Item model = this.goldpoiskDb.getProduct(article, shopName);
        boolean isUpdated = false;
        if (model == null) {
            return isUpdated;
        }

        if (model.price != price) {
            UpdatedValue updateModel = new UpdatedValue(article, "price", price + "", category);
            db.save(updateModel);
            isUpdated = true;
        }

        if (model.count != count) {
            UpdatedValue updateModel = new UpdatedValue(article, "count", count + "", category);
            db.save(updateModel);
            isUpdated = true;
        }

        return isUpdated;
    }

    public void save() {
        db.save(this);
    }
}
