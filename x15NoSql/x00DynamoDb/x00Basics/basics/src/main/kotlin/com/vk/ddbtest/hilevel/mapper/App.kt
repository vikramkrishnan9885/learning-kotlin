package com.vk.dynamoDbClienttest

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.amazonaws.services.dynamodbv2.model.BillingMode
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException
import com.vk.ddbtest.models.ProductInfo
import java.util.*


class App {
    companion object {
        @JvmStatic
        fun main(args:Array<String>){
            //val endpoint = AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2")

            val dynamoDbClient = AmazonDynamoDBClientBuilder.standard()
                    .withCredentials(ProfileCredentialsProvider("personal"))
                    .withRegion("us-east-1")
                    .build()

            // https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBMapper.OptionalConfig.html
            val mapperConfig = DynamoDBMapperConfig.builder()
                    .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.CLOBBER)
                    .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT)
                    .withTableNameOverride(null)
                    .withPaginationLoadingStrategy(DynamoDBMapperConfig.PaginationLoadingStrategy.EAGER_LOADING)
                    .build()

            val dynamoDb = DynamoDB(dynamoDbClient)
            val mapper = DynamoDBMapper(dynamoDbClient, mapperConfig)

            // create table
            try {
                dynamoDbClient.createTable(
                        mapper.generateCreateTableRequest(ProductInfo::class.java)
                                .withBillingMode(BillingMode.PAY_PER_REQUEST)
                )
                println("Table created")
            } catch (e: ResourceInUseException) {
                println("table already created.")
            }

            // https://stackoverflow.com/questions/40192304/aws-dynamodb-resource-not-found-exception
            val sleepWait =  2 * 60 * 1000
            Thread.sleep(sleepWait.toLong())
            mapper.save(
                    ProductInfo(
                            //id = "test-123",
                            msrp = "Twenty",
                            cost = 20
                    )
            )

            //val record = mapper.load(ProductInfo::class.java, "test-123")

            // THIS DOESN'T WORK
            //val record = mapper.load(ProductInfo::class.java, "1")
            //println(record)

            val eav: MutableMap<String, AttributeValue> = HashMap<String, AttributeValue>()
            eav[":val1"] = AttributeValue().withS("Twenty")
            eav[":val2"] = AttributeValue().withN("10")

            val scanExpression: DynamoDBScanExpression =
                    DynamoDBScanExpression()
                    .withFilterExpression("MSRP = :val1 and Cost > :val2")
                    .withExpressionAttributeValues(eav)

            val latestReplies: List<ProductInfo> = mapper.scan(ProductInfo::class.java, scanExpression)

            for (productInfo in latestReplies) {
                System.out.format(
                        "Id=%s, MSRP=%s, Cost=%s %n", 
                        productInfo.id,
                        productInfo.msrp,
                        productInfo.cost
                )
            }

            Thread.sleep(sleepWait.toLong())

            // See Product Info
            //val table = dynamoDb.getTable("ProductInfo")
            //table.delete()
            //table.waitForDelete()

            dynamoDbClient.deleteTable("ProductInfo")
            Thread.sleep(sleepWait.toLong())
            println("Deleted Table")
        }
    }
}