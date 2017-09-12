package com.example.graphql;

import com.coxautodev.graphql.tools.SchemaParser;
import javax.servlet.annotation.WebServlet;

import com.example.exception.SanitizedError;
import com.example.repositories.NewProductRepository;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLServlet;

import java.util.List;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/graphql")
public class GraphqlEndpoint extends SimpleGraphQLServlet{

    private static NewProductRepository newProductRepo;

    static {
        MongoDatabase mongoDB = new MongoClient().getDatabase("product");
        newProductRepo = new NewProductRepository(mongoDB.getCollection("newProducts"));
    }

    public GraphqlEndpoint() {
        super(buildSchema());
    }

    @Override
    protected List<GraphQLError> filterGraphQLErrors(List<GraphQLError> errors){

        return errors.stream()
                .filter(e -> e instanceof ExceptionWhileDataFetching || super.isClientError(e))
                .map(e -> e instanceof ExceptionWhileDataFetching ? new SanitizedError((ExceptionWhileDataFetching)e) : e)
                .collect(Collectors.toList());
    }

    private static GraphQLSchema buildSchema(){
        return SchemaParser.newParser()
                .file("new_product.graphqls")
                .resolvers(
                        new Query(newProductRepo),
                        new Mutation(newProductRepo))
                .build()
                .makeExecutableSchema();
    }
}
