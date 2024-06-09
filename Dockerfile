FROM postgres:14-alpine as gearhead_product_db
ENV POSTGRES_USER account
ENV POSTGRES_PASSWORD account123
ENV POSTGRES_DB gearhead_product
COPY init-scripts /docker-entrypoint-initdb.d


FROM openjdk:17 as gearhead_product

# Information of owner or maintainer of image
MAINTAINER gearhead

# Add the application's jar to the container
COPY target/gearheadproduct-0.0.1-SNAPSHOT.jar gearheadproduct-0.0.1-SNAPSHOT.jar

#Execute the application
ENTRYPOINT ["java", "-jar","/gearheadproduct-0.0.1-SNAPSHOT.jar"]