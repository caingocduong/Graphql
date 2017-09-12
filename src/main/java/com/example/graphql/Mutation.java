package com.example.graphql;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.example.entities.NewProduct;
import com.example.entities.NewProductInput;
import com.example.repositories.NewProductRepository;

public class Mutation implements GraphQLRootResolver {

    private NewProductRepository newProductRepo;

    public Mutation(NewProductRepository newProductRepo){
        this.newProductRepo = newProductRepo;
    }

    public NewProduct createNewProduct(String img, String title,
                                       String description, int discount,
                                       float originalPrice, float price){
        NewProduct newProduct = new NewProduct(img,title,description,discount,originalPrice,price);
        newProductRepo.saveNewProduct(newProduct);

        return newProduct;
    }

    public NewProduct updateNewProduct(String id,NewProductInput newProduct){

        return newProductRepo.updateNewProduct(id,newProduct);
    }

    public NewProduct deleteNewProduct(String id){

        return newProductRepo.deleteNewProduct(id);
    }
}
