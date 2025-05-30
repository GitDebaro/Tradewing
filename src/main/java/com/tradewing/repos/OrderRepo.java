package com.tradewing.repos;

import com.tradewing.models.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;



public interface OrderRepo extends JpaRepository<OrderEntity, Long> {

}