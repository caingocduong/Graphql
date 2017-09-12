package com.example.repositories;

import com.example.entities.NewProduct;
import com.example.entities.NewProductFilter;
import com.example.entities.NewProductInput;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.regex;

public class NewProductRepository {
    private MongoCollection<Document> newProducts;

    public NewProductRepository(MongoCollection<Document> newProducts) {
        this.newProducts = newProducts;
    }

    public List<NewProduct> getAllNewProducts(){
        List<NewProduct> allNewProducts = new ArrayList<>();
        for (Document doc : newProducts.find()){
            NewProduct newProduct = newProductInstance(doc);
            allNewProducts.add(newProduct);
        }

        return allNewProducts;
    }

    public NewProduct findNewProductById(String id){
        Document doc = newProducts.find(eq("_id", new ObjectId(id))).first();

        return newProductInstance(doc);
    }

    public void saveNewProduct(NewProduct newProduct){
        Document doc = new Document();
        doc.append("img", newProduct.getImg());
        doc.append("title", newProduct.getTitle());
        doc.append("description", newProduct.getDescription());
        doc.append("discount", newProduct.getDiscount());
        doc.append("originalPrice", newProduct.getOriginalPrice());
        doc.append("price", newProduct.getPrice());

        newProducts.insertOne(doc);
    }

    public NewProduct updateNewProduct(String id,NewProductInput newProductInput){
        Document query = newProducts.find(eq("_id", new ObjectId(id))).first();

        Document document = new Document();
        document.append("_id", new ObjectId(id));
        document.append("img", newProductInput.getImg());
        document.append("title",newProductInput.getTitle());
        document.append("description",newProductInput.getDescription());
        document.append("discount",newProductInput.getDiscount());
        document.append("originalPrice",newProductInput.getOriginalPrice());
        document.append("price",newProductInput.getPrice());

        Document documentToUpdate = new Document();
        documentToUpdate.append("$set",document);

        newProducts.updateOne(query,documentToUpdate);

        return newProductInstance(document);
    }

    public NewProduct deleteNewProduct(String id){
        Document doc = newProducts.find(eq("_id", new ObjectId(id))).first();
        newProducts.findOneAndDelete(doc);

        return newProductInstance(doc);
    }

    public List<NewProduct> getAllNewProduct(NewProductFilter filter){
        Optional<Bson> mongoFilter = Optional.ofNullable(filter).map(this::buildFilter);
        List<NewProduct> allNewProducts = new ArrayList<>();
        for(Document doc : mongoFilter.map(newProducts::find).orElseGet(newProducts::find)){
            allNewProducts.add(newProductInstance(doc));
        }

        return allNewProducts;
    }

    public List<NewProduct> allNewProductsPagination(NewProductFilter filter, int pageIndex, int pageSize){
        Optional<Bson> mongoFilter = Optional.ofNullable(filter).map(this::buildFilter);

        List<NewProduct> allNewProducts = new ArrayList<>();
        FindIterable<Document> documents = mongoFilter.map(newProducts::find).orElseGet(newProducts::find);
        for(Document doc : documents.skip(pageIndex).limit(pageSize)){
            allNewProducts.add(newProductInstance(doc));
        }

        return allNewProducts;
    }

    private Bson buildFilter(NewProductFilter filter){
        String titlePattern = filter.getTitleContains();
        Bson titleCondition = null;
        if(titlePattern != null && !titlePattern.isEmpty()){
            titleCondition = regex("title",".*"+titlePattern+".*","i");
        }

        if(titleCondition != null){

            return and(titleCondition);
        }

        return null;
    }

    private NewProduct newProductInstance(Document doc){

        return new NewProduct(
                doc.get("_id").toString(),
                doc.getString("img"),
                doc.getString("title"),
                doc.getString("description"),
                doc.getInteger("discount"),
                doc.getDouble("originalPrice"),
                doc.getDouble("price")
        );
    }
}
