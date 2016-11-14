package org.isheep.repository;

import org.isheep.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by anthony on 14/11/16.
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
