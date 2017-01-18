package br.com.hlandim.supermarket.data.service.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by hlandim on 13/01/17.
 */

public class Product implements Parcelable {

    public static final String VALIDATION_ERROR_REASON = "validationError";
    public static final String VALIDATION_ERROR_EMAIL = "email";
    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
    private String id;
    private String filename;
    private int height;
    private int width;
    private String title;
    private String type;
    private String description;
    private double price;
    private int rating;
    private List<Error> errors;

    public Product() {
    }

    protected Product(Parcel in) {
        id = in.readString();
        filename = in.readString();
        height = in.readInt();
        width = in.readInt();
        title = in.readString();
        type = in.readString();
        description = in.readString();
        price = in.readDouble();
        rating = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(filename);
        dest.writeInt(height);
        dest.writeInt(width);
        dest.writeString(title);
        dest.writeString(type);
        dest.writeString(description);
        dest.writeDouble(price);
        dest.writeInt(rating);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Product && ((Product) obj).getId().equals(this.getId());
    }
}
