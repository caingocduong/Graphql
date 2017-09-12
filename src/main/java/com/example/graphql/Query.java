package com.example.graphql;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.example.entities.NewProduct;
import com.example.entities.NewProductFilter;
import com.example.repositories.NewProductRepository;
import java.util.List;

public class Query implements GraphQLRootResolver {

    private NewProductRepository newProductRepo;

    public Query(NewProductRepository newProductRepo){
        this.newProductRepo = newProductRepo;
    }

    public List<NewProduct> allNewProducts(){

        return newProductRepo.getAllNewProducts();
    }

    public NewProduct findNewProductById(String id){

        return newProductRepo.findNewProductById(id);
    }

    public List<NewProduct> allNewProductsFilterByTitle(NewProductFilter filter){

        return newProductRepo.getAllNewProduct(filter);
    }

    public List<NewProduct> allNewProductsPagination(NewProductFilter filter, Number pageIndex, Number pageSize){

        return newProductRepo.allNewProductsPagination(filter,pageIndex.intValue(), pageSize.intValue());
    }
}
