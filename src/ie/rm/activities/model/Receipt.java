package ie.rm.activities.model;

import java.io.Serializable;
import java.util.Date;

public class Receipt implements Serializable {
  String id;
  String store;
  String description;
  Double price;
  Date date;
  String imageUrl;
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getStore() {
	return store;
}
public void setStore(String store) {
	this.store = store;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public Double getPrice() {
	return price;
}
public void setPrice(Double price) {
	this.price = price;
}
public Date getDate() {
	return date;
}
public void setDate(Date date) {
	this.date = date;
}
public String getImageUrl() {
	return imageUrl;
}
public void setImageUrl(String imageUrl) {
	this.imageUrl = imageUrl;
}
  
  
}
